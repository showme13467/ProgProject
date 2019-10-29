package pp.spacetanks.model;


import pp.spacetanks.model.item.MapContent;
import pp.spacetanks.model.item.*;
import pp.spacetanks.model.item.Projectiles.Projectile;
import pp.spacetanks.properties.StringProperty;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents the entire game map. It can be accessed as an unmodifiable {@linkplain java.util.List}
 * of all items consisting of the tanks,asteroids,planets,stationaryAsteroids and all different projectiles.
 */
public class Map extends AbstractList<MapContent> {

    // lists of items
    private final List<Tank> tanks = new ArrayList<>(2);
    private final List<Planet> planets = new ArrayList<>(4);
    private final List<Asteroid> asteroids = new ArrayList<>();
    private final List<StationaryAsteroid> stationaryAsteroids = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<Animation> animations = new ArrayList<>();
    private final Rectangle border;
    private final int width;
    private final int height;
    private final double fb=3;
    private final int asteroidAttackNumber = 5;
    private final GameModel model;
    /**
     * Creates a map of the specified size and with a droid at position (0,0)
     */
    public Map(GameModel model,int width, int height) {
        this.model = model;
        this.width = width;
        this.height = height;
        border = new Rectangle(new DoubleVec(width*0.5, height*0.5), fb*width, fb*height, 0);
    }

    /**
     * Returns the item (tank,planet, etc.) at the specified index in the list of all items.
     * Index 0, 1always indicates one of the two tanks.
     *
     * @param index the index in the list of all items.
     */
    @Override
    public MapContent get(int index) {
        int i = index;
        if (index <= 1) return tanks.get(i);
        i -= 2;
        if (i < planets.size()) return planets.get(i);
        i -= planets.size();
        if (i < asteroids.size()) return asteroids.get(i);
        i -= asteroids.size();
        if (i < stationaryAsteroids.size()) return stationaryAsteroids.get(i);
        i -= stationaryAsteroids.size();
        if (i < animations.size()) return animations.get(i);
        i -= animations.size();
        return projectiles.get(i);
    }

    /**
     *
     * @return the rectangle representing the border of the map
     */
    public Rectangle getBorder(){
        return  border;
    }
    /**
     * Returns the number of all items in the map
     */
    @Override
    public int size() {
        return 2 + planets.size() + asteroids.size() + stationaryAsteroids.size() + projectiles.size()+animations.size();
    }

    /**
     * Returns all tanks
     *
     * @return the list of all tanks
     */
    public List<Tank> getTanks() {
        return Collections.unmodifiableList(tanks);
    }

    /**
     * Returns all planets
     *
     * @return the list of all planets
     */
    public List<Planet> getPlanets() {
        return Collections.unmodifiableList(planets);
    }

    /**
     * Returns all asteroids still existing
     *
     * @return the list of all asteroids still existing
     */
    public List<Asteroid> getAsteroids() {
        return Collections.unmodifiableList(asteroids);
    }

    /**
     * Returns all flying projectiles.
     *
     * @return the list of all flying projectiles
     */
    public List<Projectile> getProjectiles() {
        return Collections.unmodifiableList(projectiles);
    }

    /**
     * Returns all stationary asteroids still existing
     *
     * @return the list of all stationary asteroids still existing
     */
    public List<StationaryAsteroid> getStationaryAsteroids() {
        return Collections.unmodifiableList(stationaryAsteroids);
    }

