

package pp.spacetanks.view.ViewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import pp.spacetanks.controller.PurchaseMenuController;
import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.Tank;
import pp.spacetanks.model.item.TankType;

/**
 * controller for PurchaseMenu.fxml
 */
public class PurchaseMenuViewController {

    private PurchaseMenuController controller;
    private Tank tank;
    private GameModel model;
    private TankType tankChosen;
    private String player;
    private String TankZustand ;


    private int boughttank = 1; //1=M55, 2=Mars, 3=Haubitze

    @FXML
    private Text introText;

    @FXML
    private Text introTextPlayer1;

    @FXML
    private Text introTextPlayer2;

    @FXML
    private Button continueBtnMenu;

    @FXML
    private Button backBtn1;

    @FXML
    private TextField playerName1;

    @FXML
    private TextField playerName2;

    @FXML
    private Button m55Im;

    @FXML
    private Button marsIm;

    @FXML
    private Button panzerhaubtitzeIm;

    @FXML
    private Button continueBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button repairAntennaBtn;

    @FXML
    private Button repairChainBtn;

    @FXML
    private Button cannonRepairBtn;

    @FXML
    private Button chassiRepairBtn;

    @FXML
    private Button repairallBtn;

    @FXML
    private Button refuelTankBtn;

    @FXML
    private TextArea tankStatus;

    @FXML
    private TextArea tankInfo;

    @FXML
    private Label infoText;

    @FXML
    private Button tanksellbtn;

    @FXML
    private Button tankbuybtn;

    @FXML
    private Text hintText;

    @FXML
    private ProgressBar progressFuel;

    @FXML
    private ProgressBar progressChain;

    @FXML
    private ProgressBar progressCannon;

    @FXML
    private ProgressBar progressChassis;

    @FXML
    private ProgressBar progressAntenna;

    @FXML
    private Text textFuel;

    @FXML
    private Text textChain;

    @FXML
    private Text textCannon;

    @FXML
    private Text textChassis;

    @FXML
    private Text textAntenna;

    @FXML
    private Text fuelCosts;

    @FXML
    private Text chainCosts;

    @FXML
    private Text antennaCosts;

    @FXML
    private Text chassisCosts;

    @FXML
    private Text cannonCosts;

    @FXML
    private BorderPane purchaseAnchor;

    @FXML
    private ImageView purchaseMenBGImg;

    @FXML
    private AnchorPane innerAnchor;


    /**
     * save the player name typed in the text field
     */
    @FXML
    void savePlayer() {
        if (playerName1.getCharacters().length() > 0) {
            model.getPlayer1().setName(playerName1.getText());
            this.player = playerName1.getText();
        } else {
            model.getPlayer1().setName("Spieler 1");
            playerName1.setText("Spieler 1");
        }
        if(playerName2.getCharacters().length()>0){
            model.getPlayer2().setName(playerName2.getText());
            }
        else {
            model.getPlayer2().setName("Spieler 2");
            playerName2.setText("Spieler 2");
        }
    }

    /**
     * continue to buying the tanks
     */
    @FXML
    void toMenu (){
        savePlayer();
        update();
        setVisibleScene(true);
        controller.setPlayerName(player);
        infoText.setText(player + " sucht Panzer aus!");
    }

    /**
     * changes text in text field
     */
    @FXML
    void changeTextHaubitze() {
        tankChosen = TankType.PANZERHAUBITZE2000;
        setTankText();
        boughttank = 3;
    }

    private void setTankText() {
        String text =   "Name:               "+ tankChosen.name+" \n\n" +
                        "Wanne :             "+ tankChosen.healthChassi+" Leben \n" +
                        "Antenne :           "+ tankChosen.healthAntenna+" Leben \n" +
                        "Kanone :            "+ tankChosen.healthCannon+" Leben \n" +
                        "Kette :                "+ tankChosen.healthChain+" Leben \n" +
                        "Reichweite :       "+ tankChosen.fuelCapacity +" Liter \n" +
                        "Kosten :             "+ tankChosen.priceWithFuel+" BwCoins \n\n" +
                        "Verfügbares Geld:   " + model.getActivePlayer().getMoney() + " BwCoins";
        tankInfo.setText(text);
    }

