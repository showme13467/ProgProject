package pp.spacetanks.model;

import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.model.item.Tank;
import pp.spacetanks.model.item.TankType;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.model.item.Projectiles.Projectile;

/**
 * Represents the entire game Automaton and is a StateMachine.
 */
public class SpaceTanksAutomaton extends StateMachine {
    protected GameModel model;
    private void purchaseToMatch(){GotoState(new Match());}
    private void matchToPurchaseMenu(){GotoState(new PurchaseMenu());}
    public SpaceTanksAutomaton(GameModel model) {
        init();
        this.model = model;
    }
/**
 * init method to initialize the Automaton with the Start State
 */
    @Override
    public void init() {
        state = new MainMenu();
        state.entry();
    }

    private class MainMenu extends State {
        public void Options() {
            GotoState(new OptionsMenu());
        }

        public void continueGame() {
            GotoState(new PurchaseMenu());
        }

        public void StartGame() {
            model.restart();
            GotoState(new PurchaseMenu());
        }

    }

    private class OptionsMenu extends State {
        public void back() {
            GotoState(new MainMenu());
        }
    }

    private class PurchaseMenu extends StateMachine {
        @Override
        public void init() {
            if (model.getActivePlayer().getName().length() !=0) {
                if (model.getActivePlayer().getTank() == null) {
                    state = new HasNoTank();
                    state.entry();
                } else {
                    state = new HasTank();
                    state.entry();
                }
            } else {
                state = new enterName();
                state.entry();
            }
        }

        @Override
        public void back() {
            if(model.getActivePlayer()==model.getPlayer2()) {
                model.nextPlayer();
                GotoState(new PurchaseMenu());
            }
            else GotoState(new MainMenu());
        }

        private class enterName extends State {
            @Override
            public void safeName(String name) {
                model.getActivePlayer().setName(name);


                GotoState(new HasNoTank());
            }
        }

        private class HasNoTank extends State {

            @Override
            public void chooseTank(TankType t) {
                if ((model.getActivePlayer().getMoney()) >= (t.priceWithFuel)) {
                    model.getActivePlayer().payMoney(t.priceWithFuel);
                    model.getActivePlayer().setTank(new Tank(model, 0, t));
                    GotoState(new HasTank());
                } else {
                    state.entry();
                }

            }
        }

        private class HasTank extends State {

            public void finishAction() {
                if (model.getActivePlayer() == model.getPlayer1()) {
                    model.nextPlayer();
                    GotoState(new PurchaseMenu());
                } else {
                    model.nextPlayer(); //Player one starts the game
                    model.loadRandomMap();
                    purchaseToMatch();
                }
            }

            public void repairChain() {
                if ((model.getActivePlayer().getMoney() < (model.getActivePlayer().getTank().chainRepairCost()))) {
                    state.entry();
                } else {
                    model.getActivePlayer().payMoney((model.getActivePlayer().getTank().chainRepairCost()));
                    model.getActivePlayer().getTank().repairChain();
                    state.entry();
                }
            }

            public void repairChassi() {
                if ((model.getActivePlayer().getMoney() < (model.getActivePlayer().getTank().chassisRepairCost()))) {
                    state.entry();
                } else {
                    model.getActivePlayer().payMoney((model.getActivePlayer().getTank().chassisRepairCost()));
                    model.getActivePlayer().getTank().repairChassis();
                    state.entry();
                }

            }

            public void repairAntenna() {
                if ((model.getActivePlayer().getMoney() < (model.getActivePlayer().getTank().antennaRepairCost()))) {
                    state.entry();
                } else {
                    model.getActivePlayer().payMoney((model.getActivePlayer().getTank().antennaRepairCost()));
                    model.getActivePlayer().getTank().repairAntenna();
                    state.entry();
                }
            }

            public void repairCannon() {
                if ((model.getActivePlayer().getMoney()< (model.getActivePlayer().getTank().cannonRepairCost())) ) {
                    state.entry();
                } else {
                    model.getActivePlayer().payMoney((model.getActivePlayer().getTank().cannonRepairCost()));
                    model.getActivePlayer().getTank().repairCannon();
                    state.entry();
                }
            }

