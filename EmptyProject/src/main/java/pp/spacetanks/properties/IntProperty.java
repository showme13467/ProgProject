package pp.spacetanks.properties;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The enumeration type of all integer properties of this game. Each property can be used to read its value in the
 * configuration stored in a {@link Properties} object.
 */
public enum IntProperty {
    /**
     * The width of each single filed of the game map.
     */
    fieldSizeX,
    /**
     * The width of each single filed of the game map.
     */
    fieldSizeY,
    /**
     * The duration how long a hint shall be shown.
     */
    flyingTime,
    attackTime;

    private static final Logger logger = Logger.getLogger(IntProperty.class.getName());

    /**
     * Returns the value of this property stored in the specified configuration.
     *
     * @param props stores the configuration
     * @return the configured value of this property or 0 if this property is not configured.
     */
    public int value(Properties props) {
        return value(props, 0);
    }

    /**
     * Returns the value of this property stored in the specified configuration.
     *
     * @param props        stores the configuration
     * @param defaultValue the default value of this property if it is not configured or does not have an integer value.
     * @return the configured value of this property or the specified default value if this property is not configured
     * or if the configured value is not an integer.
     */
    public int value(Properties props, int defaultValue) {
        final String property = props.getProperty(toString(), String.valueOf(defaultValue));
        try {
            return Integer.parseInt(property);
        }
        catch (NumberFormatException e) {
            logger.log(Level.WARNING, "wrong " + this + " value " + property, e);
            return defaultValue;
        }
    }

    public  void save(Properties props, int value){
        props.setProperty(toString(),Integer.toString(value));
    }
}