    @FXML
    void changeTextM55() {
        tankChosen = TankType.M55;
        setTankText();
        boughttank = 1;
    }

    @FXML
    void changeTextMars() {
        tankChosen = TankType.MARS;
        setTankText();
        boughttank = 2;
    }

    /**
     * method to refuel tank if the "nachfüllen" button is pressed
     */
    @FXML
    void refuelTank() {
        update();
            if(tank==null)infoText.setText(player+" besitzt keinen Panzer!");
            else {
                Tank tank1 = model.getActivePlayer().getTank();
                if(model.getActivePlayer().getMoney() < tank1.refuelCost()) infoText.setText(player +  " hat zu wenig Geld!");
                else if(tank1.getRemainingFuel() == tank1.getFuelCapacity()) infoText.setText("Tank ist bereits voll!");
                else infoText.setText(player+ " hat Tank aufgefüllt!");
            }
            controller.refuelTank();
            update();
    }

    /**
     * method to repair the tank if the "alles Reparieren" button is pressed
     */
    @FXML
    void repairAll() {
        update();
            if(tank==null)infoText.setText(player+ " besitzt keinen Panzer!");
            else if(model.getActivePlayer().getMoney()<model.getActivePlayer().getTank().getOverallRepairCost())infoText.setText(player +" hat zu wenig Geld!");
            else if(model.getActivePlayer().getTank().getOverallRepairCost()==0) infoText.setText("Panzer ist nicht beschädigt!");
            else infoText.setText(player + " hat alles repariert!");
            controller.repairAll();
            update();
    }
    /**
     * method to repair the antenna if the "Antenne Reparieren" button is pressed
     */
    @FXML
    void repairAntenna() {
        update();
            if(tank==null)infoText.setText(player +" besitzt keinen Panzer!");
            else if(model.getActivePlayer().getMoney()<model.getActivePlayer().getTank().antennaRepairCost())infoText.setText(player +" hat zu wenig Geld!");
            else if(model.getActivePlayer().getTank().antennaRepairCost()==0) infoText.setText("Antenne ist nicht beschädigt!");
            else infoText.setText(player + " hat Antenne repariert!");
            controller.repairAntenna();
            update();
    }
    /**
     * method to repair the Cannon if the "Kanone Reparieren" button is pressed
     */
    @FXML
    void repairCannon() {
        update();
            if(tank==null)infoText.setText(player +" besitzt keinen Panzer!");
            else if(model.getActivePlayer().getMoney()<model.getActivePlayer().getTank().cannonRepairCost())infoText.setText(player+" hat zu wenig Geld!");
            else if(model.getActivePlayer().getTank().cannonRepairCost()==0) infoText.setText("Kanone ist nicht beschädigt!");
            else infoText.setText(player + " hat Kanone repariert!");
            controller.repairCannon();
            update();
    }
    /**
     * method to repair the chain if the "Kette Reparieren" button is pressed
     */
    @FXML
    void repairChain() {
        update();
            if(tank==null)infoText.setText(player +" besitzt keinen Panzer!");
            else if(model.getActivePlayer().getMoney()<model.getActivePlayer().getTank().chainRepairCost())infoText.setText( player +" hat zu wenig Geld!");
            else if(model.getActivePlayer().getTank().chainRepairCost()==0) infoText.setText("Kette ist nicht beschädigt!");
            else infoText.setText(player + " hat Kette repariert!");
            controller.repairChain();
            update();
    }
    /**
     * method to repair the chassi if the "Kette Reparieren" button is pressed
     */
    @FXML
    void repairChassi() {
        update();
        if(tank==null)infoText.setText(player +" besitzt keinen Panzer!");
        else if(model.getActivePlayer().getMoney()<model.getActivePlayer().getTank().chassisRepairCost())infoText.setText(player+" hat zu wenig Geld!");
        else if(model.getActivePlayer().getTank().chassisRepairCost()==0) infoText.setText("Wanne ist nicht beschädigt!");
        else infoText.setText(player +" hat Wanne repariert!");
        controller.repairChassi();
        update();
    }


