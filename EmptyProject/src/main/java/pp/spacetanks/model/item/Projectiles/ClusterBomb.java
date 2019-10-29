package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;
/**
 * A ballistic Cluster that emits the carried Projectiles in an O shape.
 */
public class ClusterBomb extends ArtilleryProjectile {
    private ProjectileType load;
    public ClusterBomb(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity,ProjectileType load) {
        super(model, type, pos, velocity);
        this.load = load;
    }

    @Override
    public void remote() {
        for(int angle=0;angle<360;angle+=30) {
            DoubleVec vec = DoubleVec.polar(1, angle);
            load.makeProjectile(model, getPos(),vec);
        }
        destroy();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
