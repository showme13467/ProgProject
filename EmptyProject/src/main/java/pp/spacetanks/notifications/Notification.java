package pp.spacetanks.notifications;

public enum Notification {
    /**
     * Indicates that the game map has changed.
     */
    MAP_UPDATE,
    /**
     * Indicates that a rocked was launched.
     */
    ROCKET_LAUNCHED,
    /**
     * Indicates that a projectile was fired.
     */
    PROJECTILE_FIRED,
    /**
     * Indicated that a tank was moved.
     */
    TANK_MOVED,
    /**
     * Indicated that a tank has stoppped moving.
     */
    TANK_MOVED_STOP,
    /**
     * Indicates that a cannon was adjusted.
     */
    CANNON_ADJUSTED,
    /**
     * Indicates that a cannon has stopped adjusting.
     */
    CANNON_ADJUST_STOP,
    /**
     * Indicates that an explosion took place.
     */
    EXPLOSION,
    /**
     * Indicates that artillery has hit.
     */
    ARTILLERY_IMPACT,
    /**
     * Indicates that a rocket has hit.
     */
    ROCKET_IMPACT,
    /**
     * Indicates that the background Music is palying
     */
    BACKGROUND_MUSIC,
    /**
     * Indicates that the ingame music is playing
     */
    INGAME_MUSIC,
    /**
     * Indicates that the view was zoomed out.
     */
    ZOOM_OUT,
    /**
     * Indicates that the view was zoomed in.
     */
    ZOOM_IN,
    /**
     * Inidicates that a tank was hit.
     */
    TANK_HIT,
    /**
     * Indicates that the game is muted
     */
    MUTE,
    NO_MUTE;




}
