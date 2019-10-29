package pp.spacetanks.view.ViewController;


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pp.spacetanks.controller.OptionsMenuController;
import pp.spacetanks.view.FieldSize;

/**
 * controller for OptionsMenu.fxml
 */
public class OptionsMenuViewController{

    private OptionsMenuController controller;

    @FXML
    Slider sliderMap;

    @FXML
    Slider sliderProjectile;

    @FXML
    CheckBox checkboxSound;

    @FXML
    CheckBox checkboxSoundEffects;

    @FXML
    private ImageView buttonBack;

    @FXML
    private AnchorPane optionsMenuAnchor;

    @FXML
    private ImageView optionsMenuBGImg;

    /**
     * Initializes the controller.
     */
    @FXML
    void changeImageBackEnter(){
        buttonBack.setImage(new Image("/images/Buttons/ZurueckButtonBrighting.png"));
    }
    @FXML
    void changeImageBackExit(){
        buttonBack.setImage(new Image("/images/Buttons/ZurueckButton.png"));
    }

    /**
     * turn Sounds off
     */
    @FXML
    public void muteSound(){
        controller.muteSound();
    }

    @FXML
    public void muteEffects(){controller.muteEffects();}

    /**
     *  Method for clicking the Button "Zurueck".
     */
    @FXML
    public void back(){
        int flyingTime=(int)sliderProjectile.getValue();
        FieldSize size= FieldSize.MIDDLE;
        switch ((int) sliderMap.getValue()){
            case 1: size = FieldSize.SMALL;
            break;
            case 2: size = FieldSize.MIDDLE;
            break;
            case 3: size = FieldSize.LARGE;
            break;
        }
        controller.setFieldSize(size);
        controller.setFlyingTime(flyingTime);
        controller.back();
    }

    /**
     * Returns the currently set flightTime.
     * @return
     * */
    public double getFlighTime(){
        return (int) sliderProjectile.getValue();
    }

    /**
     * Returns the currently set fieldSize.
     * @return
     * */
    public int getFieldSize(){
        return (int) sliderMap.getValue();
    }

    /**
     * set current controller
     * @param controller
     */
    public void setController(OptionsMenuController controller){
        this.controller = controller;
    }

    /**
     * set Checkbox in Optionsmenu
     */
    public void setCheckBoxMute(){
        if(controller.getMute()){
            checkboxSound.setSelected(true);
        }
        else checkboxSound.setSelected(false);
    }

    public void setCheckboxSoundEffects(){
        if(controller.getMuteEffects()){
            checkboxSoundEffects.setSelected(true);
        }
        else checkboxSoundEffects.setSelected(false);
    }

    /**
     * set slider on current FieldSize position
     * @param val
     */
    public void setSliderMap(int val) {
        sliderMap.setValue(val);
    }

    /**
     * set slider on current flyTime pos
     * @param val
     */
    public void setSliderProjectile(int val) {sliderProjectile.setValue((double)val);}

    /**
     * method for setting the layout
     */
    public void setLayout() {
        Stage stageOfController = this.controller.getEngine().stage;
        optionsMenuAnchor.setPrefHeight(stageOfController.getHeight());
        optionsMenuAnchor.setPrefWidth(stageOfController.getWidth());
        optionsMenuBGImg.setFitHeight(stageOfController.getHeight());
        optionsMenuBGImg.setFitWidth(stageOfController.getWidth());
    }
}
