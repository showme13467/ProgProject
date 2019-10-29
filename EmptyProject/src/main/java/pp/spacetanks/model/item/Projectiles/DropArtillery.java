package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.DoubleVec;

/**
 * A ballistic Projectile.
 * On remoting it will fall according to gravity.
 */
public class DropArtillery extends ArtilleryProjectile{
    private boolean fall=false;
    public DropArtillery(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity) {
        super(model, type, pos, velocity);
    }

    @Override
    public void remote() {
        if(!fall)setVel(DoubleVec.NULL);
        fall = true;
    }
}
