package pp.spacetanks.view.ViewController;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pp.spacetanks.controller.MenuController;
import javafx.scene.image.ImageView;

/**
 * controller for MainMenu.fxml
 */
public class MainMenuViewController {


    private Image startBtnImageBrighting = new Image("/images/Buttons/NewGameButtonBrighting.png");
    private Image startBtnImage = new Image("/images/Buttons/NewGameButton.png");
    private Image exitBtnImage = new Image ("/images/Buttons/ExitButton.png");
    private Image exitBtnImageBighting = new Image ("/images/Buttons/ExitButtonBrighting.png");
    private Image optionBtnImage = new Image( "/images/Buttons/OptionsButton.png");
    private Image optionBtnImageBrighting = new Image("/images/Buttons/OptionsButtonBrighting.png");
    private Image continueBtnImage = new Image("/images/Buttons/ContinueButton.png");
    private Image continueBtnImageBrighting = new Image("/images/Buttons/ContinueButtonBrighting.png");
    private Image continueBtnImageGray = new Image("/images/Buttons/ContinueButtonGray.png");
    private MenuController controller;
    private boolean continueBtnOccupied = true;


    @FXML
    private ImageView mainStart;

    @FXML
    private ImageView mainOptionen;

    @FXML
    private ImageView mainBeenden;

    @FXML
    private AnchorPane mainMenuAnchor;

    @FXML
    private ImageView mainMenuBGImg;

    @FXML
    private Text headline;

    @FXML
    private ImageView mainContinue;

    /**
     * ends the game
     */
    @FXML
    void quit() {
        controller.quit();
    }

    /**
     * calls the options menu
     */
    @FXML
    void options() {
        controller.options();
    }

    /**
     * starts a game, get to the purchase menu
     */
    @FXML
    void start() {
        controller.start();
        continueBtnOccupied = false;
    }

    /**
     * continues the current game
     */
    @FXML
    void continueGame() {if(!continueBtnOccupied)controller.continueGame();}

    /**
     * lets the button shine
     */
    @FXML
    void changeImageNewGameEnter(){
        mainStart.setImage(startBtnImageBrighting);
    }
    @FXML
    void changeImageNewGameExit(){
        mainStart.setImage(startBtnImage);
    }
    @FXML
    void changeImageOptionsEnter(){
        mainOptionen.setImage(optionBtnImageBrighting);
    }
    @FXML
    void changeImageOptionsExit(){
        mainOptionen.setImage(optionBtnImage);
    }
    @FXML
    void changeImageExitEnter(){
        mainBeenden.setImage(exitBtnImageBighting);
    }
    @FXML
    void changeImageExitExit(){
        mainBeenden.setImage(exitBtnImage);
    }
    @FXML
    void changeImageContinueEnter(){
        if(!continueBtnOccupied){
            mainContinue.setImage(continueBtnImageBrighting);
        }
    }
    @FXML
    void changeImageContinueExit(){
        if(!continueBtnOccupied){
            mainContinue.setImage(continueBtnImage);
        }
    }

    /**
     * sets the controller of the engine
     * @param controller
     */
    public void setController(MenuController controller){
        this.controller = controller;
        if(controller.getEngine().getModel().getPlayer1().getName() == ""){
            continueBtnOccupied = true;
        }
        else continueBtnOccupied = false;
        if(continueBtnOccupied){
            mainContinue.setImage(continueBtnImageGray);
        }
        else mainContinue.setImage(continueBtnImage);

    }

    /**
     * method to set the layout dynamically
     */
    public void setLayout() {
        Stage stageOfController = this.controller.getEngine().stage;
        mainMenuAnchor.setPrefHeight(stageOfController.getHeight());
        mainMenuAnchor.setPrefWidth(stageOfController.getWidth());
        mainMenuBGImg.setFitHeight(stageOfController.getHeight());
        mainMenuBGImg.setFitWidth(stageOfController.getWidth());
        headline.setLayoutX((stageOfController.getWidth()/2) - (130.0));
        mainStart.setLayoutX((stageOfController.getWidth()/2) - (mainStart.getFitWidth()/2));
        mainOptionen.setLayoutX((stageOfController.getWidth()/2) - (mainOptionen.getFitWidth()/2));
        mainBeenden.setLayoutX((stageOfController.getWidth()/2) - (mainBeenden.getFitWidth()/2));
        mainContinue.setLayoutX((stageOfController.getWidth()/2)- (mainContinue.getFitWidth()/2));
    }

}
