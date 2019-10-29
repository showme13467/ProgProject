package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.Animation;
import pp.spacetanks.model.item.Planet;
import pp.spacetanks.model.item.Tank;
import pp.spacetanks.model.item.TankComponent;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;
/**
 * Projectile that deals no damage to a Tank and only destroys Asteroids.
 * On remoting it will fall according to gravity.
 */
public class AsteroidKiller extends ArtilleryProjectile {
    private Boolean remoted = false;
    public AsteroidKiller(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity) {
        super(model, type, pos, velocity);
    }

    @Override
    public void remote() {
       if(!remoted){
           setVel(DoubleVec.NULL);
           remoted = true;
       }
    }

    @Override
    public void hit(TankComponent cmp) {
        model.getMap().add(new Animation(model, StringProperty.metalHitAnimation,getRotation()+180,25,getPos(),1024,1024,4,4,16));
        destroy();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
