package pp.spacetanks.view.ViewController;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pp.spacetanks.controller.GameEngine;
import pp.spacetanks.controller.PlayGameController;
import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.model.item.Tank;
import pp.spacetanks.model.item.TankComponent;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.MapView;

import java.util.ArrayList;

/**
 * controller for PlayGame.fxml
 */
public class PlayGameViewController {

    PlayGameController controller;
    private GameModel model;
    private boolean sound;
    private MapView view;

    @FXML
    public Label activePlayer;

    @FXML
    private Label player1Name;

    @FXML
    private Label player2Name;

    @FXML
    public Label activePlayerMoney;

    @FXML
    private ProgressBar tankFuelStatusP1;

    @FXML
    private ProgressBar tankAntennaStatusP1;

    @FXML
    private ProgressBar tankCannonStatusP1;

    @FXML
    private ProgressBar tankChainStatusP1;

    @FXML
    private ProgressBar tankFuelStatusP2;

    @FXML
    private ProgressBar tankAntennaStatusP2;

    @FXML
    private ProgressBar tankCannonStatusP2;

    @FXML
    private ProgressBar tankChainStatusP2;

    @FXML
    private ProgressBar tankChassiStatusP1;

    @FXML
    private ProgressBar tankChassiStatusP2;

    @FXML
    private Text txtP1Money;

    @FXML
    private Text txtP2Money;

    @FXML
    public MenuButton munSelect;

    @FXML
    private Canvas smallCanvas;

    @FXML
    private Label endGameLabel;

