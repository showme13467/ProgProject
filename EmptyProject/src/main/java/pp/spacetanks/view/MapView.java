package pp.spacetanks.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.Map;
import pp.spacetanks.model.item.MapContent;
import pp.spacetanks.model.item.Projectiles.Projectile;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;

import java.util.logging.Logger;

/**
 * class for managing the view
 */
public class MapView {
    private final GameModel model;
    private final Images images;
    private final VisualizerVisitor visualizer;
    private final DebugVisitor debug;
    private final SmallVisitor smallVisualizer;

    private DoubleVec refPoint;
    private DoubleVec smallRefPoint;
    private DoubleVec modelMid;
    private DoubleVec delta; //for small Canvas
    private DoubleVec smallMiddle;

    private double zoomFactor = 1;
    private int zoomCounter = 0;
    private double smallZoomFactor = 2;
    private boolean zoomedOutOfBounds = false;
    private DoubleVec refPointstep=DoubleVec.NULL;
    private int refPointCounter=0;

    private final double fo=0.9;
    private final double fi=0.8;
    private Rectangle inner;
    private Rectangle outer;

    private Canvas canvas;
    private Canvas smallCanvas;

    /**
     * Creates a view for the specified game model.
     * @param model game model
     */
    public MapView(GameModel model){
        this.model = model;
        this.images = new Images(model);
        this.visualizer = new VisualizerVisitor(this, zoomFactor);
        this.debug = new DebugVisitor(this, zoomFactor);
        this.modelMid = new DoubleVec(model.getFieldSizeX()*0.5,model.getFieldSizeY()*0.5);
        this.refPoint = modelMid;
        this.smallVisualizer = new SmallVisitor(this, 3);
        updateBoxes();
    }

    /**
     * updates boxes for outOfBounds
     */
    private void updateBoxes(){
        if(model.getMap() != null) {
            DoubleVec position = invModelToView(new DoubleVec(canvas.getWidth()*0.5,canvas.getHeight()*0.5));
            inner = new Rectangle(position, 1.0/zoomFactor * fi * canvas.getWidth(), 1.0/zoomFactor * fi * canvas.getHeight(), 0);
            outer = new Rectangle(position, 1.0/zoomFactor * fo * canvas.getWidth(), 1.0/zoomFactor * fo * canvas.getHeight(), 0);
        }
    }

    /**
     * Return the manager object for all images.
     * @return
     */
    Images getImages(){
        return images;
    }

    private boolean crossedBorder(){
        updateBoxes();
        DoubleVec[] edges = model.getMap().getBorder().getEdges();
        for (DoubleVec edge:edges){
            DoubleVec tmp = modelToView(edge);
            if(tmp.x > 0 && tmp.x<canvas.getWidth()||tmp.y > 0 && tmp.y<canvas.getHeight()){
                return true;
            }
        }
        return false;
    }

    /**
     * Renders the view
     */
    public void update(){
        updateBoxes();

        final GraphicsContext context = canvas.getGraphicsContext2D();
        final GraphicsContext smallContext = smallCanvas.getGraphicsContext2D();

        //autoZoom
        Map map = model.getMap();

        if(outOfBounds()){ //projectile is out of bounds
            zoomCounter = -40;
            zoomedOutOfBounds = true;
        }
        if(backIn() && zoomedOutOfBounds){ //projectile is back in bounds
            double backup = zoomFactor;
            zoomFactor = 1;
            updateBoxes();
            if(!outOfBounds()) {
                zoomFactor=backup;
                int count=0;
                for(int i=0;zoomFactor<1;i++){
                    zoomFactor += zoomFactor*.01;
                    count = i;
                }
                zoomCounter = count;
                zoomedOutOfBounds = false;
            }
            zoomFactor=backup;
            updateBoxes();
        }
        //actual zooming
        if(zoomCounter > 0){
                zoomFactor += zoomFactor*.01;
                zoomCounter -= 1;
                updateBoxes();
        }
        else if(zoomCounter < 0){
                double tmp = zoomFactor;
                zoomFactor -= zoomFactor*.01;
                zoomCounter += 1;
                if(crossedBorder()){
                    zoomFactor=tmp;
                    zoomCounter-=1;
                    DoubleVec tmpD = refPoint(modelMid,new DoubleVec(canvas.getWidth()*0.5,canvas.getHeight()*0.5));
                    if(tmpD.distance(refPoint)<1){
                        zoomCounter = 0;
                    }
                    else{
                        setRefPoint(tmpD,(int)(tmpD.distance(refPoint)*0.1));
                    }
                }
                updateBoxes();
        }
        if(refPointCounter>0){
            DoubleVec tmp = refPoint;
            refPoint = refPoint.add(refPointstep);
            refPointCounter--;
            if(crossedBorder()){
                refPoint=tmp;
                refPointCounter = 0;
            }
            updateBoxes();
        }
        visualizer.setZoom(zoomFactor);

        //delete background
        context.setFill(Color.BLACK);
        context.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

        smallContext.setFill(Color.BLACK);
        smallContext.fillRect(0,0,smallCanvas.getWidth(), smallCanvas.getHeight());

        //draw backgroud image
        final Image bgImage = images.getImage(StringProperty.backgroundImage);
        if(bgImage != null){
            int fieldSizeX = model.getFieldSizeX();
            int fieldSizeY = model.getFieldSizeY();
            DoubleVec vPos = modelToView(new DoubleVec(2*fieldSizeX, 2*fieldSizeY));
            context.drawImage(bgImage, vPos.x,vPos.y, fieldSizeX *zoomFactor*3, fieldSizeY *zoomFactor*3);
            vPos = modelToSmallView(new DoubleVec(2*fieldSizeX, 2*fieldSizeY));
            smallContext.drawImage(bgImage, vPos.x,vPos.y, fieldSizeX *smallZoomFactor*3, fieldSizeY *smallZoomFactor*3);
        }

        //render items & debug mode
        debug.setZoom(zoomFactor);
        for(MapContent p: model.getMap()){
            p.accept(visualizer);
            if(model.isDebugMode())p.accept(debug);
        }

        //for small Canvas
        if(map.getProjectiles().size() == 0) {
            smallVisualizer.setZoom(3);
            smallZoomFactor = 3;
            smallRefPoint = model.getActivePlayer().getTank().getChassis().getPos();
        }
        else{
            smallVisualizer.setZoom(5);
            smallZoomFactor = 5;
            smallRefPoint = map.getProjectiles().get(0).getPos();
        }
        delta = smallRefPoint.sub(smallMiddle);
        for(MapContent p: model.getMap()){
            p.accept(smallVisualizer);
        }

        //for debug-mode
        if(model.isDebugMode()){
            for(DoubleVec pos:model.getMap().getBorder().getEdges()){
                DoubleVec tmp = modelToView(pos);
                context.setFill(Color.BLUE);
                context.fillOval(tmp.x-50,tmp.y-50,100,100);
            }
            debug.visit(map);
            debug.visit(inner);
            debug.visit(outer);
        }

    }

