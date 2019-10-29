package pp.spacetanks.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.view.FieldSize;
import pp.spacetanks.view.ViewController.OptionsMenuViewController;

import java.io.IOException;

/**
 * The Controller for the options Menue
 */


public class OptionsMenuController extends Controller{

    public static final String OPTIONS_MENU_FXML = "/FXML/OptionsMenu.fxml";
    private boolean isMutedSound;
    private boolean isMutedEffects;

    private FieldSize fieldSize;
    private int flyingTime;
    private OptionsMenuViewController viewController;

    public OptionsMenuController(GameEngine engine){super(engine);}

    public GameEngine getEngine(){ return engine;}

    /**
     * entry Method for OptionsMenuController
     */

    @Override
    void entry() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OPTIONS_MENU_FXML));
        fieldSize=engine.getModel().getFieldSize();
        flyingTime=engine.getModel().getFlyingTime();
        isMutedSound = engine.sound.getMutedSound();
        isMutedEffects = engine.sound.getMutedEffects();

        try {
            AnchorPane root = loader.load();
            OptionsMenuViewController c = loader.getController();

            c.setController(this);
            viewController = c;
            c.setCheckBoxMute();
            c.setCheckboxSoundEffects();
            c.setSliderProjectile(flyingTime);
            c.setSliderMap((int)getFieldSizeVal());

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/stylesheets/spacetanksStyle.css");
            engine.setScene(scene);
        }

        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * update method used for layout updates
     */
    @Override
    void update() {
        viewController.setLayout();
    }

    /**
     * exit Method for OptionsMenuController
     */
    public void exit() {
        //Do something on exiting state
    }

    /**
     * mute Method for the whole game music
     */
    public void muteSound() {
        isMutedSound = engine.sound.getMutedSound();
        if(isMutedSound) {
            engine.getModel().notifyReceivers(Notification.NO_MUTE);
            engine.sound.setMutedSound(false);

        }
        else {
            engine.getModel().notifyReceivers(Notification.MUTE);
            engine.sound.setMutedSound(true);
        }

    }

    /**
     * mute method for the games sound effects
     */
    public void muteEffects(){
        isMutedEffects = engine.sound.getMutedEffects();
        if(isMutedEffects){
            engine.sound.setMutedEffects(false);
        }
        else {
            engine.sound.setMutedEffects(true);
        }
    }

    /**
     * back Method for OptionsMenuController sets engine.Controller to Menu
     */

    public void back() {
        if(engine.inPause()) {
            engine.getModel().setFlyingTime(flyingTime);
            engine.activatePlayGameController();
            engine.getModel().continueStopWatches();
            engine.exitPause();
        } else {
            engine.getModel().setFieldSize(fieldSize);
            engine.getModel().setFlyingTime(flyingTime);
            engine.activateMenuController();
            engine.getModel().gameState.back();
        }
    }

    public void setFieldSize(FieldSize fieldSize){
        this.fieldSize = fieldSize;
    }

    public void setFlyingTime(int flyingTime) {this.flyingTime=flyingTime;}

    public boolean getMute(){
        return isMutedSound;
    }

    public boolean getMuteEffects(){return isMutedEffects;}

    private double getFieldSizeVal() {
        if (fieldSize==FieldSize.SMALL) return 1;
        else if (fieldSize==FieldSize.MIDDLE) return 2;
        else return 3;
    }
}
