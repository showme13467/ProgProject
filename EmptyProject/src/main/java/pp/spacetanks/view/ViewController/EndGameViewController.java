package pp.spacetanks.view.ViewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pp.spacetanks.controller.PlayGameController;
import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.Player;

/**
 * Controller for EndScreen.fxml
 */
public class EndGameViewController {
    PlayGameController controller;

    @FXML
    private Label player1;


    @FXML
    private Label player2;

    @FXML
    private Label player1TankHits;

    @FXML
    private Label player2TankHits;

    @FXML
    private Label player1AsteroidsHits;

    @FXML
    private Label player2AsteroidsHits;

    @FXML
    private Label player1PlanetHits;

    @FXML
    private Label player2PlanetHits;

    @FXML
    private Label player1CurrentMoney;

    @FXML
    private Label player2CurrentMoney;

    @FXML
    private Label coinsPerTankHit;

    @FXML
    private Label coinsPerPlanetHit;

    @FXML
    private Label coinsPerAsteroidHit;

    /**
     * initializes the controller
     */
    @FXML
    public void init(){
        GameModel model = controller.getEngine().getModel();
        Player p1 = model.getPlayer1();
        Player p2 = model.getPlayer2();
        player1.setText(p1.getName());
        player2.setText(p2.getName());
        player1TankHits.setText(String.valueOf(p1.getTankHits()));
        player2TankHits.setText(String.valueOf(p2.getTankHits()));
        player1AsteroidsHits.setText(String.valueOf(p1.getAsteroidHits()));
        player2AsteroidsHits.setText(String.valueOf(p2.getAsteroidHits()));
        player1PlanetHits.setText(String.valueOf(p1.getPlanetHits()));
        player2PlanetHits.setText(String.valueOf(p2.getPlanetHits()));
        player1CurrentMoney.setText(String.valueOf(model.payment(p1)));
        player2CurrentMoney.setText(String.valueOf(model.payment(p2)));
        coinsPerAsteroidHit.setText(String.valueOf(model.moneyPerAsteroidHit));
        coinsPerPlanetHit.setText(String.valueOf(model.moneyPerPlanetHit));
        coinsPerTankHit.setText(String.valueOf(model.moneyPerTankHit));
    }

    /**
     * action of continue button
     */
    @FXML
    public void endScreenContinue(){
        controller.endEndScreen();
    }

    /**
     * sets the controller which controls the engine and model
     * @param controller
     */
    public void setController(PlayGameController controller){
        this.controller = controller;
    }

}
