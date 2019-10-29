package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.Destroyer;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;
import pp.spacetanks.view.Visitor;

/**
 * Base class for all Projectiles.
 */
public class Projectile extends Destroyer {
    public final ProjectileType type;

    /**
     * Creates a new Projectile
     * @param model     the GameModel
     * @param type      the ProjectileType
     * @param pos       the position of the Projectile
     * @param velocity  the velocity of the Projectile
     */
    public Projectile(GameModel model,ProjectileType type, DoubleVec pos, DoubleVec velocity){
        super(model, pos, new Rectangle(pos,type.width,type.height,velocity.angle()), velocity, type.damage);
        this.type = type;
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        ((Rectangle)shape).setRotation(getRotation());
    }

    /**
     * The action on remoting the Projectile.
     */
    public void remote(){}

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
