package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;
import pp.spacetanks.view.Visitor;

/**
 * creates a TankComponent which is used to represent a part of a Tank
 */
public class TankComponent extends MapContent {
    public final int maxHealth;
    private int remainingHealth;
    protected final Tank tank;
    protected final DoubleVec relPos;

    /**
     *  used to create a damaged component
     * @param model           the game model whose game map will contain this item.
     * @param relPosition     the position of the component in relation to the tanks position.
     * @param maxHealth       maximum health of the component
     * @param remainingHealth remaining health of the component
     * @param height          the height of the component used to create a BoundingShape
     * @param width           the width of the component used to create a BoundingShape
     */
    public TankComponent(GameModel model, DoubleVec relPosition, double width,double height,int maxHealth,int remainingHealth,Tank tank){
        super(model,DoubleVec.NULL,new Rectangle(DoubleVec.NULL,width,height,tank.getRotation()));
        this.maxHealth = maxHealth;
        this.remainingHealth = Math.min(remainingHealth,maxHealth);
        this.relPos = relPosition;
        this.tank = tank;
        setPos(globalPos());
    }
    /**
     *  used to create a new component
     * @param model           the game model whose game map will contain this item.
     * @param relPosition     the position of the component in relation to the tanks position.
     * @param maxHealth       maximum health of the component.
     * @param height          the height of the component used to create a BoundingShape
     * @param width           the width of the component used to create a BoundingShape
     */
    public TankComponent(GameModel model, DoubleVec relPosition, double width,double height, int maxHealth,Tank tank){
        super(model,DoubleVec.NULL,new Rectangle(DoubleVec.NULL,width,height,tank.getRealRotation()));
        this.maxHealth = maxHealth;
        this.remainingHealth = maxHealth;
        this.relPos = relPosition;
        this.tank = tank;
        setPos(globalPos());
    }

    /**
     * restores the component to full health
     */
    public void repair(){remainingHealth = maxHealth;}

    /**
     *
     * @return  percentage of Damage 1 = destroyed 0 = full health
     */
    public double percentageDamaged(){return (double)(maxHealth-remainingHealth)/maxHealth;}

    /**
     *
     * @param damage  damage to be taken
     */
    public void takeDamage(int damage){
        if(remainingHealth-damage>0)remainingHealth -= damage;
        else{
            remainingHealth = 0;
        }
    }

    public int getRemainingHealth() {
        return remainingHealth;
    }

    /**
     *
     * @return  returns the global position calculated from the relative Position to the Tank and its angle.
     */
    private DoubleVec globalPos(){
      return  tank.getPos().add(DoubleVec.polar(relPos.length(),relPos.angle()+tank.getRealRotation()));
    }

    @Override
    public void update(double delta){
        setPos(globalPos());
        ((Rectangle)shape).setRotation(tank.getRealRotation());
        super.update(delta); //updates the shape pos
    }

    /**
     *
     * @return returns the general rotation of the item with which to draw the item.
     */
    @Override
    public double getRotation(){return tank.getRealRotation()+180;}

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public boolean isDestroyed() {
        return remainingHealth==0;
    }

    @Override
    public void destroy() {
        remainingHealth = 0;
    }
}
