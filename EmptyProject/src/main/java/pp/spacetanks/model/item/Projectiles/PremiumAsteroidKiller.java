package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.Map;
import pp.spacetanks.model.item.*;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * Projectile that explodes and deals no damage to a Tank and only destroys Asteroids.
 * On first remoting it will fall according to gravity.
 * On second remoting it will explode.
 */
public class PremiumAsteroidKiller extends ExplosiveProjectile {
    private Boolean remoted = false;
    public PremiumAsteroidKiller(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity) {
        super(model, type, pos, velocity);
    }

    @Override
    protected void dealRadiusDamage() {
        model.getMap().add(new Animation(model, StringProperty.explosionAnimation,getRotation(), radius, getPos().add(DoubleVec.NULL), 256, 256, 7, 5, 45));
        Circle explosion =new Circle(getPos(),radius);
        Map map =model.getMap();
        for (Asteroid a:map.getAsteroids())
            if(explosion.collisionWith(a.shape)) a.destroy();
        for (StationaryAsteroid a:map.getStationaryAsteroids())
            if(explosion.collisionWith(a.shape)) a.destroy();
    }

    @Override
    public void hit(Planet p) {
        dealRadiusDamage();
        destroy();
    }

    @Override
    public void hit(TankComponent cmp) {
        dealRadiusDamage();
        destroy();
    }

    @Override
    public void hit(Tank tank) {
        destroy();
    }

    @Override
    public void remote() {
        if(!remoted){
            setVel(DoubleVec.NULL);
            remoted = true;
        }
        else{
            dealRadiusDamage();
            destroy();
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
