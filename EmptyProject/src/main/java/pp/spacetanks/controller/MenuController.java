package pp.spacetanks.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.view.ViewController.MainMenuViewController;
import pp.spacetanks.view.ViewController.PurchaseMenuViewController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * The Controller for the starting Menue
 */


public class MenuController extends Controller {

    public static final String MAIN_MENU_FXML = "/FXML/MainMenu.fxml";

    private MainMenuViewController viewController;

    public MenuController(GameEngine engine) {super(engine);}

    public GameEngine getEngine(){ return engine;}

    /**
     * entry Method for MenuController
     */

    @Override
    void entry() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_MENU_FXML));
        try {
            AnchorPane root = loader.load();
            MainMenuViewController c = loader.getController();
            c.setController(this);
            viewController = c;
            Scene scene = new Scene(root);
            engine.setScene(scene);

        }
        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * exit Method for MenuController
     */

    public void exit() {
        //Do something on exiting state
    }

    /**
     * update method used for layout updates
     */
    @Override
    void update() {
        viewController.setLayout();
    }

    /**
     * restart the game, start Method for MenuController sets engine.Controller to PruchaseMenu
     */

    public void start() {
        engine.getModel().gameState.StartGame();
        engine.activatePurchaseMenuController();
    }

    /**
     * continues the game with the values before
     */
    public void continueGame() {
        engine.getModel().gameState.continueGame();
        engine.activatePurchaseMenuController();
    }

    /**
     * optionen Method for MenuController sets engine.Controller to OptionsMenu
     */

    public void options() {
        engine.getModel().gameState.Options();
        engine.activateOptionsMenuController();
    }

    /**
     * beenden Method for MenuController. Exits App with code 0
     */

    public void quit() {
        System.exit(0);
    }



}
