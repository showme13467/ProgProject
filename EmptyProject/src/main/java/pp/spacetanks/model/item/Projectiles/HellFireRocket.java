package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.*;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;
import pp.spacetanks.view.Visitor;

/**
 * A special, very powerful HeatSeeker.
 */
public class HellFireRocket extends HeatSeeker {
    public HellFireRocket(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity) {
        super(model, type, pos, velocity);
    }

    @Override
    protected boolean hasPointVision(DoubleVec p) {
        DoubleVec sub = p.sub(getPos());
        final Rectangle lineOfSight = new Rectangle(getPos().add(sub.div(2)),sub.length(),0,sub.angle());
        for(Planet a :model.getMap().getPlanets()){
            if (lineOfSight.collisionWith(a.shape)) return false;
        }
        return true;
    }

    @Override
    public void hit(Asteroid a) {
        model.notifyReceivers(Notification.ROCKET_IMPACT);
        a.destroy();
        dealRadiusDamage();
    }

    @Override
    public void hit(StationaryAsteroid a) {
        model.notifyReceivers(Notification.ROCKET_IMPACT);
        a.destroy();
        dealRadiusDamage();
    }

    @Override
    public void hit(TankComponent cmp) {
        model.notifyReceivers(Notification.ROCKET_IMPACT);
        cmp.destroy();
        destroy();
        dealRadiusDamage();
    }
    @Override
    protected void dealRadiusDamage(){
        DoubleVec sum = getGravForce();
        model.getMap().add(new Animation(model, StringProperty.hellfireAnimation,sum.angle()-90, radius, getPos().add(DoubleVec.NULL), 100, 100, 3, 3, 8));
    }

    private DoubleVec getGravForce() {
        DoubleVec sum = DoubleVec.NULL;
        for (Planet p : model.getMap().getPlanets())
            sum = sum.add(p.getGravityForceMultByGravConst(this));
        return sum;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void remote() {
        if(model.getMap().getProjectiles().size()<100){destroy();
        type.makeProjectile(model,getPos(),DoubleVec.polar(getVel().length(), getVel().angle()+5));
        type.makeProjectile(model,getPos(),DoubleVec.polar(getVel().length(), getVel().angle()-5));}
    }
}
