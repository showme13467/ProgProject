package pp.spacetanks.model.item;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.Projectiles.Projectile;
import pp.spacetanks.util.DoubleVec;
import java.util.Iterator;

/**
 * An Iterable Iterator used to represent the Helpline of the {@linkplain pp.spacetanks.model.item.Cannon}
 */
public class Helpline implements Iterator<HelplineElement>,Iterable<HelplineElement> {
    private final int leaveOut =20;
    private int produced =0;
    private final double time = 0.015;
    private final GameModel model;
    private DoubleVec origin;
    private Projectile projectile;
    private final Cannon cannon;

    /**
     * Creates a new Helpline
     * @param cannon    the cannon for which to calculate a Helpline
     */
    public Helpline(Cannon cannon) {
        this.cannon = cannon;
        this.model = cannon.model;
        projectile = cannon.getProjectileType().getProjectile(model,cannon.projectilePosition(),cannon.projectileVelocity());
        origin = cannon.projectilePosition();
    }

    /**
     * resets the Helpline
     */
    void reset(){
        produced = 0;
        projectile = cannon.getProjectileType().getProjectile(model,cannon.projectilePosition(),cannon.projectileVelocity());
        origin = cannon.projectilePosition();
    }

    /**
     * @return      whether the Iterator has a next element
     */
    @Override
    public boolean hasNext() {
        return !projectile.isDestroyed()&&(model.isDebugMode()?produced<100:
                projectile.getPos().sub(origin).length()<=150 &&produced<30);
    }

    /**
     * @return      the Helpline itself will be returned
     */
    @Override
    public Iterator<HelplineElement> iterator() {
        return this;
    }

    /**
     * @return  the next element
     */
    @Override
    public HelplineElement next() {
        //produce HelplineElement
        HelplineElement tmp = new HelplineElement(projectile.getPos());

        //update & check for collision
        for (int i=0;i<leaveOut;i++) {
            projectile.update(time);
            for (Planet p : model.getMap().getPlanets()) {
                if (projectile.collisionWith(p)){
                    projectile.destroy();
                }
            }
        }
        if (model.getMap().isOutOfBorder(projectile)) projectile.destroy();

        produced +=1;
        return tmp;
    }
}
