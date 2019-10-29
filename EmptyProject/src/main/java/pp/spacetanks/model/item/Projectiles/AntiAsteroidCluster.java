package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * A ballistic Cluster that emits the carried Projectiles in a C shape.
 */
public class AntiAsteroidCluster extends ClusterRocket {
    public AntiAsteroidCluster(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity, ProjectileType load) {
        super(model, type, pos, velocity, load);
    }

    @Override
    public void remote() {
        double direction =  getVel().angle();
        for(int angle=-40;angle<=40;angle+=3) {
            load.makeProjectile(model, getPos(),DoubleVec.polar(1+angle*angle*0.0001,angle+direction));
        }
        destroy();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
