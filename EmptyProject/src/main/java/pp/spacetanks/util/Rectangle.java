package pp.spacetanks.util;

import java.util.ArrayList;

public class Rectangle extends BoundingShape {
    /**
     *
     * @param position position of the rectangle
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param rotation rotation of the rectangle
     */
    public Rectangle(DoubleVec position, double width,double height,double rotation){
        super(position);
        this.height = height;
        this.width = width;
        this.rotation = rotation;
    }

    public final double width;
    public final double height;
    private double rotation;

    @Override
    public boolean collisionWith(BoundingShape other){
       return other.invCollisionWith(this);
    }

    public boolean invCollisionWith(Rectangle other){
        ArrayList<DoubleVec> normals = getNormals();
        normals.addAll(other.getNormals());
        for(DoubleVec n:normals){
            double[] minMax1 = findMinMaxProj(n);
            double[] minMax2 = other.findMinMaxProj(n);
            if(minMax1[0]>minMax2[1]||minMax1[1]<minMax2[0]) return false;
        }
        return true;
    }

    public boolean invCollisionWith(Circle c){
        ArrayList<DoubleVec> normals = getNormals();
        normals.addAll(c.getNormals(getPosition()));
        for(DoubleVec n:normals){
            double[] minMax1 = findMinMaxProj(n);
            double[] minMax2 = c.findMinMaxProj(n);
            if(minMax1[0]>minMax2[1]||minMax1[1]<minMax2[0]) return false;
        }
        return true;
    }



    /**
     *
     * @return returns the two relevant normal vectors of the rectangle sides
     */
    @Override
    protected ArrayList<DoubleVec> getNormals(){
        ArrayList<DoubleVec> normals = new ArrayList<>(4);
        normals.add(DoubleVec.polar(1,rotation));
        normals.add(DoubleVec.polar(1,rotation+90));
        return normals;
    }

    /** calculates the min and max projection length of the rectangle edges on the axis if |axis|=1
     *  axis does not need to be normalized for the collision detection to work
     * @param axis axis to project on
     * @return double[2] first min then max projection length
     */
    protected double[] findMinMaxProj(DoubleVec axis){
        DoubleVec[] mMP = findMinMaxProjPoints(axis);
        double[] minMax2 = {mMP[0].scalarProduct(axis),mMP[1].scalarProduct(axis)};
        return  minMax2;
    }

    public DoubleVec[] findMinMaxProjPoints(DoubleVec axis){
        DoubleVec[] points = getEdges();
        double[] minMax = {points[0].scalarProduct(axis),0};
        DoubleVec[] minMaxPoints={points[0],points[0]};
        minMax[1]=minMax[0];
        for(int i = 1;i<4;i++){
            double proj = points[i].scalarProduct(axis);
            if(proj<minMax[0]){
                minMax[0]=proj;
                minMaxPoints[0]=points[i];
            }
            if(proj>minMax[1]){
                minMax[1]=proj;
                minMaxPoints[1]=points[i];
            }
        }
        return minMaxPoints;
    }

    public DoubleVec[] getEdges() {
        DoubleVec down = DoubleVec.polar(height / 2, rotation + 90);
        DoubleVec right = DoubleVec.polar(width / 2, rotation);
        DoubleVec downRight = down.add(right);
        DoubleVec downLeft = down.sub(right);
        return new DoubleVec[]{
                getPosition().add(downRight),
                getPosition().add(downLeft),
                getPosition().sub(downRight),
                getPosition().sub(downLeft)};
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     *
     * @return returns the general rotation of the item with which to draw the item.
     */
    @Override
    public double getRotation(){return rotation;}
}
