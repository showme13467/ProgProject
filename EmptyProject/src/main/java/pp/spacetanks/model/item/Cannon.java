package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;
import pp.spacetanks.view.Visitor;
import java.util.ArrayList;

/**
 * Representing the Cannon of a Tank.
 */
public class Cannon extends TankComponent {
    private double angle=0;
    private double power=1.0;
    private double width;
    private ProjectileType projectile = ProjectileType.initProjectile();

    private boolean showHelpline = false;
    private boolean helplineNeedsUpdate = true;

    private ArrayList<HelplineElement> helpline =new  ArrayList();
    private Helpline helplineGenerator = new Helpline(this);

    /**
     *  used to create a damaged component
     * @param model           the game model whose game map will contain this item.
     * @param relPosition     the position of the component in relation to the tanks position.
     * @param maxHealth       maximum health of the component
     * @param height          the height of the component used to create a BoundingShape
     * @param width           the width of the component used to create a BoundingShape
     * @param tank            the tank in which the cannon is stored used to calculate the correct fire angle
     */
    public Cannon(GameModel model, DoubleVec relPosition,double width, double height, int maxHealth,Tank tank){
        super(model, relPosition, width, height,maxHealth,tank);
        this.width =width;
    }
    /**
     *  used to create a damaged component
     * @param model           the game model whose game map will contain this item.
     * @param relPosition     the position of the component in relation to the tanks position.
     * @param maxHealth       maximum health of the component
     * @param remainingHealth remaining health of the component
     * @param height          the height of the component used to create a BoundingShape
     * @param width           the width of the component used to create a BoundingShape
     * @param tank            the tank in which the cannon is stored used to calculate the correct fire angle
     */
    public Cannon(GameModel model, DoubleVec relPosition, int width, int height, int maxHealth,int remainingHealth,Tank tank){
        super(model, relPosition, width, height, maxHealth,remainingHealth,tank);
        this.width =width;
    }

    /**
     *
     * @return used to get the angle between cannon and tank
     */
    public double getAngle() {
        return angle;
    }

    /**
     *
     * @param angle sets this angle as the new angle between cannon and tank
     */
    public void setAngle(double angle) {
        if(this.angle!=angle)helplineNeedsUpdate = true;
        this.angle = angle;
        setPos(globalPos());
        ((Rectangle)shape).setRotation(getRotation());
    }

    /**
     * Sets the power of the cannon
     * @param power the power to be set
     */
    public void setPower(double power) {
        if(power>=0.1&&power<=5){
            if(power!=this.power)helplineNeedsUpdate = true;
            this.power = power;
        }
    }

    /**
     * @return  the power of the cannon
     */
    public double getPower() {
        return power;
    }

    /**
     * @return the price of the selected Projectile
     */
    public int projectilePrice(){
        if(projectile != null){
            return projectile.price;
        }
        else return 0;
    }

    /**
     * Selects the ProjectileType
     * @param projectile    the ProjectileType to be selected
     */
    public void setProjectile(ProjectileType projectile) {
        this.projectile = projectile;
        helplineNeedsUpdate = true;
    }

    /**
     * Calculates the position where to place the fired projectile
     * @return the position
     */
    DoubleVec projectilePosition(){
       return globalPos().add(DoubleVec.polar(projectile.width*0.51 +width*0.51,getRotation()));
    }

    /**
     * Calculates the velocity with which to fire the projectile
     * @return
     */
    DoubleVec projectileVelocity(){
        return DoubleVec.polar(power,getRotation());
    }

    /**
     * fires the selected Projectile
     */
    public void fire() {
        if (projectile != null) {
            model.getMap().add(new Animation(model, StringProperty.shotAnimation,getRotation()-90,12,globalPos().add(DoubleVec.polar( 12+width*0.51,getRotation())),128,128,4,4,15));
            projectile.makeProjectile(model, projectilePosition(),projectileVelocity());
            showHelpline = false;
            updateHelpline();
        }
    }

    /**
     *
     * @return  returns the global position calculated from the relative Position to the Tank and its angle.
     */
    private DoubleVec globalPos(){
        return  tank.getPos().add(DoubleVec.polar(relPos.length(),relPos.angle()+tank.getRealRotation())).add(DoubleVec.polar(width/2,getRotation()));
    }


    @Override
    public void update(double delta){
        setPos(globalPos());
        ((Rectangle)shape).setRotation(getRotation());
        if(!ProjectileType.getAllTypes(model).contains(projectile)&&
                projectile!=ProjectileType.FEURWERKSRAKETE&&
                model.getActivePlayer().getTank().getCannon()==this)setProjectile(ProjectileType.ARTILLERIEGESCHOSS);
       if(helplineNeedsUpdate&&showHelpline)updateHelpline();
    }

    @Override
    public void setPos(DoubleVec pos) {
        if(pos.x != this.getPos().x || pos.y != this.getPos().y)helplineNeedsUpdate = true;
        super.setPos(pos);
    }

    /**
     *
     * @return returns the general rotation of the item with which to draw the item.
     */
    @Override
    public double getRotation(){return tank.getRotation()+angle;}//not realRotation its corrected with the angle...

    /**
     * Is used to change whether a Helpline is drawn or not.
     */
    public void changeHelpline(){
        showHelpline = !showHelpline;
    }

    /**
     * Generates a new Helpline.
     */
    public void updateHelpline(){
        helpline.clear();
        helplineGenerator.reset();
        for(HelplineElement e: helplineGenerator){
            helpline.add(e);
        }
        helplineNeedsUpdate = false;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
        if(showHelpline)helpline.forEach((e)->e.accept(v));
    }

    /**
     * @return  the selected ProjectileType
     */
    public ProjectileType getProjectileType() {
        return projectile;
    }

}
