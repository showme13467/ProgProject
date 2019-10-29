package pp.spacetanks.model.item.Projectiles;

import pp.spacetanks.model.GameModel;
import pp.spacetanks.model.item.TankType;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.util.*;
import java.util.ArrayList;

/**
 * Enum representing the different Projectiles.
 */
public enum ProjectileType {
    ARTILLERIEGESCHOSS(0,12.23,2.34,25,0,0,5,Notification.PROJECTILE_FIRED,"Artilleriegeschoss"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new ArtilleryProjectile(model,ARTILLERIEGESCHOSS,pos,vel);
        }
    },
    ARTILLERIEBOMBE(0,9.6,2.46,25,25,25,15,Notification.PROJECTILE_FIRED,"Artilleriesprengsatz"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new ExplosiveProjectile(model,ARTILLERIEBOMBE,pos,vel);
        }
    },
    ASTEROIDKILLER(0,11.76,4.08,25,0,0,5,Notification.PROJECTILE_FIRED,"Asteriden Killer"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new AsteroidKiller(model,ASTEROIDKILLER,pos,vel);
        }
    },
    PREMIUMASTEROIDKILLER(0,11.76,4.08,25,20,0,15,Notification.PROJECTILE_FIRED,"Premium Asteriden Killer"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new PremiumAsteroidKiller(model,PREMIUMASTEROIDKILLER,pos,vel);
        }
    },
    ANTIASTEROIDCLUSTER(0.5,13.17,3.96,0,25,25,100,Notification.ROCKET_LAUNCHED,"Anti Asteroiden Cluster"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new AntiAsteroidCluster(model,CLUSTERROCKET,pos,vel.div(5),ASTEROIDKILLER);
        }
    },
    PREMIUMANTIASTEROIDCLUSTER(0.5,13.17,3.96,0,25,25,150,Notification.ROCKET_LAUNCHED,"Premium anti Asteroiden Cluster"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new AntiAsteroidCluster(model,PREMIUMANTIASTEROIDCLUSTER,pos,vel.div(5),PREMIUMASTEROIDKILLER);
        }
    },
    DROPARTILLERIEBOMB(0,9.6,2.46,25,25,25,60,Notification.PROJECTILE_FIRED,"Stopp-Artilleriesprengsatz"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new DropExplosiveProjectile(model,DROPARTILLERIEBOMB,pos,vel);
        }
    },
    DROPARTILLERY(0,9.6,2.46,25,0,0,30,Notification.PROJECTILE_FIRED,"Stoppgeschoss"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new DropArtillery(model,DROPARTILLERY,pos,vel);
        }
    },
    CLUSTERBOMB(0,11.76,6.33,25,0,0,100,Notification.PROJECTILE_FIRED,"Cluster Bomb"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new ClusterBomb(model,CLUSTERBOMB,pos,vel,ARTILLERIEGESCHOSS);
        }
    },
    PREMIUMCLUSTERBOMB(0,11.76,6.33,25,0,0,400,Notification.PROJECTILE_FIRED,"Premium Cluster Bomb"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new ClusterBomb(model,PREMIUMCLUSTERBOMB,pos,vel,ARTILLERIEBOMBE);
        }
    },
    FERNZUENDGESCHOSS(0.5,9.6,2.46,0,30,35,25,Notification.ROCKET_LAUNCHED,"Fernzündgeschoss"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new RemotableRocket(model, FERNZUENDGESCHOSS,pos,vel.div(5));
        }
    },
    DROPROCKET(0.5,9.6,2.46,0,30,35,25,Notification.ROCKET_LAUNCHED,"Stopprakete"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new DropRocket(model,DROPROCKET,pos,vel.div(5));
        }
    },
    CLUSTERROCKET(0.5,11.76,6.33,0,30,35,100,Notification.ROCKET_LAUNCHED,"Cluster Rocket"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new ClusterRocket(model,CLUSTERROCKET,pos,vel.div(5),ARTILLERIEGESCHOSS);
        }
    },
    PREMIUMCLUSTERROCKET(0.5,11.76,6.33,0,30,35,500,Notification.ROCKET_LAUNCHED,"Premium Cluster Rocket"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new ClusterRocket(model,PREMIUMCLUSTERROCKET,pos,vel.div(5),ARTILLERIEBOMBE);
        }
    },
    GRAVITATIONBOMB(0,12.23,2.34,25,25,0,100,Notification.PROJECTILE_FIRED,"Gravitationsbombe"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new GravitationBomb(model,GRAVITATIONBOMB,pos,vel);
        }
    },
    RAKETE(0.5,17*0.8,4*0.8,50,30,25,100,Notification.ROCKET_LAUNCHED,"Rakete"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new HeatSeeker(model,RAKETE,pos,vel);
        }
    },
    FEURWERKSRAKETE(0.5,4.52*3.5,0.9*3.5,0,15,25,0,Notification.ROCKET_LAUNCHED,"Feuerwerksrakete",false){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new FireworkRocket(model,FEURWERKSRAKETE,pos,vel);
        }
    },
    HELLFIREROCKET(0.5,13.17,3.96,0,15,25,666,Notification.ROCKET_LAUNCHED,"Höllenfeuer"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new HellFireRocket(model,HELLFIREROCKET,pos,vel);
        }
    },
    GOTTESZORN(0.5,17,4,50,30,25,10000,Notification.ROCKET_LAUNCHED,"Gottes Zorn"){
        @Override
        public Projectile getProjectile(GameModel model, DoubleVec pos, DoubleVec vel) {
            return new PathFindingCluster(model,GOTTESZORN,pos,vel,RAKETE);
        }


    };

    /**
     * Create a new ProjectileType
     * @param acceleration   Acceleration of a ExplosiveProjectile.
     * @param width          Width for rectangular Projectiles.
     * @param height         Height for rectangular Projectiles.
     * @param damage         Damage dealt on hit.
     * @param radius         Explosion radius.
     * @param radiusDamage   Explosion damage.
     * @param price          Price of the Projectile.
     * @param sound          The sound played on firing the Projectile.
     * @param name           The name of the Projectile.
     * @param count          If a hit counted for the statistics.
     */
    ProjectileType(double acceleration,double width,double height,int damage, int radius,
                   int radiusDamage, int price, Notification sound,String name,boolean count){
        this.damage = damage;
        this.radius = radius;
        this.radiusDamage = radiusDamage;
        this.width = width;
        this.height = height;
        this.price = price;
        this.power = acceleration;
        this.sound = sound;
        this.name = name;
        this.count = count;
    }

    ProjectileType(double acceleration,double width,double height,int damage, int radius,
                   int radiusDamage, int price, Notification sound,String name){
        this(acceleration, width, height, damage, radius, radiusDamage, price, sound, name, true);
    }

    public final String name;
    private final Notification sound;
    public final double width;
    public final double height;
    public final int damage;
    public final int radius;
    public final int radiusDamage;
    public final int price;
    public final double power;
    public final double rocketPower=0.04;
    public final boolean count;

    /**
     * Creates the represented Projectile which will be added to the map
     * @param model     the GameModel
     * @param pos       the position of the Projectile
     * @param vel       the velocity of the Projectile
     */
    public void makeProjectile(GameModel model,DoubleVec pos,DoubleVec vel){
        model.notifyReceivers(sound);
        model.getMap().add(getProjectile(model,pos,vel));
    }
    /**
     * Creates the represented Projectile that will be returned
     * @param model     the GameModel
     * @param pos       the position of the Projectile
     * @param vel       the velocity of the Projectile
     */
    public abstract Projectile getProjectile(GameModel model,DoubleVec pos,DoubleVec vel);

    /**
     * @return  the minimum price for a Projectile
     */
    public static int getMinPrice(GameModel model){
        return Calculations.minPrice(getAllTypes(model));
    }

    /**
     * @return  all available ProjectileTypes to be fired
     */
    public static ArrayList<ProjectileType> getAllTypes(GameModel model){
        ArrayList<ProjectileType> tmp = new ArrayList<>();
        tmp.add(ARTILLERIEGESCHOSS);
        tmp.add(ARTILLERIEBOMBE);
        tmp.add(ANTIASTEROIDCLUSTER);
        tmp.add(PREMIUMANTIASTEROIDCLUSTER);
        tmp.add(DROPARTILLERIEBOMB);
        tmp.add(DROPARTILLERY);
        tmp.add(CLUSTERBOMB);
        tmp.add(PREMIUMCLUSTERBOMB);
        tmp.add(FERNZUENDGESCHOSS);
        tmp.add(DROPROCKET);
        tmp.add(CLUSTERROCKET);
        tmp.add(PREMIUMCLUSTERROCKET);
        if(model.getActivePlayer().getTank().getType()==TankType.MARS)tmp.add(GRAVITATIONBOMB);
        if(model.getActivePlayer().getMoney()>=100000)tmp.add(HELLFIREROCKET);
        tmp.add(RAKETE);
        tmp.add(GOTTESZORN);
        tmp.sort((ProjectileType p1,ProjectileType p2)->p1.price-p2.price);
        return tmp;
    }

    /**
     * @return  the ProjectileType with which a cannon will be initialized
     */
    public static ProjectileType initProjectile(){return ARTILLERIEGESCHOSS;}

    @Override
    public String toString() {
        return name;
    }
}
