package pp.spacetanks.controller;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pp.spacetanks.model.GameModel;
import pp.spacetanks.sound.Sound;
import pp.spacetanks.view.MapView;

import java.awt.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The game engine using the state pattern to control the game in its different states, i.e., playing the game,
 * showing that the game has been won or lost, and the menu.
 */
public class GameEngine {

    private static final Logger logger = Logger.getLogger(GameEngine.class.getName());
    /**
     * creates the Controllers, that are needed for the Game
     */
    private final Controller optionsMenuController = new OptionsMenuController(this);
    private final Controller menuController = new MenuController(this);
    private final PlayGameController playGameController = new PlayGameController(this);
    private final Controller purchaseMenuController = new PurchaseMenuController(this);
    public final Sound sound;
    public double stageHeight;
    public double stageWidth;

    private final GameModel model;
    private final MapView view;

    public final Stage stage;

    private Controller controller;

    private boolean pause=false;

    private boolean inGame = false;

    /**
     * Creates a new GameEngine
     *
     * @param stage the Stage for the Game and the different menus
     * @param properties the game properties, which - among other - configures resources like images and audio clips
     */
    public GameEngine(Stage stage, Properties properties){
        this.stage=stage;

        this.stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            stageWidth = (double) newValue;
            this.controller.update();
        });
        this.stage.heightProperty().addListener((observable, oldVal, newVal) -> {
            stageHeight = (double) newVal;
            this.controller.update();
        });

        //set up model, view
        model = new GameModel(properties);
        view = new MapView(model);
        sound = new Sound(model); //no reference needed later

        //set up the first Controller, Within the Game starts
        activateMenuController();

    }

    /**
     * Method to change the current Controller and to switch the state
     *
     * @param controller the Controller for the new state
     */
    private void setController(Controller controller){
        if (this.controller!=null) this.controller.exit();
        this.controller=controller;
        this.controller.entry();
    }


    /**
     * Returns the model
     *
     * @return the model, which is used in the DroidsGame
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * Returns the view
     *
     * @return the view, which is used of the DroidsMap
     */
    public MapView getView() {
        return view;
    }

    /**
     * Switches the game into the game state by selecting its controller
     */
    void activatePlayGameController() {
        setController(playGameController);
        inGame = true;
    }

    /**
     * Switches the game into the menu state by selecting its controller
     */
    void activateMenuController() {
        setController(menuController);
    }

    /**
     * Switches the game into the state that indicates victory by selecting its controller
     */
    void activateOptionsMenuController() {
        setController(optionsMenuController);
    }

    /**
     * Switches the game into the state that indicates defeat by selecting its controller
     */
    void activatePurchaseMenuController() {
        setController(purchaseMenuController);
        inGame=false;
    }


    /**
     * The game loop, which checks user inputs and updates the view periodically, if the game is not in pause.
     */
    public void gameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long time) {
                if(!pause && inGame) {
                    controller.update();
                    view.update();
                }
            }
        }.start();
    }


    /**
     * Sets the specified scene and changes the UI that way.
     *
     * @param scene the scene to be shown in the stage of the game.
     */
    void setScene(Scene scene) {
        stage.setScene(scene);
        stage.sizeToScene();
    }

    /**
     * set app to fullscreen
     */
    void fullscreen(){
        stage.setFullScreen(true);
        controller.update();
    }

    /**
     * saves, if the game is in pause
     */
    void enterPause(){pause=true;}

    /**
     * saves, if the game is not in pause
     */
    void exitPause() {pause=false;}

    Boolean inPause(){return pause;}


}
