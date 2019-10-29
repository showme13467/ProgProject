package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.Asteroid;
import pp.spacetanks.model.item.Planet;
import pp.spacetanks.model.item.StationaryAsteroid;
import pp.spacetanks.model.item.Tank;
import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.util.Rectangle;
import pp.spacetanks.view.Visitor;

/**
 * A rocket that accelerates towards the target if it is not shadowed through Asteroids or Planets
 */
public class HeatSeeker extends ExplosiveProjectile {
    protected Tank target;

    public HeatSeeker(GameModel model,ProjectileType type, DoubleVec pos,DoubleVec velocity){
        super(model,type, pos, velocity);
        target = model.getInactivePlayer().getTank();
    }

    @Override
    protected void move(double t){
        if(hasVision())setVel(getVel().add(velToTarget()));
        super.move(t);
    }

    public boolean hasVision(){
       return hasPointVision(target.getChassis().getPos());
    }

    public Rectangle getLineOfSight(){
        DoubleVec sub = target.getChassis().getPos().sub(getPos());
        return new Rectangle(getPos().add(sub.div(2)),sub.length(),0.1,sub.angle());
    }

    protected boolean hasPointVision(DoubleVec p){
        DoubleVec sub = p.sub(getPos());
        final Rectangle lineOfSight = new Rectangle(getPos().add(sub.div(2)),sub.length(),0,sub.angle());
            for(Asteroid a :model.getMap().getAsteroids()){
                   if (lineOfSight.collisionWith(a.shape)) return false;
                }
            for(StationaryAsteroid a :model.getMap().getStationaryAsteroids()){
                if (lineOfSight.collisionWith(a.shape)) return false;
            }
            for(Planet a :model.getMap().getPlanets()){
                if (lineOfSight.collisionWith(a.shape)) return false;
            }
            return true;
    }

    protected DoubleVec velToTarget(){
        return target.getPos().sub(getPos()).normalize().mult(power);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
