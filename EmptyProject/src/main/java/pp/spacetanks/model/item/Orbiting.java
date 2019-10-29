package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.BoundingShape;
import pp.spacetanks.util.Calculations;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * Abstract base class for all items with circular motion around a planet
 */
public class Orbiting extends MapContent {
    private double angle;
    protected Planet planet;
    public final double distance; // distance to surface

    /**
     * Creates a new orbiting item for the specified game model.
     * @param model           the game model whose game map will contain this item.
     * @param shape           the bounding shape of this item.
     * @param distance        the distance to the planet surface.
     * @param angle           the current position in degree.
     */
    public Orbiting(GameModel model, BoundingShape shape, double distance, double angle){
        super(model,DoubleVec.NULL,shape);
        this.angle = angle;
        this.distance = distance;
    }
    @Override
    public void update(double delta){
        setPos(planet.getPos().add(DoubleVec.polar(distance+planet.radius,angle)));
        super.update(delta);
    }

    /**
     *  incrases the angle by the passed value
     * @param angle  value to increase by
     */
    public void increaseRotationOrbiting(double angle) {
        this.angle = Calculations.normalizeAngle(this.angle+angle);
    }
    public void setRotationOrbiting(double angle){this.angle = angle;}
    /**
     *
     * @return returns the general rotation of the item with which to draw the item.
     */
    @Override
    public double getRotation(){return angle;}

    /**
     * Sets the planet to be orbited
     * @param planet    the planet
     */
    public void setPlanet(Planet planet) {
        this.planet = planet;
        setPos(DoubleVec.polar(distance+planet.radius/2,angle).add(planet.getPos()));
    }

    /**
     * @return  the orbited planet
     */
    public Planet getPlanet() {
        return planet;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
