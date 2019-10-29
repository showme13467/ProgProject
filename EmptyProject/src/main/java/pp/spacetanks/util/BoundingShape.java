package pp.spacetanks.util;

import java.util.ArrayList;

/**
 * represents a bounding shape for collision detection
 */
public abstract class BoundingShape {
    public BoundingShape(DoubleVec position){
        pos = position;
    }

    private DoubleVec pos;

    /**
     *
     * @return returns the center position of the shape
     */
    public DoubleVec getPosition() {return pos;}

    public void setPosition(DoubleVec position) {pos = position;}

    /**checks if this shape is colliding with the other shape
     *
     * @param other
     * @return
     */
    public abstract boolean collisionWith(BoundingShape other);
    public abstract boolean invCollisionWith(Rectangle r);
    public abstract boolean invCollisionWith(Circle c);

    /**calculates the min and max projection length of the shape on the axis if |axis|=1
     *  axis does not need to be normalized for the collision detection to work
     * @param axis axis to project on
     * @return double[2] first min then max projection length
     **/

    /**
     *
     * @return returns the normals of each of the shapes sides
     */
    protected abstract ArrayList<DoubleVec> getNormals();

    /**
     *
     * @return returns the general rotation of the item with which to draw the item.
     */
    public double getRotation(){return 0;}
}
