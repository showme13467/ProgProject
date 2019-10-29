package pp.spacetanks.view;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import pp.spacetanks.model.Map;
import pp.spacetanks.model.item.*;
import pp.spacetanks.model.item.Projectiles.*;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;

/**
 * visitor for debugging
 */
public class DebugVisitor  implements Visitor{

    private MapView view;
    double zoomFactor;

    public DebugVisitor(MapView view, double zoomFactor){
        this.view = view;
        this.zoomFactor = zoomFactor;
    }

    /**
     * draws the animation bounding shape in debug mode
     * @param a Animation object
     */
    @Override
    public void visit(Animation a){
        final Circle shape = (Circle) a.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageCircle((Circle)a.shape,Color.ROSYBROWN);
        context.setTransform(ori);
    }

    /**
     * draws the tank bounding shape in debug mode
     * @param tank
     */
    @Override
    public void visit(Tank tank) {
        final Rectangle shape = (Rectangle) tank.getChassis().shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, tank.getChassis().isDestroyed()?Color.RED:Color.GREEN);
        context.setTransform(ori);

        drawAntenna(tank);
        drawChain(tank);
        visit(tank.getCannon());
    }

    /**
     * draws the heatseeker bounding shape in debug mode
     * @param projectile
     */
    @Override
    public void visit(HeatSeeker projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        if (projectile.hasVision())drawImageRectangle(shape, Color.GREEN);
        else drawImageRectangle(shape, Color.RED);
        context.setTransform(ori);

        final Rectangle shape2 =  projectile.getLineOfSight();
        final Affine ori2 = context.getTransform();
        final DoubleVec pos2 = view.modelToView(shape2.getPosition());
        context.translate(pos2.x, pos2.y);
        context.rotate(shape2.getRotation());
        drawImageRectangle(shape2, Color.YELLOW);
        context.setTransform(ori2);
    }

    /**
     * draws the remoteable rocket  bounding shape in debug mode
     * @param projectile
     */
    @Override
    public void visit(RemotableRocket projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(PremiumAsteroidKiller projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(AsteroidKiller projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(DropExplosiveProjectile projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(ExplosiveProjectile projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(DropRocket projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(PathFindingCluster projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(ClusterRocket projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(ClusterBomb projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(AntiAsteroidCluster projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    /**
     * draws the artillery projectile bounding shape in debug mode
     * @param projectile
     */
    @Override
    public void visit(ArtilleryProjectile projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.PURPLE);
        context.setTransform(ori);
    }

    @Override
    public void visit(FireworkRocket projectile) {
        final Rectangle shape = (Rectangle) projectile.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageRectangle(shape, Color.WHITE);
        context.setTransform(ori);
    }
    /**
     * draws the asteroid bounding shape in debug mode
     * @param asteroid
     */
    @Override
    public void visit(Asteroid asteroid) {
        final Circle shape = (Circle) asteroid.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageCircle(shape, Color.BLUE);
        context.setTransform(ori);
    }

    /**
     * draws the stationary asteroid bounding shape in debug mode
     * @param stationary
     */
    @Override
    public void visit(StationaryAsteroid stationary) {
        final Circle shape = (Circle) stationary.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageCircle(shape, Color.BLUE);
        context.setTransform(ori);
    }

    /**
     * draws the planet bounding shape in debug mode
     * @param planet
     */
    @Override
    public void visit(Planet planet) {
        final Circle shape = (Circle) planet.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        drawImageCircle(shape, Color.BLUE);
        context.setTransform(ori);
    }

    /**
     * draws the cannon bounding shape in debug mode
     * @param cannon
     */
    @Override
    public void visit(Cannon cannon) {
        final Rectangle shape = (Rectangle) cannon.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        if(cannon.isDestroyed())drawImageRectangle(shape, Color.RED);
        else drawImageRectangle(shape, Color.ORANGE);
        context.setTransform(ori);
    }

    @Override
    public void visit(MapContent content) {
        //do nothing
    }

    @Override
    public void visit(HelplineElement line) {
        //
    }

    /**
     * Change zoomFactor for drawing content.
     * @param zoomFactor new factor
     */
    @Override
    public void setZoom(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     * Draw Rectangle to approximate MapContent.
     * @param shape the content to be drawn
     * @param color color of the rectangle
     */
    public void drawImageRectangle(Rectangle shape, Color color){
        final GraphicsContext context = view.getGraphicsContext2D();
        context.setStroke(color);
        context.strokeRect(-shape.width/2*zoomFactor,
                -shape.height/2*zoomFactor,
                shape.width*zoomFactor,
                shape.height*zoomFactor);
    }

    /**
     * Draw Rectangle to approximate MapContent.
     * @param shape the content to be drawn
     * @param color color of the rectangle
     */
    private void drawImageCircle(Circle shape, Color color){
        final GraphicsContext context = view.getGraphicsContext2D();
        context.setStroke(color);
        context.strokeOval(-shape.radius*zoomFactor,
                -shape.radius*zoomFactor,
                shape.radius*2*zoomFactor,
                shape.radius*2*zoomFactor);
    }

    /**
     * Method to draw the Antenna.
     * @param tank
     */
    private void drawAntenna(Tank tank){
        TankComponent antenna = tank.getAntenna();

        final Rectangle shape = (Rectangle) antenna.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        if(antenna.isDestroyed())drawImageRectangle(shape, Color.RED);
        else drawImageRectangle(shape, Color.BLACK);
        context.setTransform(ori);
    }

    /**
     * Method to draw the Chain.
     * @param tank
     */
    private void drawChain(Tank tank){
        TankComponent chain = tank.getChain();

        final Rectangle shape = (Rectangle) chain.shape;
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(shape.getPosition());
        context.translate(pos.x, pos.y);
        context.rotate(shape.getRotation());
        if(chain.isDestroyed())drawImageRectangle(shape, Color.RED);
        else drawImageRectangle(shape, Color.YELLOWGREEN);
        context.setTransform(ori);
    }

    /**
     * draw map boarder lines
     * @param map
     */
    public void visit(Map map){
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        DoubleVec pos = view.modelToView(new DoubleVec(map.getWidth()/2, map.getHeight()/2));
        context.translate(pos.x,pos.y);
        drawImageRectangle(map.getBorder(), Color.RED);
        context.setTransform(ori);
    }

    public void visit(Rectangle r){
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        DoubleVec pos = view.modelToView(r.getPosition());
        context.translate(pos.x,pos.y);
        drawImageRectangle(r, Color.YELLOW);
        context.setTransform(ori);
    }


}
