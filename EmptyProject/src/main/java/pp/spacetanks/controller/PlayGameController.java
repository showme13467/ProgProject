package pp.spacetanks.controller;


import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.Player;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.util.StopWatch;
import pp.spacetanks.view.ViewController.EndGameViewController;
import pp.spacetanks.view.ViewController.PlayGameViewController;

import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * The Controller for the running Game
 */
public class PlayGameController extends Controller {

    public static final String PLAY_GAME_CONTROLLER_FXML = "/PlayGame.fxml";

    private final StopWatch stopWatch= new StopWatch();
    private final StopWatch endGame = new StopWatch();
    private double lastUpdate;
    private PlayGameViewController viewController;
    private Player playerBefore;
    private boolean end = false;
    private Stage stage;
    private EndGameViewController endGameController;

    private boolean vor = false;
    private boolean zurueck = false;
    private boolean hasStoped = true;
    private boolean movingSound = false;
    private boolean firstTime = true;

    public PlayGameController(GameEngine engine){super(engine);}

    @Override
    void entry() {
        engine.getModel().notifyReceivers(Notification.INGAME_MUSIC);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PlayGame.fxml"));
        try {
            AnchorPane root = loader.load();
            viewController = loader.getController();
            viewController.setController(this);
            viewController.initialize(engine.getModel());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/stylesheets/spacetanksStyle.css");
            engine.setScene(scene);
            engine.stage.setFullScreenExitHint("Mit Esc Pausemenü öffnen, mit X Vollbild schließen");
            engine.stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("X"));
            engine.fullscreen();
        }
        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        engine.getView().setCanvas(getCanvas());
        engine.getView().setSmallCanvas(getSmallCanvas());
        Canvas test = new Canvas();
        test.getGraphicsContext2D().setFill(javafx.scene.paint.Color.BLUE);
        test.getGraphicsContext2D().fillRect(0,0,100,100);
        stopWatch.start();
        playerBefore=engine.getModel().getActivePlayer();
    }

    @Override
    void update() {
        final double delta = stopWatch.getTime() - lastUpdate;
        lastUpdate = stopWatch.getTime();
        GameModel model = engine.getModel();
        if(!end) {
            model.update(delta);
        }
        if(engine.getModel().gameOver()&& endGame.getTime() == 0&&firstTime) {
            endGame.start();
            viewController.setEndGame();
        }
        if (endGame.getTime()>5&&model.getMap().getProjectiles().isEmpty()) {
            firstTime = false;
            endGame.reset();
            viewController.deleteEndGame();
            setEndScreen();
        }

        //moving the tank
        if(engine.getModel().getActivePlayer().getTank().getRemainingFuel()==0||engine.getModel().getMap().getProjectiles().size()>0||engine.getModel().getActivePlayer().getTank().getChain().isDestroyed()) {
            stopSoundMoving();
            movingSound=false;
        }

        else if(vor) {
            engine.getModel().gameState.moveRight();
            if (!movingSound) {
                playSoundMoving();
                movingSound=true;
            }
        }
        else if(zurueck){
            engine.getModel().gameState.moveLeft();
            if (!movingSound) {
                playSoundMoving();
                movingSound=true;
            }
        }

        if (activePlayerChange()) {
            viewController.prepareSlider();
            playerBefore=engine.getModel().getActivePlayer();
        }
        viewController.setBackgroundSizeAndPos();
        viewController.setProgressBars();
        if(model.changedPlayerSinceLastCheck()) {
            viewController.setMenuItems();
            viewController.munSelect.show();
            viewController.munSelect.hide();
        }
        viewController.activePlayer.setText(engine.getModel().getActivePlayer().getName());
        viewController.activePlayerMoney.setText(String.valueOf(engine.getModel().getActivePlayer().getMoney()));
        viewController.munSelect.setText(engine.getModel().getActivePlayer().getTank().getCannon().getProjectileType().toString());
    }

    /**
     * pauses the game and change the Scene to the Options
     */
    public void pause() {
        engine.enterPause();
        stopWatch.stop();
        engine.getModel().pauseStopWatches();
        engine.getModel().gameState.Options();
        engine.activateOptionsMenuController();
    }

    /**
     * method to return, if the active player has changed
     * @return true for active player change
     */
    private boolean activePlayerChange(){ return playerBefore!=engine.getModel().getActivePlayer();}

    /**
     * zooms in on the field where the game is running
     */
    public void zoomIn() {
        engine.getView().zoomIn();
    }

    /**
     * zooms out on the field where the game is running
     */
    public void zoomOut() {
        engine.getView().zoomOut();
    }

    /**
     * starts the movement to the right
     */
    public void moveRight() {
        if(hasStoped) {
            vor = true;
        }
        hasStoped=false;
    }

    /**
     * starts the movement to the left
     */
    public void moveLeft() {
        if(hasStoped) {
            zurueck = true;
        }
        hasStoped=false;
    }

    /**
     * method to stop the movement
     */
    public void stop(){
        vor = false;
        zurueck = false;
        hasStoped=true;
        movingSound=false;
        stopSoundMoving();
    }

    /**
     * method to spark the projectile in the game
     */
    public void spark() {
        engine.getModel().gameState.remoteTrigger();
    }

    /**
     * fires the projectile of the current player
     */
    public void fire() {
        engine.getModel().gameState.fire();
    }

    /**
     * start the sound of the moving tank
     */
    public void playSoundMoving(){
        engine.getModel().notifyReceivers(Notification.TANK_MOVED);
    }

    /**
     * stop the sound of the moving tank
     */
    public void stopSoundMoving(){
       engine.getModel().notifyReceivers(Notification.TANK_MOVED_STOP);
    }

    /**
     * stop the sound of the moving cannon
     */
    public void stopSoundCannonMoving(){
        engine.getModel().notifyReceivers(Notification.CANNON_ADJUST_STOP);
    }

    /**
     * start the sound of the moving cannon
     */
    public void playSoundCannonMoving(){
        engine.getModel().notifyReceivers(Notification.CANNON_ADJUSTED);
    }

    /**
     * activate or deactivates the helpline
     */
    public void helpLine(){engine.getModel().gameState.showHelpline();}

    public Canvas getCanvas(){
        return viewController.getCanvas();
    }

    public Canvas getSmallCanvas(){return viewController.getSmallCanvas();}

    public GameEngine getEngine(){ return engine;}

    /**
     * makes the end screen after a match round
     */
    public void endEndScreen(){
        firstTime = true;
        stage.close();
        end = false;
        engine.getModel().pay();
        engine.activatePurchaseMenuController();
    }

    /**
     * set the end screen after the match round
     */
    private void setEndScreen(){
        end = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EndScreen.fxml"));
        try {
            AnchorPane root = loader.load();
            endGameController = loader.getController();
            endGameController.setController(this);
            endGameController.init();
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add("/stylesheets/spacetanksStyle.css");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setOnCloseRequest(Event::consume);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
