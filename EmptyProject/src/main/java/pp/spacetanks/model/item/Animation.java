package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * represents an animation used for explosions
 * generally for the visualisation of animations out of one image
 */
public class Animation extends MapContent {
    private final DoubleVec pos;
    public final StringProperty prop;
    private final int maxX;
    private final int maxY;
    public final int width;
    public final int height;
    private final int count;
    private double rotation;
    private int x=0;
    private int y=0;
    private int frames = 0;
    private int fpa=2;

    /**
     * creates an animation
     * @param model the game model that has this animation.
     * @param pos position of the animation
     * @param width of one frame
     * @param height of one frame
     * @param maxX number of frames in one row
     * @param maxY number of frames in one column
     * @param count total number of frames
     */
    public Animation(GameModel model, StringProperty prop,double rotation, double effectiveRadius, DoubleVec pos, int width, int height, int maxX, int maxY, int count){
        super(model,pos,new Circle(pos,effectiveRadius));
        this.prop = prop;
        this.rotation = rotation;
        this.maxX = maxX;
        this.maxY = maxY;
        this.count = count;
        this.pos = pos;
        this.height = height;
        this.width = width;
    }

    /**
     *
     * @return returns the position of the animation.
     */
    public DoubleVec getPos(){return pos;}

    /**
     * Accept method of the visitor pattern.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    /**
     * Called once per frame. Used to update the position of the current frame in the image .
     *
     * @param delta time in seconds since the last update call
     */
    public void update(double delta) {
        frames += 1;
        if (frames == fpa) {
            frames = 0;
            if (x +1 > maxX) {
                x = 0;
                if (y + 1 > maxY) {
                    destroy();
                } else y += 1;
            } else if (x + 1 + y * (maxX+1)> count) destroy();
            else x += 1;
        }
    }

    /**
     *
     * @return returns the position of the current frame in the row of the image
     */
    public int getX(){return x;}
    /**
     *
     * @return returns the position of the current frame in the column of the image
     */
    public int getY(){return y;}

    @Override
    public double getRotation() {
        return rotation;
    }
}