    /**
     * Returns the height (i.e., the number of rows) of the map
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width (i.e., the number of columns) of the map
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }



    /**
     * Called once per frame. This method calls the update method of each item in this map and removes items that
     * cease to exist.
     *
     * @param deltaTime time in seconds since the last update call
     */
    public void update(double deltaTime) {
        for (MapContent item : this)
            item.update(deltaTime);

        // process hits by projectiles
        for (Projectile proj : projectiles) {
            for (Tank t : tanks)
                for (TankComponent cmp : t.getComponents())
                    if (proj.collisionWith(cmp) && !proj.isDestroyed()) {
                        proj.hit(cmp);
                       if (proj.type.count)model.getActivePlayer().addHit(t);
                    }
            for(Planet p : planets)
                if (p.collisionWith(proj) && !proj.isDestroyed()) {
                    proj.hit(p);
                    if (proj.type.count)model.getActivePlayer().addHit(p);
                }
            for(Asteroid a:asteroids)
                if (proj.collisionWith(a) && !proj.isDestroyed()) {
                    proj.hit(a);
                    if (proj.type.count)model.getActivePlayer().addHit(a);
                }
            for(StationaryAsteroid a:stationaryAsteroids)
                if (proj.collisionWith(a) && !proj.isDestroyed()) {
                    proj.hit(a);
                    if (proj.type.count)model.getActivePlayer().addHit(a);
                }
            if(isOutOfBorder(proj)){
                proj.destroy();
                if (proj.type.count)model.getActivePlayer().addMiss();
            }
        }

        // process hits by asteroids
        for (Asteroid ast : asteroids){
            for (Tank t : tanks)
                for (TankComponent cmp : t.getComponents())
                    if (ast.collisionWith(cmp) && !ast.isDestroyed()) {
                        ast.hit(cmp);
                    }
            for(StationaryAsteroid a:stationaryAsteroids)
                if (ast.collisionWith(a) && !ast.isDestroyed()) {
                    ast.hit(a);
                }
            for(Planet p : planets)
                if (ast.collisionWith(p) && !ast.isDestroyed()) {
                    ast.hit(p);
                }
            if(isOutOfBorder(ast))ast.destroy();
        }
        //hits by stationaryAsteroids already processed

        // remove all destroyed items, except the tanks
        List<MapContent> removed = new ArrayList<>();
        for (MapContent item : this)
            if (item.isDestroyed())
                removed.add(item);
        stationaryAsteroids.removeAll(removed);
        planets.removeAll(removed);
        asteroids.removeAll(removed);
        projectiles.removeAll(removed);
        animations.removeAll(removed);
    }

    /**
     * @param item the item to be checked
     * @return if the item left the map
     */
    public boolean isOutOfBorder(MapContent item) {
        return !border.collisionWith(item.shape);
    }

    /**
     * Adds a tank to this map.
     */
    public void add(Tank t) {
        tanks.add(t);
    }

    /**
     * Adds a planet to this map.
     */
    public void add(Planet p) {
        planets.add(p);
    }

    /**
     * Adds an asteroid to the list of asteroids.
     */
    public void add(Asteroid a) {
        asteroids.add(a);
    }

    /**
     * Adds an animation to the list of animations.
     */
    public void add(Animation a) {
        animations.add(a);
    }

    /**
     * Adds an ufo to the list of ufos
     */
    public void add(StationaryAsteroid st) {
        stationaryAsteroids.add(st);
    }


    /**
     * Adds a projectile to this map.
     */
    public void add(Projectile p) {
        projectiles.add(p);
    }

    /**
     * Spawns new Asteroids at random places around the initial field of view
     */
    void asteroidAttack(){
        for(int i=0;i<asteroidAttackNumber;i++){
            DoubleVec pos = (Math.random()>0.5)?new DoubleVec((Math.random()>0.5)?1.2*width:-0.2*width,
                                                                Math.random()*height)
                                                :new DoubleVec(Math.random()*width,
                                                                 (Math.random()>0.5)?1.2*height:-0.2*height);
            add(new Asteroid(model,pos,new DoubleVec(model.getFieldSizeX()/2,model.getFieldSizeY()/2).sub(pos).normalize().mult(0.1),25));
        }
    }

    /**
     * clears the entire list of projectiles
     */
    public void clearProjectiles(){
        projectiles.clear();
    }

    /**
     * clears the entire list of animations
     */
    public void clearAnimations(){
        animations.clear();
    }
    /**
     * clears the entire list
     */
    public void clearAll(){
        animations.clear();
        projectiles.clear();
        asteroids.clear();
        stationaryAsteroids.clear();
        planets.clear();
    }
}
