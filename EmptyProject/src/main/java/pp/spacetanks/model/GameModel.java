package pp.spacetanks.model;

import javafx.animation.AnimationTimer;
import pp.spacetanks.model.item.Cannon;
import pp.spacetanks.model.item.Planet;
import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.notifications.NotificationReceiver;
import pp.spacetanks.properties.BooleanProperty;
import pp.spacetanks.properties.IntProperty;
import pp.spacetanks.util.StopWatch;
import pp.spacetanks.view.FieldSize;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;



/**
 * Represents the game model.
 */
public class GameModel {
    private static final Logger logger = Logger.getLogger(GameModel.class.getName());
    private final Properties props;
    private final List<NotificationReceiver> receivers = new ArrayList<>();

    private Player player1 = new Player("",null);
    private Player player2 = new Player("",null);
    private Player activePlayer = player1;
    private Player winner;
    private boolean lost = false;
    public final State gameState = new SpaceTanksAutomaton(this);
    public final int moneyPerTankHit = 50;
    public final int moneyPerAsteroidHit = 10;
    public final int moneyPerPlanetHit = 1;
    private boolean changedPlayerSinceLastCheck = false;
    private AnimationTimer endGameAnimation= new AnimationTimer() {
        private int counter = 0;
        private Cannon cannon;
        public void refresh(){
            cannon = winner.getTank().getCannon();
            cannon.setProjectile(ProjectileType.FEURWERKSRAKETE);
            cannon.setAngle(-45);
            cannon.setPower(1.5);
            counter =0;
        }

        @Override
        public void handle(long time) {
            counter++;
            if (cannon.getAngle()<45){
            if(counter >= 6) {
                counter = 0;
                cannon.setAngle(cannon.getAngle()+3);
                cannon.fire();
            }}
            else stop();
        }

        @Override
        public void start() {
            refresh();
            super.start();
        }
    };

    private StopWatch watchFlyingTime = new StopWatch();
    private StopWatch watchAtackTime = new StopWatch();
    private Map map;
    private boolean debugMode;
    private boolean muted;
    private int flyingTime;
    private int fieldSizeX;
    private int fieldSizeY;
    private int attackTimeAverage;
    private int attackTime;
    private double rest =0;
    private double step =0.015;
    private ProjectileType backupType;
    private double backupPower;
    private double backupAngle;

    private FieldSize fieldSize = FieldSize.MIDDLE;

    /**
     * Creates a game model
     *
     * @param props The properties storing the configuration of this game.
     */
    public GameModel(Properties props) {
        this.props = props;
        fieldSizeX = IntProperty.fieldSizeX.value(props,1000);
        fieldSizeY = IntProperty.fieldSizeY.value(props,1000);
        debugMode = BooleanProperty.debugMode.value(props,false);
        muted = BooleanProperty.muted.value(props,false);
        flyingTime = IntProperty.flyingTime.value(props,20);
        attackTimeAverage=IntProperty.attackTime.value(props,60);
    }

    /**
     * pays each player the money from the last game
     */
    public void pay(){
        player1.recieveMoney(payment(player1));
        player2.recieveMoney(payment(player2));
        player1.resetHits();
        player2.resetHits();
    }

    /**
     * Calculates the money the player p will earn for the last game
     * @param p the player to calculate the payment for
     * @return the amount of money the player will earn
     */
    public int payment(Player p){
        return  (p==winner?(player1==winner?player2.getTank().getType().price:player1.getTank().getType().price):0) +
                +moneyPerAsteroidHit*p.getAsteroidHits()
                +moneyPerPlanetHit*p.getPlanetHits()
                +moneyPerTankHit*p.getTankHits();
    }
    /**
     *
     * @return returns the active player
     */
    public Player getActivePlayer(){
        return activePlayer;
    }
    /**
     * Sets activePayer to next player.
     */
    public void nextPlayer(){
        if(activePlayer == player1) activePlayer = player2;
        else activePlayer = player1;
        changedPlayerSinceLastCheck =true;
    }

    public boolean changedPlayerSinceLastCheck() {
        if(changedPlayerSinceLastCheck){
            changedPlayerSinceLastCheck = false;
            return true;
        }
        return changedPlayerSinceLastCheck;
    }

    /**
     * Returns whether the debug mode is activated
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Switches the debug mode on or off depending on the specified value.
     *
     * @param debugMode if debug mode is switched on if and only if this value is true
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        BooleanProperty.debugMode.save(props,debugMode);
    }

    /**
     * Returns whether sound is muted.
     */
    public boolean isMuted() {
        return muted;
    }

    /**
     * Mutes or unmutes the sound depending on the specified value.
     *
     * @param muted the sound is muted if and only if this value is true.
     */
    public void setMuted(boolean muted) {
        this.muted = muted;
        BooleanProperty.muted.save(props,muted);
    }
    /**
     * Returns the flyingTime.
     */
    public int getFlyingTime() {
        return flyingTime;
    }

    /**
     * Sets the flyingTime to the specified value.
     *
     * @param flyingTime the time to be set.
     */
    public void setFlyingTime(int flyingTime) {
        this.flyingTime = flyingTime;
        IntProperty.flyingTime.save(props,flyingTime);
    }

    /**
     * Returns the configuration of this game.
     */
    public Properties getProperties() {
        return props;
    }

