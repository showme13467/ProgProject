package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

import java.util.ArrayList;

/**
 *  creates a Tank which can be controlled by a Player
 */
public class Tank extends Orbiting {
    private double fuelRate=2; // consumption for roughly 1 pixel movement
    private double fuelPrice=0.1;
    private double moveDistance = 0.5;

    private TankComponent chain;
    private TankComponent chassis;
    private TankComponent antenna;
    private Cannon cannon;
    private FuelTank fuelTank;
    public TankType type;
    private ArrayList<TankComponent> collidables = new ArrayList<>(4);

    /**
     * Creates a new Tank
     * @param model the GameModel
     * @param angle the position of the tank on the planet in degree
     * @param type  the TankType to be represented
     */
    public Tank(GameModel model, double angle,TankType type){
        super(model,null,0,angle);
        this.type = type;
        chain = new TankComponent(model,type.relPosChain,type.chainWidth,type.chainHeight,type.healthChain,this);
        chassis = new TankComponent(model,type.relPosChassi,type.chassiWidth,type.chassiHeight,type.healthChassi,this);
        antenna = new TankComponent(model,type.relPosAntenna,type.antennaWidth,type.antennaHeight,type.healthAntenna,this);
        cannon = new Cannon(model,type.relPosCannon,type.cannonWidth,type.cannonHeight,type.healthCannon,this);
        fuelTank = new FuelTank(type.fuelCapacity);
        collidables.add(chain);
        collidables.add(chassis);
        collidables.add(antenna);
        collidables.add(cannon);
    }

    /**
     *  Should not be used
     * @param other the item which is checked for a collision
     * @return
     */
    @Override
    public boolean collisionWith(MapContent other){
        for(TankComponent component:collidables){
            if(component.collisionWith(other)) return true;
        }
        return false;
    }

    /**
     * @return whether the tank can remotely trigger a remote projectile
     */
    public boolean canRemote(){return !antenna.isDestroyed();}

    /**
     * @return whether the tank can move
     */
    public boolean canMove(){return !chain.isDestroyed()&&fuelTank.getRemainingFuel()>=fuelRate;}

    /**
     * move the tank clockwise around the planet
     */
    public void moveRight(){
        increaseRotationOrbiting(moveDistance*360/(Math.PI*2*getPlanet().radius));
        fuelTank.consumeFuel(fuelRate);
        cannon.updateHelpline();
    }

    /**
     * move the tan counter clockwise around the planet
     */
    public void moveLeft(){
        increaseRotationOrbiting(-moveDistance*360/(Math.PI*2*getPlanet().radius));
        fuelTank.consumeFuel(fuelRate);
        cannon.updateHelpline();
    }

    /**
     * @return  the cost to repair all TankComponents
     */
    public int getOverallRepairCost(){
       return (int)(type.price/4*(antenna.percentageDamaged()+cannon.percentageDamaged()+ chassis.percentageDamaged()+chain.percentageDamaged()));
    }

    /**
     * @return the TankType represented by this tank
     */
    public TankType getType(){return type;}

    /**
     * @return the cost to refuel the tanks tank
     */
    public int refuelCost(){return (int)(fuelTank.fuelNeeded()*fuelPrice);}

    /**
     * @return the cost to repair the cannon
     */
    public int cannonRepairCost(){return (int)(cannon.percentageDamaged()*type.price/4);}
    /**
     * @return the cost to repair the chassis
     */
    public int chassisRepairCost(){return (int)(chassis.percentageDamaged()*type.price/4);}
    /**
     * @return the cost to repair the chain
     */
    public int chainRepairCost(){return (int)(chain.percentageDamaged()*type.price/4);}
    /**
     * @return the cost to repair the antenna
     */
    public int antennaRepairCost(){return (int)(antenna.percentageDamaged()*type.price/4);}

    /**
     * refuel the tank
     */
    public void refuel(){fuelTank.refuel();}

    /**
     * repairs the tanks cannon
     */
    public void repairCannon(){cannon.repair();}
    /**
     * repairs the tanks chassis
     */
    public void repairChassis(){chassis.repair();}
    /**
     * repairs the tanks chain
     */
    public void repairChain(){chain.repair();}
    /**
     * repairs the tanks antenna
     */
    public void repairAntenna(){antenna.repair();}
    /**
     * repairs the whole tank
     */
    public void repairAll(){
        repairAntenna();
        repairCannon();
        repairChain();
        repairChassis();
    }

    /**
     *
     * @return an Array containing Cannon,Antenna,Chassis,Chain in this order
     */
    public TankComponent[] getComponents(){
        TankComponent[] cmp =  {cannon,antenna, chassis,chain};
        return cmp;
    }

    /**
     * @return  the chain of the tank
     */
    public TankComponent getChain() { return chain; }
    /**
     * @return  the cannon of the tank
     */
    public Cannon getCannon() { return cannon; }
    /**
     * @return  the chassis of the tank
     */
    public TankComponent getChassis() { return chassis; }
    /**
     * @return  the antenna of the tank
     */
    public TankComponent getAntenna() { return antenna; }

    //cannon methods

    /**
     * fires the projectile selected by tha cannon
     */
    public void fire(){
        cannon.fire();
    }

    /**
     * @return the price of the projectile selected by tha cannon
     */
    public int projectilePrice(){return cannon.projectilePrice();}

    /**
     * @return used to get the angle between cannon and tank
     */
    public double getAngle() {return cannon.getAngle(); }

    /**
     * @param angle sets this angle as the new angle between cannon and tank
     */
    public void setAngle(double angle) {cannon.setAngle(angle); }

    /**
     * Sets the cannons power
     * @param power the power to be set
     */
    public void setPower(double power) {cannon.setPower(power);}
    /**
     * @return  the power of the cannon
     */
    public double getPower() {return cannon.getPower();}
    /**
     * @return  the remaining fuel of the tank
     */
    public double getRemainingFuel(){
        return fuelTank.getRemainingFuel();
    }
    /**
     * @return  the maximum fuel of the tank
     */
    public int getFuelCapacity(){
        return fuelTank.maxCapacity;
    }
    /**
     * Sets a new ProjectileType
     * @param projectile the ProjectileType to be set
     */
    public void setProjectile(ProjectileType projectile) {cannon.setProjectile(projectile);}

    /**
     * @return  the correct rotation of the tank
     */
    public double getRealRotation(){return getRotation()+90;}

    @Override
    public void update(double delta){
        setPos(planet.getPos().add(DoubleVec.polar(distance+planet.radius,getRotation())));
        //no super.update allowed because shape is null
        chassis.update(delta);
        chain.update(delta);
        cannon.update(delta);
        antenna.update(delta);
    }

    /**
     * Set the position of the tank on a new map
     * @param planet    the planet on which the tank sits
     * @param angle     the position of the tank on the planet in degree
     */
    public void setOnMapCreation(Planet planet,double angle){
        setPlanet(planet);
        setRotationOrbiting(angle);
    }

    /**
     * Change whether a Helpline is drawn or not.
     */
    public void changeHelpline(){
        cannon.changeHelpline();
    }

    @Override
    public boolean isDestroyed(){
        return chassis.isDestroyed()||cannon.isDestroyed();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
        cannon.accept(v);
    }
}
