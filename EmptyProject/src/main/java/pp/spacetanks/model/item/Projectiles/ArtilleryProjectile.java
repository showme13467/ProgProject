package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.*;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * Base class for ballistic Projectiles.
 */
public class ArtilleryProjectile extends Projectile {
    public ArtilleryProjectile(GameModel model,ProjectileType type, DoubleVec pos,DoubleVec velocity){
        super(model,type, pos, velocity);
    }
    @Override
    public void hit(Asteroid a){
        model.notifyReceivers(Notification.ARTILLERY_IMPACT);
        model.getMap().add(new Animation(model, StringProperty.asteroidDustAnimation,a.getRotation(),10,a.getPos(),128,128,8,8,17));
        super.hit(a);
    }
    @Override
    public void hit(StationaryAsteroid a){
        model.notifyReceivers(Notification.ARTILLERY_IMPACT);
        model.getMap().add(new Animation(model, StringProperty.asteroidDustAnimation,a.getRotation(),10,a.getPos(),128,128,8,8,17));
        super.hit(a);
    }
    @Override
    public void hit(Planet p){
        model.notifyReceivers(Notification.ARTILLERY_IMPACT);
        model.getMap().add(new Animation(model, StringProperty.planetHitAnimation,getRotation(),25,getPos(),128,128,8,8,64));
        super.hit(p);
    }
    @Override
    public void hit(TankComponent cmp){
        model.notifyReceivers(Notification.ARTILLERY_IMPACT);
        model.getMap().add(new Animation(model, StringProperty.metalHitAnimation,getRotation()+180,25,getPos(),1024,1024,4,4,16));
        super.hit(cmp);
    }
    @Override
    public void hit(Tank tank){
        model.notifyReceivers(Notification.ARTILLERY_IMPACT);
        super.hit(tank);
    }
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