    /**
     * manually zoom in
     */
    public void zoomIn(){
        if(zoomCounter == 0&&zoomFactor<20) {
            zoomCounter += 10;
        }
    }

    /**
     * manually zoom out
     */
    public void zoomOut(){
        if(zoomCounter == 0) {
            zoomCounter -= 10;
        }
    }

    /**
     * calculates the position on the view
     * @param pos position of the content
     * @return position on the view
     */
    public DoubleVec modelToView(DoubleVec pos){
        return refPoint.sub(pos).mult(zoomFactor).add(refPoint);
    }

    /**
     * Calculates a position for the model which will be represented at pos on the canvas
     * @param pos   position on the canvas
     * @return      related position in the model
     */
    public DoubleVec invModelToView(DoubleVec pos){
        return refPoint.sub((pos.sub(refPoint)).div(zoomFactor));
    }

    public DoubleVec refPoint(DoubleVec modelPos,DoubleVec canvasPos){
        return modelPos.mult(zoomFactor).add(canvasPos).div(zoomFactor+1);
    }

    /**
     * model to view for small canvas
     * @param pos
     * @return
     */
    public DoubleVec modelToSmallView(DoubleVec pos) {
        DoubleVec p = pos.sub(delta);
        return smallMiddle.sub(p).mult(smallZoomFactor).add(smallMiddle);
    }

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }
    public void setSmallCanvas(Canvas smallCanvas){
        this.smallCanvas = smallCanvas;
        smallMiddle = new DoubleVec(smallCanvas.getWidth()/2, smallCanvas.getHeight()/2);
        smallRefPoint = model.getActivePlayer().getTank().getChassis().getPos();
        delta= smallRefPoint.sub(smallMiddle); //for small Canvas
    }

    /**
     * context of view to draw on
     * @return
     */
    public GraphicsContext getGraphicsContext2D(){
        return canvas.getGraphicsContext2D();
    }

    /**
     * context of small view to draw on
     * @return
     */
    public GraphicsContext getSmallGraphicsContext2D(){
        return smallCanvas.getGraphicsContext2D();
    }

    public DoubleVec getRefPoint() {
        return refPoint;
    }

    public void setRefPoint(DoubleVec refPoint){
        refPointstep = refPoint.sub(this.refPoint).mult(0.1);
        refPointCounter = 10;
    }

    public void setRefPoint(DoubleVec refPoint,int steps){
        if(steps == 0) this.refPoint = refPoint;
        else{
        refPointstep = refPoint.sub(this.refPoint).div(steps);
        refPointCounter = steps;}
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
/**
     *
     * @return returns if a projectile is out of bounds
     */

    /**
     * checks if all projectiles are within a BoundingShape dependant on OutOfBounds
     */
    private boolean outOfBounds() {
        for (Projectile p : model.getMap().getProjectiles()){
            if (!outer.collisionWith(p.shape)) return true;}
        return false;
    }
    private boolean backIn() {
        for (Projectile p : model.getMap().getProjectiles()){
            if (!inner.collisionWith(p.shape)) return false;}
        return true;
    }

}
