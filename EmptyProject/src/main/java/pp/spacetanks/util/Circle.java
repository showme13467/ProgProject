package pp.spacetanks.util;

import java.util.ArrayList;

public class Circle extends BoundingShape {
    /**
     *
     * @param position position of the circle
     * @param radius radius of the circle
     */
    public Circle(DoubleVec position, double radius){
        super(position);
        this.radius = radius;
    }

    public final double radius;

    public double getRadius(){return radius;}

    @Override
    public boolean collisionWith(BoundingShape other){
        return other.invCollisionWith(this);
    }


    public boolean invCollisionWith(Rectangle r){
        ArrayList<DoubleVec> normals = getNormals(r.getPosition());
        normals.addAll(r.getNormals());
        for(DoubleVec n:normals){
            double[] minMax1 = findMinMaxProj(n);
            double[] minMax2 = r.findMinMaxProj(n);
            if(minMax1[0]>minMax2[1]||minMax1[1]<minMax2[0]) return false;
        }
        return true;
    }

    public boolean invCollisionWith(Circle c){
        return getPosition().distance(c.getPosition()) < radius+c.getRadius();
    }

    /**
     *
     * @return returns the two relevant normal vectors of the rectangle sides
     */
    protected ArrayList<DoubleVec> getNormals(DoubleVec p){
        ArrayList<DoubleVec> normal = new ArrayList<DoubleVec>(1);
        normal.add(p.sub(getPosition()).normalize());
        return normal;
    }

    @Override
    protected ArrayList<DoubleVec> getNormals() {
        return new ArrayList<DoubleVec>(0);
    }

    protected double[] findMinMaxProj(DoubleVec axis){
        double proj = getPosition().scalarProduct(axis);
        double[] mMP = {proj-radius,proj+radius};
        return  mMP;
    }

}
