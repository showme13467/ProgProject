package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.Map;
import pp.spacetanks.model.item.*;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * Base class for exploding Projectiles with radius damage.
 */
public class ExplosiveProjectile extends Projectile {
    public final int radius;
    public final int radiusDamage;
    public final double power;

    public ExplosiveProjectile(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity){
        super(model,type, pos, velocity);
        this.radius = type.radius;
        this.radiusDamage = type.radiusDamage;
        this.power = type.rocketPower;
    }

    @Override
    public void hit(Asteroid a){
        model.notifyReceivers(Notification.ROCKET_IMPACT);
        super.hit(a);
        dealRadiusDamage();
    }
    @Override
    public void hit(StationaryAsteroid a){
        model.notifyReceivers(Notification.ROCKET_IMPACT);
        super.hit(a);
        dealRadiusDamage();
    }
    @Override
    public void hit(Planet p){
        model.notifyReceivers(Notification.ROCKET_IMPACT);
        super.hit(p);
        dealRadiusDamage();
    }
    @Override
    public void hit(TankComponent cmp){
        model.notifyReceivers(Notification.ROCKET_IMPACT);
        super.hit(cmp);
        dealRadiusDamage();
    }
    @Override
    public void hit(Tank tank){
        model.notifyReceivers(Notification.ROCKET_IMPACT);
        super.hit(tank);
        dealRadiusDamage();
    }

    protected void dealRadiusDamage(){
        model.getMap().add(new Animation(model, StringProperty.explosionAnimation,getRotation(), radius, getPos().add(DoubleVec.NULL), 256, 256, 7, 5, 45));
        Circle explosion =new Circle(getPos(),radius);
        Map map =model.getMap();
        for (Asteroid a:map.getAsteroids())
            if(explosion.collisionWith(a.shape)) a.destroy();
        for (StationaryAsteroid a:map.getStationaryAsteroids())
            if(explosion.collisionWith(a.shape)) a.destroy();
        for (Tank t:map.getTanks())
            for (TankComponent cmp:t.getComponents())
            if(explosion.collisionWith(cmp.shape)) cmp.takeDamage(radiusDamage);
    }
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
