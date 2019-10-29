package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;
/**
 * A ballistic Projectile.
 * On remoting it will fall according to gravity.
 */
public class DropExplosiveProjectile extends ExplosiveProjectile {
    private boolean fall=false;
    public DropExplosiveProjectile(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity) {
        super(model, type, pos, velocity);
    }

    @Override
    public void remote() {
        if(!fall)setVel(DoubleVec.NULL);
        fall = true;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
