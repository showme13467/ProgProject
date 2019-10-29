package pp.spacetanks.model;

import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.model.item.TankType;

import java.util.ArrayList;
/** calls every called method for every parallel running and in regions saved StateMachine */
public abstract class OrthogonalState extends State{
    public ArrayList<StateMachine> regions = new ArrayList<>();


    @Override
    public void entry() {
        for(State s:regions){ s.entry();}
    }

    @Override
    public void exit() {
        for(State s:regions){s.exit();}
    }

    @Override
    public void moveLeft() {
        for (State s:regions){s.moveLeft();}
    }

    @Override
    public void moveRight() {
        for (State s:regions) {s.moveRight();}
    }

    @Override
    public void remoteTrigger() {
        for (State s:regions){s.remoteTrigger();}
    }

    @Override
    public void setAngle(double a) {
        for (State s:regions){s.setAngle(a);}
    }



    @Override
    public void setPower(double p) {
        for (State s:regions){s.setPower(p);}
    }

    @Override
    public void fire() {
        for (State s:regions){s.fire();}
    }

    @Override
    public void setAmmo(ProjectileType p){
        for (State s:regions){s.setAmmo(p);}
    }

    @Override
    public void asteroidAttack() {
        for (State s:regions){s.asteroidAttack();}
    }

    @Override
    public void update() {
        for (State s:regions){s.update();}
    }

    @Override
    public void showHelpline() {
        for (State s:regions)s.showHelpline();
    }

    @Override
    public void repairAll() { for (State s : regions) { s.repairAll(); }}

    public void back(){for (State s:regions){s.back();}}

    public void Options(){for (State s:regions){s.Options();}}

    public void StartGame(){for (State s:regions){s.StartGame();}}

    public void continueGame() {for (State s:regions){s.continueGame();}}

    public void safeName(String name){for (State s:regions){s.safeName(name);}}

    public void chooseTank(TankType t){for (State s:regions){s.chooseTank(t);}}

    public void buy(){for (State s:regions){s.buy();}}

    public void sell(){for (State s:regions){s.sell();}}

    public void finishAction() {for (State s:regions){s.finishAction();}}

    public void repairChassi() {for (State s:regions){s.repairChassi();}}

    public void repairChain()  {for (State s:regions){s.repairChain();}}

    public void repairAntenna() {for (State s:regions){s.repairAntenna();}}

    public void repairCannon() {for (State s:regions){s.repairCannon();}}

    public void refueling() {for (State s:regions){s.refueling();}}



}