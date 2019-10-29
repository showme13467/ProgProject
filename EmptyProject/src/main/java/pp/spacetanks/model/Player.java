package pp.spacetanks.model;

import pp.spacetanks.model.item.Asteroid;
import pp.spacetanks.model.item.Planet;
import pp.spacetanks.model.item.Projectiles.ProjectileType;
import pp.spacetanks.model.item.StationaryAsteroid;
import pp.spacetanks.model.item.Tank;

/**
 * Represents a player of the game.
 */
public class Player {
    private int tankHits=0;
    private int planetHits=0;
    private int asteroidHits=0;
    private int projectilesLost=0;
    private int money=400;
    private String name;
    private Tank tank;

    /**
     * Sets the name for a player.
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the players name.
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a Player.
     * @param name the name of the Player.
     * @param tank the tank of the Player.
     */
    public Player(String name, Tank tank){
        this.name = name;
        this.tank = tank;
    }

    /**
     * Sets a tank as the players tank.
     * @param tank the tank to be set.
     */
    protected void setTank(Tank tank){this.tank=tank;}

    /**
     * Gets the players tank.
     * @return the players tank.
     */
    public Tank getTank() {
        return tank;
    }

    /**
     * Reduce the players money by the amount.
     * @param amount to be subtracted.
     */
    public void payMoney(int amount){money -=amount;}

    /**
     * Gets the current money of te player.
     * @return current money.
     */
    public int getMoney(){return money;}

    /**
     * Increase the players money by the amount.
     * @param amount to be added.
     */
    public void recieveMoney(int amount){money += amount;}

    /**
     * Get the number of hits
     * @return how many times the player has hit a tank
     */
    public int getTankHits() {
        return tankHits;
    }
    /**
     * Get the number of hits
     * @return how many times the player has hit a planet
     */
    public int getPlanetHits() {
        return planetHits;
    }
    /**
     * Get the number of hits
     * @return how many times the player has hit a asteroid
     */
    public int getAsteroidHits() {
        return asteroidHits;
    }
    /**
     * Get the number of misses
     * @return how many times the player has not hit with a projectile
     */
    public int getProjectilesLost() {
        return projectilesLost;
    }

    /**
     * resets all hit counters
     */
    void resetHits(){
        tankHits = 0;
        asteroidHits = 0;
        projectilesLost = 0;
        planetHits = 0;
    }

    /**
     *
     * @return if the player has lost
     */
    boolean hasLost(){return tank.isDestroyed()||cantBuyMun();}

    /**
     * Adds the hit of a tank
     * @param t the tank which has been hit
     */
    public void addHit(Tank t){tankHits+=1;}
    /**
     * Adds the hit of a planet
     * @param p the planet which has been hit
     */
    void addHit(Planet p){planetHits+=1;}
    /**
     * Adds the hit of a asteroid
     * @param a the asteroid which has been hit
     */
    void addHit(Asteroid a){asteroidHits+=1;}
    /**
     * Adds the hit of a stationary asteroid
     * @param a the stationary asteroid which has been hit
     */
    void addHit(StationaryAsteroid a){asteroidHits+=1;}
    /**
     * Adds the loss of a projectile
     */
    void addMiss(){projectilesLost+=1;}
    /**
     * Adds the loss of multiple projectiles
     * @param n number of lost projectiles
     */
    void addMiss(int n){projectilesLost+=n;}

    /**
     *
     * @return if the player cannot buy the cheapest projectile
     */
    private boolean cantBuyMun(){return ProjectileType.getMinPrice(tank.model)>money;}
    /**
     *
     * @return if the player can buy the current projectile of his tank
     */
    boolean canFire(){return tank.projectilePrice()<=money;}
    /**
     * lets the tank fire and pays the money for the projectile
     */
    public void fire(){
        payMoney(tank.projectilePrice());
        tank.fire();
    }
    }

