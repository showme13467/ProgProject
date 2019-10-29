package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.Map;
import pp.spacetanks.model.item.*;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;

/**
 * A ballistic Projectile which will force all StationaryAsteroids in its radius to fall down.
 */
public class GravitationBomb extends ExplosiveProjectile {
    public GravitationBomb(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity) {
        super(model, type, pos, velocity);
    }

    @Override
    protected void dealRadiusDamage() {
        model.getMap().add(new Animation(model, StringProperty.gravBombAnimation,getRotation(), radius, getPos().add(DoubleVec.NULL), 64, 64, 8, 4, 32));
        Circle explosion =new Circle(getPos(),radius);
        Map map =model.getMap();
        for (StationaryAsteroid a:map.getStationaryAsteroids())
            if(explosion.collisionWith(a.shape)){
                a.destroy();
                map.add(new Asteroid(model,a.getPos(),DoubleVec.NULL,25));
            }
    }
}
