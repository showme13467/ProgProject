package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.*;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.StopWatch;
import pp.spacetanks.view.Visitor;

/**
 * A Firework rocket that won't be destroyed through StationaryAsteroids and deals no damage.
 */
public class FireworkRocket extends Projectile{
    private ProjectileType type;
    public final int randomNum;
    private StopWatch timer = new StopWatch();
    public FireworkRocket(GameModel model, ProjectileType type, DoubleVec pos, DoubleVec velocity) {
        super(model, type, pos, velocity);
        this.type = type;
        this.randomNum = (int)(Math.random()*5);
        timer.start();
    }

    @Override
    public void hit(Asteroid a) {
        remote();}

    @Override
    public void hit(TankComponent a) {
        remote();}

    @Override
    public void hit(StationaryAsteroid a) {}

    @Override
    public void hit(Planet a) {
        remote();}

    @Override
    public void hit(Tank tank) {
        remote();}

    @Override
    public void remote() {
        switch ((int)(Math.random()*4)){
            case 0: model.getMap().add(new Animation(model, StringProperty.blueshotAnimation,getRotation(),type.radius , getPos().add(DoubleVec.NULL), 42, 40, 8, 1, 8));break;
            case 1: model.getMap().add(new Animation(model, StringProperty.redshotAnimation,getRotation(),type.radius , getPos().add(DoubleVec.NULL), 42, 40, 8, 1, 8));break;
            case 2: model.getMap().add(new Animation(model, StringProperty.yellowshotAnimation,getRotation(),type.radius , getPos().add(DoubleVec.NULL),  42, 40, 8, 1, 8));break;
            case 3: model.getMap().add(new Animation(model, StringProperty.violetshotAnimation,getRotation(),type.radius , getPos().add(DoubleVec.NULL),  42, 40, 8, 1, 8));break;
        }
        destroy();
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        if (timer.getTime()>=2&&!isDestroyed()){
            remote();
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
