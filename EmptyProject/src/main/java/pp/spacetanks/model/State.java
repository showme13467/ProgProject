package pp.spacetanks.model;

import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.model.item.TankType;
/**all methods used in the SpaceTanksAutomaton*/

public abstract class State {
    public void showHelpline(){}
    public void entry(){}
    public void exit(){}
    public void moveLeft(){}
    public void moveRight(){}
    public void remoteTrigger(){}
    public void setAngle(double a){}
    public void setPower(double p){}
    public void fire() {}
    public void setAmmo(ProjectileType p){}
    public void asteroidAttack(){}
    public void update(){}
    public void back(){}
    public void Options(){}
    public void StartGame(){}
    public void continueGame() {}
    public void safeName(String name){}
    public void chooseTank(TankType t){}
    public void buy(){}
    public void sell(){}
    public void finishAction() {}
    public void repairChassi() {}
    public void repairChain() {}
    public void repairAntenna() {}
    public void repairCannon() {}
    public void refueling() {}
    public void repairAll() {}
}




