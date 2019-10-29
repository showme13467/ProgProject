package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * Base class for remotable rockets.
 */
public class RemotableRocket extends ExplosiveProjectile{
    public RemotableRocket(GameModel model,ProjectileType type, DoubleVec pos,DoubleVec velocity){
        super(model,type, pos,velocity);
    }
    @Override
    public void remote(){
        dealRadiusDamage();
        destroy();
    }
    @Override
    protected void move(double t){
        setVel(getVel().add(DoubleVec.polar(power,getVel().angle())));
        super.move(t);
    }
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