    /**
     * Returns the current game map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Sets the specified game map as the current one.
     *
     * @param map map
     */
    public void setMap(Map map) {
        this.map = map;
        notifyReceivers(Notification.MAP_UPDATE);
    }

    /**
     * Generates and sets a random game map.
     */
    public void loadRandomMap() {
        winner = null;
        lost = false;
        setMap(new MapCreator(this).makeRandomMap());
        map.getTanks().forEach((e)->e.getCannon().updateHelpline());
    }

    /**
     * Generates and sets the demo game map.
     */
    public void loadDemoMap() {
        winner = null;
        lost = false;
        setMap(new MapCreator(this).makeDemoMap());
        map.getTanks().forEach((e)->e.getCannon().updateHelpline());
    }

    /**
     * Called once per frame. This method triggers any update of the game model based on the elapsed time.
     *
     * @param deltaTime time in seconds since the last update call
     */
    public void update(double deltaTime) {
        double delta = deltaTime+rest;
        while(delta-step>0) {
            map.update(step);
            delta -= step;
        }
        rest = delta;
        gameState.update();
        updateWinner();
    }

    /**
     * updates the winner of the last game
     */
    private void updateWinner(){
        if(!lost){
            if(player1.hasLost()){
                winner = player2;
                lost = true;
            }
            if(player2.hasLost()){
                winner = player1;
                lost = true;
            }
        }
    }

    /**
     * Adds the specified receiver to the list of all event notification subscribers.
     *
     */
    public void addReceiver(NotificationReceiver receiver) {
        logger.fine(() -> "add receiver " + receiver);
        receivers.add(receiver);
    }

    /**
     * Removes the specified receiver from the list of all event notification subscribers.
     *
     */
    public void removeReceiver(NotificationReceiver receiver) {
        logger.fine(() -> "remove receiver " + receiver);
        receivers.remove(receiver);
    }

    /**
     * Notifies every registered
     *
     * @param notification The notification event communicated to every registered receiver.
     */
    public void notifyReceivers(Notification notification) {
        for (NotificationReceiver receiver : new ArrayList<>(receivers))
            receiver.notify(notification);
    }

    /**
     * @return the field size height
     */
    public int getFieldSizeY() {
        return fieldSizeY;
    }
    /**
     * @return the field size width
     */
    public int getFieldSizeX() {
        return fieldSizeX;
    }
    /**
     * @return whether the game is over
     */
    public boolean gameOver(){
        return (player1.hasLost()||player2.hasLost())&&map.getProjectiles().isEmpty();
    }
    /**
     * @return the first player
     */
    public Player getPlayer1() {
        return player1;
    }
    /**
     * @return the second player
     */
    public Player getPlayer2() {
        return player2;
    }
    /**
     * @return the inactive player
     */
    public Player getInactivePlayer(){
        if(activePlayer == player1)return player2;
        else return player1;
    }
    /**
     * stops and resets the flying time of the projectiles
     */
    public void stopFlyingTime(){
        watchFlyingTime.reset();
        watchFlyingTime.start();
    }

    /**
     * @return whether the flying time is over
     */
    public boolean flyingTimeOver(){
        if(watchFlyingTime.getTime()>=flyingTime){
            watchFlyingTime.reset();
            return true;
        }
        return false;
    }

    /**
     * starts the countdown for the next asteroid attack
     */
    public void attackWait(){
        watchAtackTime.start();
        attackTime = attackTimeAverage + (int)(Math.random()*20);
    }

    /**
     * @return whether the next asteroid attack should start
     */
    public boolean attackTime(){
        watchAtackTime.start();
        if(watchAtackTime.getTime()>=attackTime){
            watchAtackTime.reset();
            return true;
        }
        return false;
    }

    /**
     * pauses all stop watches for a pause
     */
    public void pauseStopWatches(){
        watchAtackTime.stop();
        watchFlyingTime.stop();
    }
    /**
     * continues all stop watches after a pause
     */
    public void continueStopWatches(){
        watchFlyingTime.start();
        watchAtackTime.start();
    }

    /**
     * Sets a new FieldSize
     * @param fieldSize the FieldSize to be set
     */
    public void setFieldSize(FieldSize fieldSize){
        this.fieldSize = fieldSize;
    }

    /**
     * @return the FieldSize
     */
    public FieldSize getFieldSize(){
        return fieldSize;
    }

    /**
     * starts the end game animation.
     */
    public void startEndGameAnimation(){
        Cannon cannon = winner.getTank().getCannon();
        backupType = cannon.getProjectileType();
        backupAngle =  cannon.getAngle();
        backupPower = cannon.getPower();
        endGameAnimation.start();
    }
    /**
     * stops the end game animation.
     */
    public void stopEndGameAnimation(){
        Cannon cannon = winner.getTank().getCannon();
        cannon.setProjectile(backupType);
        cannon.setAngle(backupAngle);
        cannon.setPower(backupPower);
    }

    /**
     * @return the winner of he last game
     */
    public Player getWinner() {
        return winner;
    }

    /**
     *  Starts a new game.
     */
    public void restart() {
        player1 = new Player("", null);
        player2 = new Player("", null);
        activePlayer = player1;
    }

    /**
     * @param attackTimeAverage sets the average time after which a new asteroid attack starts
     */
    public void setAttackTimeAverage(int attackTimeAverage){
        this.attackTimeAverage = attackTimeAverage;
    }

}
