package pp.spacetanks.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import pp.spacetanks.model.item.*;
import pp.spacetanks.model.item.Projectiles.*;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;

/**
 * visitor for main PlayGame Window
 */
public class VisualizerVisitor implements Visitor {

    private enum Shape {RECTANGLE, CIRCLE};
    private final MapView view;

    double zoomFactor;


    public VisualizerVisitor(MapView view, double zoomFactor){
        this.view = view;
        this.zoomFactor = zoomFactor;
    }

    /**
     * draws the tank (depends on tank type)
     * @param tank
     */
    public void visit(Tank tank) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(tank.getChassis().getPos());
        context.translate(pos.x, pos.y);
        context.rotate(tank.getChassis().getRotation());

        if (tank.getType() == TankType.M55) {
            final Image img = view.getImages().getImage(StringProperty.M55Image);
            DoubleVec scale = scaleRectangle(tank.getChassis(), img);
            drawImage(tank.getChassis().isDestroyed()? StringProperty.m55DestroyedImage :StringProperty.M55Image, Shape.RECTANGLE, Color.RED, scale.x, scale.y);
        } else if (tank.getType() == TankType.MARS) {
            final Image img = view.getImages().getImage(StringProperty.marsImage);
            DoubleVec scale = scaleRectangle(tank.getChassis(), img);
            drawImage(tank.getChassis().isDestroyed()? StringProperty.marsDestroyedImage :StringProperty.marsImage, Shape.RECTANGLE, Color.RED, scale.x, scale.y);
        } else {
            final Image img = view.getImages().getImage(StringProperty.haubitzeImage);
            DoubleVec scale = scaleRectangle(tank.getChassis(), img);
            drawImage(tank.getChassis().isDestroyed()? StringProperty.haubitzeDestroyedImage :StringProperty.haubitzeImage, Shape.RECTANGLE, Color.RED, scale.x, scale.y);
        }
        context.setTransform(ori);

        drawChain(tank,tank.getChain().isDestroyed());
        drawAntenna(tank, tank.getAntenna().isDestroyed());
        drawCannon(tank, tank.getCannon().isDestroyed());

