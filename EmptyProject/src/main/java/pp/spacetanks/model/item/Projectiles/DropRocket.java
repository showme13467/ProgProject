package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;
/**
 * A rocket Projectile.
 * On remoting it will fall according to gravity.
 */
public class DropRocket extends ExplosiveProjectile{
    private boolean fall=false;
    public DropRocket(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity) {
        super(model, type, pos, velocity);
    }

    @Override
    protected void move(double t) {
        if(!fall)setVel(getVel().add(DoubleVec.polar(power,getVel().angle())));
        super.move(t);
    }

    @Override
    public void remote() {
        if(!fall)setVel(DoubleVec.NULL);
        fall=true;
    }


    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
