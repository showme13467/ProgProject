package pp.spacetanks.view;

import pp.spacetanks.model.item.*;
import pp.spacetanks.model.item.Projectiles.*;

/**
 * visitor methods
 */
public interface Visitor {
    void visit(Tank tank);
    void visit(HeatSeeker projectile);
    void visit(RemotableRocket projectile);
    void visit(ArtilleryProjectile projectile);
    void visit(DropExplosiveProjectile projectile);
    void visit(ExplosiveProjectile projectile);
    void visit(PathFindingCluster projectile);
    void visit(ClusterRocket projectile);
    void visit(ClusterBomb projectile);
    void visit(FireworkRocket projectile);
    void visit(AntiAsteroidCluster projectile);
    void visit(DropRocket projectile);
    void visit(PremiumAsteroidKiller premiumAsteroidKiller);
    void visit(AsteroidKiller asteroidKiller);
    void visit(Asteroid asteroid);
    void visit(StationaryAsteroid stationary);
    void visit(Planet planet);
    void visit(Cannon canon);
    void visit(MapContent content);
    void setZoom(double zoomFactor);
    void visit(HelplineElement line);
    void visit(Animation animation);
}