            public void refueling() {
                if ((model.getActivePlayer().getMoney()) < (model.getActivePlayer().getTank().refuelCost()) ){
                    state.entry();
                }
                else {
                    model.getActivePlayer().payMoney(model.getActivePlayer().getTank().refuelCost());
                    model.getActivePlayer().getTank().refuel();
                    state.entry();
                }
            }
            public void repairAll() {
                if ((model.getActivePlayer().getMoney()< (model.getActivePlayer().getTank().getOverallRepairCost())) ) {
                    state.entry();
                } else {
                    model.getActivePlayer().payMoney((model.getActivePlayer().getTank().getOverallRepairCost()));
                    model.getActivePlayer().getTank().repairAll();
                    state.entry();
                }

            }

            public void sell() {
                Tank tank = model.getActivePlayer().getTank();
                model.getActivePlayer().recieveMoney(model.getActivePlayer().getTank().type.priceWithFuel-tank.getOverallRepairCost() - tank.refuelCost());
                model.getActivePlayer().setTank(null);
                GotoState(new HasNoTank());
            }


        }
    }

    private class Asteroids extends StateMachine {
        public void init() {
            state = new NoAsteroidsRemaining();
            state.entry();
        }

        private class NoAsteroidsRemaining extends State {

            public void entry() {
                if (model.attackTime()) {
                    model.getMap().asteroidAttack();
                    GotoState(new AsteroidsFlying());
                }
            }

            public void update() {
                state.entry();
            }
        }

        private class AsteroidsFlying extends State {

            public void update() {
                this.entry();
            }

            public void entry() {

                if (model.gameOver()) {
                    if(model.getPlayer2()==model.getActivePlayer()) model.nextPlayer();
                    model.notifyReceivers(Notification.BACKGROUND_MUSIC);
                    matchToPurchaseMenu();

                } else if (model.getMap().getAsteroids().isEmpty()) {
                    model.attackWait();
                    GotoState(new NoAsteroidsRemaining());

                }
            }

        }


    }


    private class MatchRound extends StateMachine {
        public void init() { state = new turn();
        state.entry();
        }

        private class turn extends State {
            public void entry() {
                if (model.gameOver()) {
                    if(model.getPlayer2()==model.getActivePlayer())model.nextPlayer();
                    model.notifyReceivers(Notification.BACKGROUND_MUSIC);
                    matchToPurchaseMenu();
                }
            }



            @Override
            public void showHelpline() {
                model.getActivePlayer().getTank().changeHelpline();
            }

            public void moveLeft() {
                if (model.getActivePlayer().getTank().canMove()) {
                    model.getActivePlayer().getTank().moveLeft();
                }
            }

            public void moveRight() {
                if (model.getActivePlayer().getTank().canMove()) {
                    model.getActivePlayer().getTank().moveRight();
                }
            }

            public void update() {
                this.entry();
            }

            public void fire() {
                if(model.getActivePlayer().canFire()){
                    model.stopFlyingTime();
                    model.getActivePlayer().fire();
                    GotoState(new ProjectileFlying());
                }
            }
            public void setAmmo(ProjectileType p){
                model.getActivePlayer().getTank().setProjectile(p);
            }

            public void setPower(double p) {
                model.getActivePlayer().getTank().setPower(p);

            }
            public void setAngle(double a) {
                model.getActivePlayer().getTank().setAngle(a);
            }

        }

        public class ProjectileFlying extends State{
            public void entry(){
                if (model.getMap().getProjectiles().isEmpty() || model.flyingTimeOver()) {
                    model.getActivePlayer().addMiss(model.getMap().getProjectiles().size());
                    model.getMap().clearProjectiles();
                    model.nextPlayer();
                    GotoState(new turn());
                }
                if (model.gameOver()) {
                    if(model.getPlayer2()==model.getActivePlayer())model.nextPlayer();
                    model.notifyReceivers(Notification.BACKGROUND_MUSIC);
                    matchToPurchaseMenu();
                }

            }
            public void remoteTrigger(){
                if(model.getActivePlayer().getTank().canRemote()){
                    final Projectile[] projectiles = model.getMap().getProjectiles().toArray(new Projectile[model.getMap().getProjectiles().size()]);
                    for(Projectile p : projectiles) {p.remote();}
                }

            }

            @Override
            public void update(){entry();}


        }

    }

    private class Match extends OrthogonalState {
        public Match(){
            regions.add(new Asteroids());
            regions.add(new MatchRound());
        }

    }
}
