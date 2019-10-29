package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.BoundingShape;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 *  Base class of most items in a {@linkplain pp.spacetanks.model.item}
 */
public class MapContent {

        public final GameModel model;
        public final BoundingShape shape;
        private DoubleVec pos;
        private boolean destroyed = false;

        /**
         * Creates a new item for the specified game model.
         *
         * @param model           the game model whose game map will contain this item.
         * @param pos             the position of the item.
         * @param shape           the bounding shape of this item.
         *                        It is used to compute collisions between two items with polygonal or circular bounds.
         */
        protected MapContent(GameModel model,DoubleVec pos, BoundingShape shape) {
            this.model = model;
            this.shape = shape;
            this.pos = pos;
        }

        /**
         * Returns the position of the item
         */
        public DoubleVec getPos(){ return pos;}

        /**
         * Sets the new position to the item and its BoundingShape.
         */
        public void setPos(DoubleVec  pos){
            this.pos = pos;
            if(shape!=null)shape.setPosition(pos);
        }

        /**
         * Accept method of the visitor pattern.
         */
        public void accept(Visitor v){ v.visit(this);}

        /**
         * Called once per frame. Used for updating this item's position etc.
         *
         * @param delta time in seconds since the last update call
         */
        public void update(double delta){shape.setPosition(pos);}

        /**
         * Checks whether there is a collision with another item
         *
         * @param other the item which is checked for a collision
         */
        public boolean collisionWith(MapContent other) {
            return !other.isDestroyed()&& shape.collisionWith(other.shape);
        }

        /**
         * Checks, whether this item has been destroyed
         *
         * @return true or false
         */
        public boolean isDestroyed() {
            return destroyed;
        }

        /**
         * Indicates that this item has been destroyed.
         */
        public void destroy() {
            destroyed = true;
        }

    /**
     *
     * @return returns the general rotation of the item with which to draw the item.
     */
    public double getRotation(){return 0;}
    }


