package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Planet with gravitation
 */
public class Planet extends MapContent {
    public final int mass;
    public final int radius;
    public final int randomNum;
    private final double g = 9.807;

    /**
     * Creates a new Planet
     * @param model     the GameModel of the planet
     * @param pos       the position of the planet
     * @param mass      the mass of the planet
     * @param radius    the radius of the planet
     */
    public Planet(GameModel model, DoubleVec pos,int mass, int radius){
        super(model, pos, new Circle(pos,radius));
        this.radius = radius;
        this.mass = mass;
        this.randomNum = ThreadLocalRandom.current().nextInt(0, 9);
    }
    /**
     * Creates a new Planet
     * @param model     the GameModel of the planet
     * @param pos       the position of the planet
     * @param mass      the mass of the planet
     * @param radius    the radius of the planet
     * @param randomNum used to select the image representing the planet
     */
    public Planet(GameModel model, DoubleVec pos,int mass, int radius, int randomNum){
        super(model, pos, new Circle(pos,radius));
        this.radius = radius;
        this.mass = mass;
        this.randomNum = randomNum;
    }
    /**
     * Calculating the gravitation force on the target multiplied by the gravitation constant
     * @param target    the target to calculate the gravitation force for
     * @return          gravitation force on the target multiplied by the gravitation constant
     */
    public DoubleVec getGravityForceMultByGravConst(MapContent target){
       DoubleVec tmp =  getPos().sub(target.getPos());
       return tmp.div(Math.pow(tmp.length(),3)).mult(mass*g);
    }

    @Override
    public void accept(Visitor v) {
       v.visit(this);
    }
}
