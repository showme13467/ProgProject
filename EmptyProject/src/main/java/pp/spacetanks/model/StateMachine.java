package pp.spacetanks.model;

import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.model.item.TankType;

public abstract class StateMachine extends State{
    protected State state;

    @Override
    public void repairAll() {
        state.repairAll();
    }

    @Override
    public void showHelpline() {
        state.showHelpline();
    }

    public StateMachine(){
        init();
    }

    public abstract void init();

    public void GotoState(State s) {
        state.exit();
        state = s;
        state.entry();
    }
    @Override
    public void entry() {
        state.entry();

    }

    @Override
    public void exit() {
        state.exit();
    }

    @Override
    public void moveLeft() {
        state.moveLeft();

    }

    @Override
    public void moveRight() {
        state.moveRight();
    }

    @Override
    public void remoteTrigger() {
        state.remoteTrigger();
    }

    @Override
    public void setAngle(double a) {
        state.setAngle(a);
    }

    @Override
    public void setPower(double p) {
        state.setPower(p);
    }

    @Override
    public void fire() {
        state.fire();
    }

    @Override
    public void setAmmo(ProjectileType p) {
        state.setAmmo(p);
    }

    @Override
    public void asteroidAttack() {
        state.asteroidAttack();
    }
    @Override
    public void update(){
        state.update();
    }
    @Override
    public void back(){state.back(); }
    @Override
    public void Options(){state.Options();}
    @Override
    public void StartGame(){state.StartGame();}
    @Override
    public void safeName(String name){state.safeName(name);}
    @Override
    public void chooseTank(TankType t){state.chooseTank(t);}
    @Override
    public void buy(){state.buy();}
    @Override
    public void sell(){state.sell();}
    @Override
    public void continueGame() {state.continueGame();}
    @Override
    public void finishAction() {state.finishAction();}

    public void repairChassi(){state.repairChassi();}
    public void repairChain() {state.repairChain();}
    public void repairAntenna() {state.repairAntenna();}
    public void repairCannon() {state.repairCannon();}
    public void refueling() {state.refueling();}
}
