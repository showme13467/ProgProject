package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.BoundingShape;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;

/**
 * A base class for moving MapContents.
 */
public class Moving extends MapContent {
    private DoubleVec vel;
    private DoubleVec oldPos;

    /**
     * Creates a new Moving
     * @param model     the GameModel
     * @param pos       the position of the moving MapContent
     * @param shape     the BoundingShape of the moving MapContent
     * @param velocity  the velocity of the moving MapContent
     */
    public Moving(GameModel model, DoubleVec pos, BoundingShape shape,DoubleVec velocity){
        super(model, pos, shape);
        vel = velocity;
        oldPos = pos;
    }

    /**
     * Sets a new velocity
     * @param vel   the velocity to be set
     */
    public void setVel(DoubleVec vel) {
        this.vel = vel;
    }

    /**
     * @return the velocity
     */
    public DoubleVec getVel() {
        return vel;
    }

    /**
     * @return  the last position of the moving MapContent
     */
    public DoubleVec getOldPos() {
        return oldPos;
    }

    /**
     * moves the item related to the gravity force applied to it and its velocity
     * @param t  the time in ns in which the item moved
     */
    protected void move(double t){
        DoubleVec sum = DoubleVec.NULL;
        for(Planet p:model.getMap().getPlanets())
            sum = sum.add(p.getGravityForceMultByGravConst(this));
        setVel(vel.add(sum.mult(t*t)));
        setPos(getPos().add(vel));
    }

    @Override
    public double getRotation(){return vel.angle();}

    @Override
    public void update(double delta){
        oldPos = getPos();
        move(delta);
        super.update(delta);
    }
}
