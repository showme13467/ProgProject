package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.BoundingShape;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * Represents an Asteroid.
 */
public class Asteroid extends Destroyer {
    /**
     * Creates a new Asteroid
     * @param model     the GameModel
     * @param pos       the position
     * @param velocity  the velocity
     * @param damage    the damage inflicted to tank
     */
    public Asteroid(GameModel model, DoubleVec pos, DoubleVec velocity, int damage){
        super(model, pos, new Circle(pos,3), velocity, damage);
    }

    @Override
    public void hit(TankComponent cmp){
        super.hit(cmp);
        model.getMap().add(new Animation(model, StringProperty.metalHitAnimation,getRotation()+180,25,getPos(),1024,1024,4,4,16));
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void hit(Asteroid a) {
        super.hit(a);
        model.getMap().add(new Animation(model, StringProperty.asteroidDustAnimation,a.getRotation(),10,a.getPos(),128,128,8,8,17));

    }

    @Override
    public void hit(StationaryAsteroid a) {
        super.hit(a);
        model.getMap().add(new Animation(model, StringProperty.asteroidDustAnimation,a.getRotation(),10,a.getPos(),128,128,8,8,17));

    }

    @Override
    public void hit(Planet p) {
        super.hit(p);
        model.getMap().add(new Animation(model, StringProperty.planetHitAnimation,getRotation(),25,getPos(),128,128,8,8,64));
    }

    @Override
    public void hit(Tank tank) {
        super.hit(tank);
        model.getMap().add(new Animation(model, StringProperty.metalHitAnimation,getRotation()+180,25,getPos(),1024,1024,4,4,16));

    }
}