    /**
     * the current player end its action and the next player is the current player  or the game will start after player 2
     */
    @FXML
    void nextPlayerStartGame() {
        controller.setPlayerName(player);
        update();
        if(tank != null) {
            if(!model.getActivePlayer().getTank().isDestroyed()){
             if (model.getActivePlayer() == model.getPlayer1()){
                controller.nextPlayerStartGame();
                update();
                if (player=="")infoText.setText("Spieler 2 sucht Panzer aus!");
                else infoText.setText(player +" verwaltet seinen Panzer!");
            }
            else {
                controller.startGame();
            }
        }
        }
        else  infoText.setText("Panzer wählen!");
    }

    /**
     * goes one step back
     */
    @FXML
    void back() {
    controller.back();
    update();
    infoText.setText(player +" verwaltet seinen Panzer!");
    }

    /**
     * buys the chosen tank
     */
    @FXML
    void buyTank(){
        update();
        int playerMoney = controller.getEngine().getModel().getActivePlayer().getMoney();
        if(model.getActivePlayer().getTank()==null) {
            switch (boughttank) {
                case 1:
                    changeTextM55();
                    if (playerMoney < 100)
                        infoText.setText(player +" hat nicht genügend Geld!");
                    else infoText.setText(player +" hat M55 gekauft!");
                    break;
                case 2:
                    changeTextMars();
                    if (playerMoney < 500)
                        infoText.setText(player+" hat nicht genügend Geld");
                    else infoText.setText(player +" hat MARS gekauft!");
                    break;
                case 3:
                    changeTextHaubitze();
                    if (playerMoney < 2500)
                        infoText.setText(player+" hat nicht genügend Geld!");
                    else infoText.setText(player+" hat Panzerhaubitze2000 gekauft!");
                    break;
            }
        }
        else infoText.setText(player+" hat schon einen Panzer!");
        controller.buyTank(tankChosen);
        update();
    }

    /**
     * sell the chosen tank
     */
    @FXML
    void sellTank() {
        update();
        if(tank!=null)infoText.setText(player+" hat Panzer verkauft!");
        else if(player!="") infoText.setText(player+" hat keinen Panzer!");
        else infoText.setText("Namen eingeben!");
        controller.sellTank();
        switch(boughttank) {
            case 1:
                changeTextM55();
                break;
            case 2:
                changeTextMars();
                break;
            case 3:
                changeTextHaubitze();
                break;
        }
        update();
    }

    /**
     * constructor ot the purchaseMenuViewController
     * @param controller
     * @param model
     */
    public void init(PurchaseMenuController controller, GameModel model){
        if(model.getPlayer1().getName() == "" && model.getPlayer2().getName()=="") {
            setVisibleScene(false);
            setProgressVisible(false);
        }
        else
            setVisibleScene(true);
        tankInfo.setEditable(false);
        tankStatus.setEditable(false);
        this.controller = controller;
        this.model = model;
        this.tank = model.getActivePlayer().getTank();
        this.player = model.getActivePlayer().getName();
        update();
        if (player=="") infoText.setText(player + "sucht Panzer aus!");
        else infoText.setText(player+" verwaltet seinen Panzer!");

    }