    /**
     * method to initialize a constructor
     * @param model
     */
    @FXML
    public void initialize(GameModel model){
        this.model = model;
        //setBackgroundSizeAndPos();

        sound=false;

        activePlayer.setText(model.getActivePlayer().getName());
        activePlayerMoney.setText(String.valueOf(model.getActivePlayer().getMoney()));
        player1Name.setText(model.getPlayer1().getName());
        player2Name.setText(model.getPlayer2().getName());

        setProgressBars();
        setMenuItems();
        prepareSlider();

        this.anchor.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyEventEnter(event);
            }
        });
        anchor.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyEventRelease(event);
            }
        });
        view = controller.getEngine().getView();
    }

    @FXML
    private AnchorPane anchor;

    @FXML
    private Slider sliderAngle;

    @FXML
    private Slider sliderPower;

    @FXML
    private Canvas canvas;

    /**
     * method when the "pause" button is pressed
     */

    @FXML
    public void pause(){
        stop();
        stopCannonMovingSound();
        controller.pause();
    }
    /**
     * method when the "+" button is pressed
     */

    @FXML
    public void zoomIn(){
        controller.zoomIn();
    }

    /**
     * method when the "-" button is pressed
     */
    @FXML
    public void zoomOut(){
        controller.zoomOut();
    }

    /**
     * method when the "forward" button is pressed
     */
    @FXML
    public void forward(){
        controller.moveRight();
    }

    /**
     * method when the "back" button is pressed
     */

    @FXML
    public void backward(){
        controller.moveLeft();
    }

    /**
     * Method to stop the tank, if current player released button "forward" or "zureck"
     */
    @FXML
    public void stop(){
        controller.stop();
    }

    /**
     * Method to stop movingCannonSound
     */
    @FXML
    public void stopCannonMovingSound(){
        controller.stopSoundCannonMoving();
        sound=false;
        anchor.requestFocus();
        model.gameState.setAngle(sliderAngle.getValue());
    }

    /**
     * just starts the canonmovingsound
     */
    public void startCanonMovingSound() {
        if (!model.getMap().getProjectiles().isEmpty()) stopCannonMovingSound();
        else if (!sound) {
            controller.playSoundCannonMoving();
            sound = true;
        }
    }

    /**
     * updates the angle of the canon and plays canonmovingsound
     */
    @FXML
    public void updateAngle() {
        model.gameState.setAngle(sliderAngle.getValue());
        prepareSlider();

        if(model.getMap().getProjectiles().size() > 0||model.getActivePlayer().getTank().getAngle()==-90||model.getActivePlayer().getTank().getAngle()==90) stopCannonMovingSound();
        else if (!sound) {
            controller.playSoundCannonMoving();
            sound = true;
        }
    }

    /**
     * changes Power of the current player while slider is dragged
     */
    @FXML
    private void updatePower() {
        anchor.requestFocus();
        model.gameState.setPower(sliderPower.getValue());
    }

    /**
     * method to explode the projectile
     */
    @FXML
    public void spark(){
        controller.spark();
    }

    /**
     * method to shot the projectile
     */
    @FXML
    public void fire(){
        controller.fire();
    }

    /**
     * method to show the help Line
     */
    @FXML
    public void helpLine(){controller.helpLine();}

    /**
     * sets the current controller
     * @param controller
     */
    public void setController(PlayGameController controller){
        this.controller = controller;
    }

    /**
     * returns the current value of the angle slider
     * @return
     */
    public double getAngle(){
        return sliderAngle.getValue();
    }

    /**
     * Method to set the progressBar on map
     */
    public void setProgressBars() {
        Tank tank1 = model.getPlayer1().getTank();
        tankFuelStatusP1.setProgress(tank1.getRemainingFuel()/ tank1.getFuelCapacity());
        tankAntennaStatusP1.setProgress(calculateProgress(tank1.getAntenna()));
        tankCannonStatusP1.setProgress(calculateProgress(tank1.getCannon()));
        tankChainStatusP1.setProgress(calculateProgress(tank1.getChain()));
        tankChassiStatusP1.setProgress(calculateProgress(tank1.getChassis()));
        Tank tank2 = model.getPlayer2().getTank();
        tankFuelStatusP2.setProgress(tank2.getRemainingFuel()/ tank2.getFuelCapacity());
        tankAntennaStatusP2.setProgress(calculateProgress(tank2.getAntenna()));
        tankCannonStatusP2.setProgress(calculateProgress(tank2.getCannon()));
        tankChainStatusP2.setProgress(calculateProgress(tank2.getChain()));
        tankChassiStatusP2.setProgress(calculateProgress(tank2.getChassis()));
        txtP1Money.setText(String.valueOf(model.getPlayer1().getMoney()));
        txtP2Money.setText(String.valueOf(model.getPlayer2().getMoney()));
    }
    /**
     * Method to set windowSize and set relative positions
     */
    public void setBackgroundSizeAndPos() {
        GameEngine engineOfController = controller.getEngine();
        anchor.setPrefWidth(engineOfController.stageWidth);
        canvas.setWidth(engineOfController.stageWidth+35.0);
        activePlayer.setLayoutX((engineOfController.stageWidth/2)-(activePlayer.getWidth()/2));
        activePlayerMoney.setLayoutX((engineOfController.stageWidth/2)-(activePlayerMoney.getWidth()/2));
        anchor.setPrefHeight(engineOfController.stageHeight);
        canvas.setHeight(engineOfController.stageHeight+20);
        endGameLabel.setLayoutX((engineOfController.stageHeight/2) - (endGameLabel.getWidth()/2));
    }

    /**
     * calculate state of the progressbar
     * @param component specified tank component like antenna
     * @return a double value of calculated by remaining health / max health
     **/
    private double calculateProgress(TankComponent component){
        return (double)component.getRemainingHealth()/component.maxHealth;
    }

    /**
     * sets the slider on current player values
     */
    public void prepareSlider(){
        sliderAngle.setValue(model.getActivePlayer().getTank().getAngle());
        sliderPower.setValue(model.getActivePlayer().getTank().getPower());
    }

    /**
     * prepares the Dropdown menu and changes the current projectile to the new projectile
     */
    public void setMenuItems() {
        ArrayList<MenuItem> items = new ArrayList<>();
        for(ProjectileType type: ProjectileType.getAllTypes(model)){
            MenuItem tmp = new MenuItem(type+", Preis: "  + type.price + " BwCoins");
            items.add(tmp);
            tmp.setOnAction(a->{
                model.gameState.setAmmo(type);
                munSelect.setText(type.toString());
                anchor.requestFocus();
            });
        }
        munSelect.getItems().remove(0,munSelect.getItems().size());
        munSelect.getItems().addAll(items);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Canvas getSmallCanvas(){
        return smallCanvas;
    }

    /**
     * method to bind keys on actions (move,fire,pause)
     */
    private void keyEventEnter(KeyEvent event) {
        MapView view = controller.getEngine().getView();
        switch (event.getCode()) {
            case ENTER: fire(); stopCannonMovingSound(); break;

            case ESCAPE: pause(); stopCannonMovingSound(); break;

            case SHIFT: helpLine(); break;

            case LEFT: backward(); break;

            case RIGHT: forward(); break;

            case UP: if (model.getActivePlayer().getTank().getAngle() == 90||model.getMap().getProjectiles().size()>0){stopCannonMovingSound();} else {model.gameState.setAngle(model.getActivePlayer().getTank().getAngle()+1); startCanonMovingSound(); prepareSlider();}  break;

            case DOWN: if (model.getActivePlayer().getTank().getAngle() == -90||model.getMap().getProjectiles().size()>0){stopCannonMovingSound();} else {model.gameState.setAngle(model.getActivePlayer().getTank().getAngle()-1); startCanonMovingSound(); prepareSlider();}  break;

            case PLUS: model.gameState.setPower(model.getActivePlayer().getTank().getPower()+0.1); prepareSlider(); break;

            case MINUS: model.gameState.setPower(model.getActivePlayer().getTank().getPower()-0.1); prepareSlider(); break;

            case R: spark(); break;

            case N: model.loadRandomMap(); break;
            case G: {
                model.getPlayer1().recieveMoney(10000);
                model.getPlayer2().recieveMoney(10000);
                break;
            }
            case M: model.loadDemoMap(); break;

            case W:
                view.setRefPoint(view.getRefPoint().add(new DoubleVec(0,20)));
                break;

            case S:
                view.setRefPoint(view.getRefPoint().sub(new DoubleVec(0,20)));
                break;

            case A:
                view.setRefPoint(view.getRefPoint().add(new DoubleVec(20,0)));
                break;

            case D:
                view.setRefPoint(view.getRefPoint().sub(new DoubleVec(20,0)));
                break;

            case E: zoomIn(); break;

            case Q: zoomOut(); break;
        }
    }

    /**
     * Method when key released
     */
    private void keyEventRelease(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT: stop(); break;

            case RIGHT: stop(); break;

            case UP: stopCannonMovingSound(); break;

            case DOWN: stopCannonMovingSound(); break;
        }
    }

    /**
     * for endgameanimation
     */
    public void setEndGame(){
        GameModel model = controller.getEngine().getModel();
            view.setZoomFactor(3);
            view.setRefPoint(new DoubleVec(canvas.getWidth()*0.5,canvas.getHeight()*0.5).add(model.getWinner().getTank().getChassis().getPos().mult(view.getZoomFactor())).div(view.getZoomFactor()+1));
            endGameLabel.setText(model.getWinner().getName() + " hat gewonnen!");
            model.startEndGameAnimation();

    }

    /**
     * end of endgameanimation
     */
    public void deleteEndGame(){
        model.stopEndGameAnimation();
        endGameLabel.setVisible(false);
        view.setZoomFactor(1);
        view.setRefPoint(new DoubleVec(model.getFieldSizeX()*0.5,model.getFieldSizeY()*0.5),0);
        model.getMap().clearProjectiles();
        model.getMap().clearAnimations();
    }

}
