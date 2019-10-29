package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.BoundingShape;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * Asteroids orbiting a planet
 */
public class StationaryAsteroid extends Orbiting {
    private DoubleVec oldPos;
    public final double speed;

    /**
     *
     * Creates a new stationaryAsteroid item for the specified game model.
     * @param model           the game model whose game map will contain this item.
     * @param planet          the planet to be orbited.
     * @param shape           the bounding shape of this item.
     * @param distance        the distance to the planet surface.
     * @param angle           the current position in degree.
     * @param speed           the radius in degree moved every second.
     */
    public StationaryAsteroid(GameModel model, BoundingShape shape, Planet planet, double distance, double angle, double speed){
        super(model,shape,distance,angle);
        this.speed = speed;
        this.oldPos = getPos();
        setPlanet(planet);
    }

    @Override
    public void update(double delta){
        increaseRotationOrbiting(speed*delta);
        super.update(delta);
    }

    /**
     * @return the last position of the StationaryAsteroid
     */
    public DoubleVec getOldPos() {
        return oldPos;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
