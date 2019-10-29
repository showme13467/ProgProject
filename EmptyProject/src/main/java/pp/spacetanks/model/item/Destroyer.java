package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.BoundingShape;
import pp.spacetanks.util.DoubleVec;

/**
 * Base class for damage dealing MapContent.
 */
public class Destroyer extends Moving {
    private final int damage;

    /**
     * Creates a new Destroyer
     * @param model     the GameModel
     * @param pos       the position
     * @param shape     the BoundingShape
     * @param velocity  the velocity
     * @param damage    the damage dealt to a tank
     */
    public Destroyer(GameModel model, DoubleVec pos, BoundingShape shape, DoubleVec velocity,int damage){
        super(model, pos, shape, velocity);
        this.damage = damage;
    }

    /**
     * Process a hit with an Asteroid
     * @param a the Asteroid
     */
    public void hit(Asteroid a){
        a.destroy();
        this.destroy();
    }
    /**
     * Process a hit with a StationaryAsteroid
     * @param a the StationaryAsteroid
     */
    public void hit(StationaryAsteroid a){
        a.destroy();
        this.destroy();
    }
    /**
     * Process a hit with a Planet
     * @param p the Planet
     */
    public void hit(Planet p){
        this.destroy();
    }
    /**
     * Process a hit with a TankComponent
     * @param cmp the TankComponent
     */
    public void hit(TankComponent cmp){
        cmp.takeDamage(damage);
        this.destroy();
    }
    /**
     * Process a hit with a Tank
     * @param tank the Tank
     */
    public void hit(Tank tank){
        for(TankComponent cmp:tank.getComponents())
            cmp.takeDamage(damage);
        this.destroy();
    }
}
