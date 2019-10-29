package pp.spacetanks.properties;

import java.util.Properties;

/**
 * The enumeration type of all boolean properties of this game. Each property can be used to read its value in the
 * configuration stored in a {@link Properties} object.
 */
public enum BooleanProperty {
    /**
     * Property indicating whether the number of frames per second shall be shown.
     */
    debugMode,
    muted;

    /**
     * Returns the value of this property stored in the specified configuration.
     *
     * @param props stores the configuration
     * @return the configured value of this property or false if this property is not configured.
     */
    public boolean value(Properties props) {
        return value(props, false);
    }

    /**
     * Returns the value of this property stored in the specified configuration.
     *
     * @param props        stores the configuration
     * @param defaultValue the default value of this property if it is not configured.
     * @return the configured value of this property or the specified default value if this property is not configured.
     */
    public boolean value(Properties props, boolean defaultValue) {
        return Boolean.parseBoolean(props.getProperty(toString(), String.valueOf(defaultValue)));
    }

    public void save(Properties props, boolean value){
        props.setProperty(toString(),Boolean.toString(value));
    }
}
