package pp.spacetanks.properties;

import java.util.Properties;

/**
 * The enumeration type of all string properties of this game. Each property can be used to read its value in the
 * configuration stored in a link {@link java.util.Properties} object.
 */
public enum StringProperty {
    /**
     * Path of the BAckgroundimage
     */
    backgroundImage,
    /**
     * Path of the image of an stationary obstacle
     */
    stationaryAsteroidImage,
    /**
     * Path of the image of an obstacle
     */
    asteroidImage,
    /**
     * Path of the image of a artillery projectile
     */
    artilleryProjectileImage,
    /**
     * Path of the image of a remote able rocket
     */
    remotableRocketImage,
    /**
     * Path of the image of a heat seeker rocket
     */
    heatSeekerImage,
    /**
     * Path to the image of a M55 tank
     */
    M55Image,
    /**
     * Path to the image of a PANZERHAUBITZE 2000 tank
     */
    haubitzeImage,
    /**
     * Path of the image of a MARS tank
     */
    marsImage,
    /**
     * Path of the image of a destroyed M55
     */
    m55DestroyedImage,
    /**
     * Path of the image for test tank
     */
    M55Komplett,
    /**
     * Path of the image of a destroyed MARS
     */
    marsDestroyedImage,
    /**
     * Path of the image of a destroyed PANZERHAUBITZE 2000
     */
    haubitzeDestroyedImage,
    /**
     * Path of the image of the M55 chain
     */
    M55ChainImage,
    /**
     * Path of the image of the M55 destroyed chain
     */
    M55ChainDestroyedImage,
    /**
     *  Path of the image of the MARS chain
     */
    MARSChainImage,
    /**
     * Path of the image of the MARS destroyed chain
     */
    MARSChainDestroyedImage,
    /**
     * Path of the image of the PANTZERHAUBITZE 2000 chain
     */
    PANZERHAUBITZE2000ChainImage,
    /**
     * Path to the image of the PANZERHAUBITZE 2000 destroyed chain
     */
    PANZERHAUBITZE2000ChainDestroyedImage,
    /**
     * Path of the image of the M55 antenna
     */
    M55AntennaImage,
    /**
     * Path of the image of the M55 destroyed chain
     */

    M55AntennaDestroyedImage,
    /**
     * Path of the image of the MARS antenna
     */
    MARSAntennaImage,
    /**
     * Path of the image of the MARS destroyed antenna
     */
    MARSAntennaDestroyedImage,
    /**
     * Path of the image of the PANZERHAUBITZE 2000 antenna
     */
    PANZERHAUBITZE2000AntennaImage,
    /**
     * Path of the image of the PANZERHAUBITZE 2000 destroyed antenna
     */
    PANZERHAUBITZE2000AntennaDestroyedImage,
    /**
     * Path of the image of the M55 cannon
     */
    M55CannonImage,
    /**
     * Path of the image of the M55 destroyed cannon
     */
    M55CannonDestroyedImage,
    /**
     * Path of the image of the MARS cannon
     */
    MARSCannonImage,
    /**
     * Path of the image of the MARS destroyed cannon
     */
    MARSCannonDestroyedImage,
    /**
     * Path of the image of the PANZERHAUBITZE 2000 cannon
     */
    PANZERHAUBITZE2000CannonImage,
    /**
     * Path of the image of the PANZERHAUBITZE 2000 destroyed cannon
     */
    PANZERHAUBITZE2000CannonDestroyedImage,
    /**
     * Path of the image of the Planet
     */
    planetImage1,
    planetImage2,
    planetImage3,
    planetImage4,
    planetImage5,
    planetImage6,
    planetImage7,
    planetImage8,
    planetImage9,
    bigCarryImage,
    smallCarryImage,
    missileImage,
    swarmImage,

    fireworkRocket1Image,
    fireworkRocket2Image,
    fireworkRocket3Image,
    fireworkRocket4Image,
    fireworkRocket5Image,
    blueshotAnimation,
    redshotAnimation,
    violetshotAnimation,
    yellowshotAnimation,
    gravBombAnimation,
    hellfireAnimation,
            /**
             * Path to the sound file played when a projectile is launched
             */
    launchSound1,
    launchSound2,
    launchSound3,
    /**
     * Path to the sound file played when a projectile hits an obstacle or enemy
     */
    rocketImpactSound,
    artilleryImpactSound,
    explosionSoundPlanet,
    /**
     * Path of the sound file played when a rocket activated its thrust.
     */
    rocketSound,
    /**
     * Path of the background sound file
     */
    backgroundMusic,
    /**
     * Path of the image of a rocket
     */
    explosionAnimation,
    /**
     * Path of the sound file played when a tank is moving
     */
    tankMovingSound,
    /**
     * Path of the sound file played when a game starts
     */
    ingameSound,
    /**
     * Path of the sound file played when a game is over
     */
    winnerSound1,
    winnerSound2,
    winnerSound3,
    winnerSound4,
    winnerSound5,
    winnerSound6,
    winnerSound7,
    /**
     * Path of the sound file played when a tank is moving
     */
    cannonMovingSound,
    shotAnimation,
    planetHitAnimation,
    metalHitAnimation,
    asteroidDustAnimation;


    /**
     * Returns the value of this property stored in the specified configuration.
     *
     * @param
     * @return
     */
    public String value(Properties props){
        return value(props, null);
    }

    /**
     * Returns the value of this property stored in the specified configuration.
     *
     * @param props stores the configuration
     * @param defaultValue the default value of this property if it is not configured.
     * @return the configured value of this property or the specified default value if thus property is not configured.
     */
    public String value(Properties props, String defaultValue){
        return props.getProperty(toString(), defaultValue);
    }

    public  void save(Properties props, String value){
        props.setProperty(toString(),value);
    }
}
