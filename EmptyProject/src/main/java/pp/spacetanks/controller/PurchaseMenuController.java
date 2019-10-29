package pp.spacetanks.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pp.spacetanks.model.item.TankComponent;
import pp.spacetanks.model.item.TankType;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.view.ViewController.OptionsMenuViewController;
import pp.spacetanks.view.ViewController.PurchaseMenuViewController;

import java.io.IOException;
import java.net.URL;

/**
 * The Controller for the Menu, where the Players can buy and repair there Tanks
 */
public class PurchaseMenuController extends Controller {

    private static final String PURCHASE_MENU_FXML = "/FXML/PurchaseMenu.fxml";

    private PurchaseMenuViewController viewController;

    public PurchaseMenuController(GameEngine engine){

        super(engine);
    }

    @Override
    void entry() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PURCHASE_MENU_FXML));
        try {
            BorderPane root = loader.load();
            viewController = loader.getController();
            viewController.init(this, engine.getModel());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/stylesheets/spacetanksStyle.css");
            engine.setScene(scene);
            engine.stage.setWidth(1280.0);
            engine.stage.setHeight(800.0);
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
     * refuels the tank of the current player
     */
    public void refuelTank() {
        engine.getModel().gameState.refueling();
    }

    /**
     * repairs all components of the tank from the current player
     */
    public void repairAll() {
        engine.getModel().gameState.repairAll();
    }

    /**
     * repairs the antenna of the tank from the active player
     */
    public void repairAntenna() {
        engine.getModel().gameState.repairAntenna();
    }

    /**
     * repairs the cannon of the tank from the current player
     */
    public void repairCannon() {
        engine.getModel().gameState.repairCannon();
    }

    /**
     * repairs the chain of the tank from the current Player
     */
    public void repairChain() {
        engine.getModel().gameState.repairChain();
    }

    /**
     * repairs the chassi of the tank from the current Player
     */
    public void repairChassi() { engine.getModel().gameState.repairChassi();}

    /**
     * ends the action of the current player and jump to the next
     */
    public void nextPlayerStartGame() {
        engine.getModel().gameState.finishAction();
    }

    /**
     * start the game
     */
    public void startGame(){
        if(!engine.getModel().getActivePlayer().getTank().isDestroyed()) {
            engine.getModel().gameState.finishAction();
            engine.activatePlayGameController();
        }
    }

    /**
     * gets back to the last Controller or player
     */
    public void back() {
        if(engine.getModel().getActivePlayer()==engine.getModel().getPlayer1()) engine.activateMenuController();
        engine.getModel().gameState.back();
    }

    /**
     * the current player gets the tank
     * @param tank the tank that is choosen
     */
    public void buyTank(TankType tank) {
        engine.getModel().gameState.chooseTank(tank);
    }

    /**
     * method to sell the tank of the current player
     */
    public void sellTank() {engine.getModel().gameState.sell();}

    /**
     * method to save the players name
     * @param name of the current player
     */
    public void setPlayerName(String name) {
        engine.getModel().gameState.safeName(name);
    }

    public GameEngine getEngine(){
        return engine;
    }

}