       // context.setTransform(ori);
    }

    public void visit(Cannon cannon){
        //do nothing
    }

    @Override
    public void visit(MapContent content) {
        //do nothing
    }

    @Override
    public void setZoom(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     * draws helpline
     * @param line
     */
    @Override
    public void visit(HelplineElement line) {
        final GraphicsContext context = view.getGraphicsContext2D();
        DoubleVec pos = view.modelToView(line.position);
        context.setFill(Color.RED);
        context.fillOval(pos.x, pos.y, 5, 5);
    }

    @Override
    public  void visit(Animation animation){
        drawItem(animation,animation.prop);
    }

    /**
     * draws the Item with animation
     * @param a
     * @param prop
     */
    public void drawItem(Animation a, StringProperty prop) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(a.getPos());
        final Image img = view.getImages().getImage(prop);
        final double scale = ((Circle)a.shape).radius/(a.width)*zoomFactor*3;
        context.translate(pos.x, pos.y);
        context.rotate(a.getRotation());
        if (img != null) {
            context.drawImage(img, a.getX()*a.width,a.getY()*a.width,a.width,a.height,-a.width*0.5*scale,-a.height*0.5*scale,a.width*scale,a.height*scale);
        }
        context.setTransform(ori);
    }

    /**
     * draws the heatseeker projectile and calculate size and position
     * @param projectile
     */
    public void visit(HeatSeeker projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.heatSeekerImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.heatSeekerImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }

    /**
     * draws the remoteable rocket and calculate size and position
     * @param projectile
     */
    public void visit(RemotableRocket projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.remotableRocketImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.remotableRocketImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }

    /**
     * draws the artillery projectile and calculate size and position
     * @param projectile
     */
    @Override
    public void visit(ArtilleryProjectile projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.artilleryProjectileImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.artilleryProjectileImage, Shape.RECTANGLE, Color.RED, scale.x, scale.y);
        context.setTransform(ori);
    }
    @Override
    public void visit(DropExplosiveProjectile projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.remotableRocketImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.remotableRocketImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }
    @Override
    public void visit(ExplosiveProjectile projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.remotableRocketImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.artilleryProjectileImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }

    @Override
    public void visit(PremiumAsteroidKiller projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.swarmImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.swarmImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }

    @Override
    public void visit(FireworkRocket projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        StringProperty prop = StringProperty.fireworkRocket1Image;
        switch (projectile.randomNum){
            case 1 : prop = StringProperty.fireworkRocket2Image;break;
            case 2 : prop = StringProperty.fireworkRocket3Image;break;
            case 3 : prop = StringProperty.fireworkRocket4Image;break;
            case 4 : prop = StringProperty.fireworkRocket5Image;break;
        }
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        Image img= view.getImages().getImage(prop);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(prop, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }

    @Override
    public void visit(AsteroidKiller projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.swarmImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.swarmImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }

    public void visit(DropRocket projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.remotableRocketImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.remotableRocketImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }
    @Override
    public void visit(PathFindingCluster projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.missileImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.missileImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }
    @Override
    public void visit(AntiAsteroidCluster projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.smallCarryImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.smallCarryImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }
    @Override
    public void visit(ClusterRocket projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.bigCarryImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.bigCarryImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }
    @Override
    public void visit(ClusterBomb projectile) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(projectile.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(projectile.getRotation());
        final Image img = view.getImages().getImage(StringProperty.bigCarryImage);
        DoubleVec scale = scaleRectangle(projectile, img);
        drawImage(StringProperty.bigCarryImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        context.setTransform(ori);
    }
    /**
     * draws the asteroid and calculate size and position
     * @param asteroid
     */
    public void visit(Asteroid asteroid) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(asteroid.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(asteroid.getRotation());
        final Image img = view.getImages().getImage(StringProperty.stationaryAsteroidImage);
        double scale = scaleCircle(asteroid, img);
        drawImage(StringProperty.stationaryAsteroidImage, Shape.CIRCLE, Color.RED, scale, scale);
        context.setTransform(ori);
    }

    /**
     * draws the stationary asteroids and calculate size and position
     * @param stationary
     */
    public void visit(StationaryAsteroid stationary) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(stationary.getPos());
        context.translate(pos.x, pos.y);
        context.rotate(stationary.getRotation());
        final Image img = view.getImages().getImage(StringProperty.stationaryAsteroidImage);
        double scale = scaleCircle(stationary, img);
        drawImage(StringProperty.stationaryAsteroidImage, Shape.CIRCLE, Color.RED, scale, scale);
        context.setTransform(ori);
    }

    /**
     * draws the planet and calculate size and position
     * @param planet
     */
    public void visit(Planet planet) {
        int randomNum = planet.randomNum;
        Circle shape = (Circle) planet.shape;
        double scale;
        Image img;
        switch (randomNum) {
            case 0: img = view.getImages().getImage(StringProperty.planetImage1);
                    scale = scaleCircle(planet, img);
                    drawItem(planet, StringProperty.planetImage1, Shape.CIRCLE, Color.RED, scale);
                    break;
            case 1: img = view.getImages().getImage(StringProperty.planetImage2);
                    scale = scaleCircle(planet, img);
                    drawItem(planet, StringProperty.planetImage2, Shape.CIRCLE, Color.RED, scale);
                    break;
            case 2: img = view.getImages().getImage(StringProperty.planetImage3);
                    scale = scaleCircle(planet, img);
                    drawItem(planet, StringProperty.planetImage3, Shape.CIRCLE, Color.RED, scale);
                    break;
            case 3: img = view.getImages().getImage(StringProperty.planetImage4);
                     scale = scaleCircle(planet, img);
                     drawItem(planet, StringProperty.planetImage4, Shape.CIRCLE, Color.RED, scale);
                     break;
            case 4: img = view.getImages().getImage(StringProperty.planetImage5);
                    scale = scaleCircle(planet, img);
                    drawItem(planet, StringProperty.planetImage5, Shape.CIRCLE, Color.RED, scale);
                     break;
            case 5: img = view.getImages().getImage(StringProperty.planetImage6);
                     scale = scaleCircle(planet, img);
                     drawItem(planet, StringProperty.planetImage6, Shape.CIRCLE, Color.RED, scale);
                     break;
            case 6: img = view.getImages().getImage(StringProperty.planetImage7);
                    scale = scaleCircle(planet, img);
                    drawItem(planet, StringProperty.planetImage7, Shape.CIRCLE, Color.RED, scale);
                    break;
            case 7: img = view.getImages().getImage(StringProperty.planetImage8);
                    scale = scaleCircle(planet, img);
                    drawItem(planet, StringProperty.planetImage8, Shape.CIRCLE, Color.RED, scale);
                    break;
            case 8: img = view.getImages().getImage(StringProperty.planetImage9);
                    scale = scaleCircle(planet, img);
                    drawItem(planet, StringProperty.planetImage9, Shape.CIRCLE, Color.RED, scale);
                    break;
        }
    }

    /**
     * Draws an item as an image or, if the image is missing, as the specified shape.
     *
     * @param content  the item to be drawn
     * @param prop  the property specifying the image
     * @param shape the shape, which is drawn if the image is missing
     * @param color the color, which is used if the image is missing
     * @param scale the factor by which to scale the image
     */
    private void drawItem(MapContent content, StringProperty prop, Shape shape, Color color, double scale) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(content.getPos());
        context.translate(pos.x, pos.y);
        drawImage(prop, shape, color, scale, scale);
        context.setTransform(ori);
    }


    /**
     * Draws an image if such an image has been configured.
     * Otherwise, a specified shape of the specified color is drawn
     *
     * @param prop  the string property indicating an image
     * @param shape the shape to be used when the image is missing
     * @param color the color that is used as a circle color if the image is missing
     * @param scaleX the factor by which to scale the image
     */
    private void drawImage(StringProperty prop, Shape shape, Color color,double scaleX, double scaleY) {
        final GraphicsContext context = view.getGraphicsContext2D();
        final Image img = view.getImages().getImage(prop);
        if (img != null)
            context.drawImage(img, -img.getWidth() * scaleX*zoomFactor/2,
                    -img.getHeight() * scaleY*zoomFactor/2,
                    img.getWidth()*scaleX*zoomFactor,
                    img.getHeight()*scaleY*zoomFactor);
        else
            switch (shape) {
                case RECTANGLE:
                    context.setFill(color);
                    context.fillRect(-50,
                            -50,
                            100,
                            100);
                    break;
                case CIRCLE:
                    context.setFill(color);
                    break;

            }
    }


    /**
     * draws the chain of a tank
     * @param tank the tank where the chain is from
     * @param destroyed is the chain destroyed?
     */
    private void drawChain (Tank tank, boolean destroyed){
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(tank.getChain().getPos());
        context.translate(pos.x, pos.y);
        context.rotate(tank.getChain().getRotation());
        TankType tankType = tank.getType();
        if (tankType == TankType.M55 && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.M55ChainImage);
            DoubleVec scale = scaleRectangle(tank.getChain(), img);
            drawImage(StringProperty.M55ChainImage, Shape.RECTANGLE, Color.RED, scale.x, scale.y);
        }
        else if(tankType == TankType.M55 && destroyed){
            final Image img = view.getImages().getImage(StringProperty.M55ChainDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getChain(), img);
            drawImage(StringProperty.M55ChainDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        if (tankType == TankType.MARS && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.MARSChainImage);
            DoubleVec scale = scaleRectangle(tank.getChain(), img);
            drawImage(StringProperty.MARSChainImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        else if (tankType == TankType.MARS && destroyed){
            final Image img = view.getImages().getImage(StringProperty.MARSChainDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getChain(), img);
            drawImage(StringProperty.MARSChainDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        if(tankType == TankType.PANZERHAUBITZE2000 && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.PANZERHAUBITZE2000ChainImage);
            DoubleVec scale = scaleRectangle(tank.getChain(), img);
            drawImage(StringProperty.PANZERHAUBITZE2000ChainImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        else if (tankType == TankType.PANZERHAUBITZE2000 && destroyed){
            final Image img = view.getImages().getImage(StringProperty.PANZERHAUBITZE2000ChainDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getChain(), img);
            drawImage(StringProperty.PANZERHAUBITZE2000ChainDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        context.setTransform(ori);
    }

    /**
     * draws the antenna of a tank
     * @param tank the tank where the antenna is from
     * @param destroyed is the antenna destroyed?
     */
    private void drawAntenna (Tank tank, boolean destroyed){
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(tank.getAntenna().getPos());
        context.translate(pos.x, pos.y);
        context.rotate(tank.getAntenna().getRotation());
        TankType tankType = tank.getType();
        if (tankType == TankType.M55 && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.M55AntennaImage);
            DoubleVec scale = scaleRectangle(tank.getAntenna(), img);
            drawImage(StringProperty.M55AntennaImage, Shape.RECTANGLE, Color.RED, scale.x, scale.y);
        }
        else if(tankType == TankType.M55 && destroyed){
            final Image img = view.getImages().getImage(StringProperty.M55AntennaDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getAntenna(), img);
            drawImage(StringProperty.M55AntennaDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        if (tankType == TankType.MARS && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.MARSAntennaImage);
            DoubleVec scale = scaleRectangle(tank.getAntenna(), img);
            drawImage(StringProperty.MARSAntennaImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        else if (tankType == TankType.MARS && destroyed){
            final Image img = view.getImages().getImage(StringProperty.MARSAntennaDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getAntenna(), img);
            drawImage(StringProperty.MARSAntennaDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        if(tankType == TankType.PANZERHAUBITZE2000 && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.PANZERHAUBITZE2000AntennaImage);
            DoubleVec scale = scaleRectangle(tank.getAntenna(), img);
            drawImage(StringProperty.PANZERHAUBITZE2000AntennaImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        else if (tankType == TankType.PANZERHAUBITZE2000 && destroyed){
            final Image img = view.getImages().getImage(StringProperty.PANZERHAUBITZE2000AntennaDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getAntenna(), img);
            drawImage(StringProperty.PANZERHAUBITZE2000AntennaDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        context.setTransform(ori);
    }

    /**
     * draws the cannon of a tank
     * @param tank the tank where the cannon is from
     * @param destroyed is the cannon destroyed?
     */
    private void drawCannon (Tank tank, boolean destroyed){
        final GraphicsContext context = view.getGraphicsContext2D();
        final Affine ori = context.getTransform();
        final DoubleVec pos = view.modelToView(tank.getCannon().getPos());
        context.translate(pos.x, pos.y);
        context.rotate(tank.getCannon().getRotation());
        TankType tankType = tank.getType();
        if (tankType == TankType.M55 && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.M55CannonImage);
            DoubleVec scale = scaleRectangle(tank.getCannon(), img);

           /* double imgHeight = img.getHeight();
            double imgWidth = img.getWidth();
            Rectangle shape = (Rectangle) tank.getCannon().shape;
            double height = shape.height;
            double width = shape.width;
            double scaleY = height/imgHeight;
            double scaleX = width/imgWidth;*/

            drawImage(StringProperty.M55CannonImage, Shape.RECTANGLE, Color.RED, scale.x, scale.y);
        }
        else if(tankType == TankType.M55 && destroyed){
            final Image img = view.getImages().getImage(StringProperty.M55CannonDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getCannon(), img);
            drawImage(StringProperty.M55CannonDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        if (tankType == TankType.MARS && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.MARSCannonImage);
            DoubleVec scale = scaleRectangle(tank.getCannon(), img);
            drawImage(StringProperty.MARSCannonImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        else if (tankType == TankType.MARS && destroyed){
            final Image img = view.getImages().getImage(StringProperty.MARSCannonDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getCannon(), img);
            drawImage(StringProperty.MARSCannonDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        if(tankType == TankType.PANZERHAUBITZE2000 && !destroyed){
            final Image img = view.getImages().getImage(StringProperty.PANZERHAUBITZE2000CannonImage);
            DoubleVec scale = scaleRectangle(tank.getCannon(), img);
            drawImage(StringProperty.PANZERHAUBITZE2000CannonImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        else if (tankType == TankType.PANZERHAUBITZE2000 && destroyed){
            final Image img = view.getImages().getImage(StringProperty.PANZERHAUBITZE2000CannonDestroyedImage);
            DoubleVec scale = scaleRectangle(tank.getCannon(), img);
            drawImage(StringProperty.PANZERHAUBITZE2000CannonDestroyedImage, Shape.RECTANGLE, Color.BLUE, scale.x, scale.y);
        }
        context.setTransform(ori);
    }

    /**
     * calculate the scale of the rectangle
     * @param content
     * @param img
     * @return position for the rectangle
     */
    private DoubleVec scaleRectangle(MapContent content, Image img){
        Rectangle shape = (Rectangle) content.shape;
        if(shape != null && img != null) {
            double height = shape.height;
            double width = shape.width;
            return new DoubleVec(width/img.getWidth(), height/img.getHeight());
        }
        return new DoubleVec(0,0);
    }

    /**
     * calculate the scale of the circle
     * @param content
     * @param img
     * @return position of the circle
     */
    private double scaleCircle(MapContent content, Image img){
        Circle shape = (Circle) content.shape;
        if(content != null && img != null) {
            double radius = shape.getRadius();
            return 2*radius / img.getWidth();
        }
        return 0;
    }


}