    /**
     * change text in the info text field
     */
    private void changeText(){
        if(tank != null) {
            Tank tank = model.getActivePlayer().getTank();
            TankZustand = "";
            setProgressVisible(true);
            progressFuel.setProgress(tank.getRemainingFuel()/tank.getFuelCapacity());
            progressChassis.setProgress((double)tank.getChassis().getRemainingHealth()/(double)tank.getChassis().maxHealth);
            progressChain.setProgress((double)tank.getChain().getRemainingHealth()/(double)tank.getChain().maxHealth);
            progressCannon.setProgress((double)tank.getCannon().getRemainingHealth()/(double)tank.getCannon().maxHealth);
            progressAntenna.setProgress((double)tank.getAntenna().getRemainingHealth()/(double)tank.getAntenna().maxHealth);

            fuelCosts.setText(String.valueOf(tank.refuelCost()) + " BwCoins");
            chassisCosts.setText(String.valueOf(tank.chassisRepairCost()) + " BwCoins");
            chainCosts.setText(String.valueOf(tank.chainRepairCost()) + " BwCoins");
            cannonCosts.setText(String.valueOf(tank.cannonRepairCost()) + " BwCoins");
            antennaCosts.setText(String.valueOf(tank.antennaRepairCost()) + " BwCoins");
            hintText.setText("Dein Panzer: "+tank.type.toString());
            hintText.setFont(Font.font(30));
        }
        else {TankZustand =  "  ░░░░█████ ]▄▄▄▄▄▄▄▄▃\n" +
                            "  ▂▄▅███████▅▄▃▂\n" +
                            " [██████████████].\n" +
                            "  ◥⊙▲⊙▲⊙▲⊙▲⊙▲⊙▲⊙◤\n\n" +
                            " Hier stehen später die Werte\n des Panzers";
            setProgressVisible(false);
            hintText.setText("Um einen Panzer zu kaufen, klicke auf einen Panzer und dann auf \"Panzer kaufen\".");
            hintText.setFont(Font.font(18));
        }
        tankStatus.setText(TankZustand);

    }

    /**
     * update method to update methods every frame
     */
    private void update(){
        setLayout();
        this.tank = model.getActivePlayer().getTank();
        this.player = model.getActivePlayer().getName();
        changeText();
        switch(boughttank){
            case 1: changeTextM55();
                break;
            case 2: changeTextMars();
                break;
            case 3: changeTextHaubitze();
                break;
        }

    }

    /**
     * method to set Progressbars to visible
     * @param b
     */

    private void setProgressVisible(boolean b){
        progressAntenna.setVisible(b);
        progressCannon.setVisible(b);
        progressChain.setVisible(b);
        progressChassis.setVisible(b);
        progressFuel.setVisible(b);

        textAntenna.setVisible(b);
        textCannon.setVisible(b);
        textChain.setVisible(b);
        textChassis.setVisible(b);
        textFuel.setVisible(b);

        antennaCosts.setVisible(b);
        cannonCosts.setVisible(b);
        chassisCosts.setVisible(b);
        chainCosts.setVisible(b);
        fuelCosts.setVisible(b);
    }

    private void setVisibleScene(boolean x){
        m55Im.setVisible(x);
        marsIm.setVisible(x);
        panzerhaubtitzeIm.setVisible(x);
        continueBtn.setVisible(x);
        repairAntennaBtn.setVisible(x);
        repairChainBtn.setVisible(x);
        repairallBtn.setVisible(x);
        cannonRepairBtn.setVisible(x);
        tankStatus.setVisible(x);
        tankInfo.setVisible(x);
        infoText.setVisible(x);
        tankbuybtn.setVisible(x);
        tanksellbtn.setVisible(x);
        chassiRepairBtn.setVisible(x);
        refuelTankBtn.setVisible(x);
        backBtn.setVisible(x);
        hintText.setVisible(x);

        introText.setVisible(!x);
        introTextPlayer1.setVisible(!x);
        introTextPlayer2.setVisible(!x);
        playerName1.setVisible(!x);
        playerName2.setVisible(!x);
        continueBtnMenu.setVisible(!x);
        backBtn1.setVisible(!x);
    }

    /**
     * method for setting the layout
     */
    public void setLayout() {
        Stage stageOfController = this.controller.getEngine().stage;
        purchaseAnchor.setPrefHeight(stageOfController.getHeight());
        purchaseAnchor.setPrefWidth(stageOfController.getWidth());
        purchaseMenBGImg.setFitHeight(stageOfController.getHeight());
        purchaseMenBGImg.setFitWidth(stageOfController.getWidth());
        innerAnchor.setLayoutX(stageOfController.getWidth()/2);
        innerAnchor.setLayoutY(stageOfController.getHeight()/2);
    }


}

