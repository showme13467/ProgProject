package pp.spacetanks.view;


import javafx.scene.image.Image;
import pp.spacetanks.model.GameModel;
import pp.spacetanks.properties.StringProperty;

import java.io.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Images {
    private static final Logger logger = Logger.getLogger(Images.class.getName());

    private final Properties props;
    private final Map<StringProperty, Image> images = new EnumMap<>(StringProperty.class);

    /**
     * Creates a new instance of the specified game model.
     */
    public Images(GameModel model){
        this.props = model.getProperties();
        loadImage(StringProperty.asteroidImage);
        loadImage(StringProperty.stationaryAsteroidImage);
        loadImage(StringProperty.planetImage1);
        loadImage(StringProperty.planetImage2);
        loadImage(StringProperty.planetImage3);
        loadImage(StringProperty.planetImage4);
        loadImage(StringProperty.planetImage5);
        loadImage(StringProperty.planetImage6);
        loadImage(StringProperty.planetImage7);
        loadImage(StringProperty.planetImage8);
        loadImage(StringProperty.planetImage9);
        //Animations
        loadImage(StringProperty.explosionAnimation);
        loadImage(StringProperty.asteroidDustAnimation);
        loadImage(StringProperty.metalHitAnimation);
        loadImage(StringProperty.planetHitAnimation);
        loadImage(StringProperty.shotAnimation);
        //M55
        loadImage(StringProperty.M55Image);
        loadImage(StringProperty.M55AntennaImage);
        loadImage(StringProperty.M55CannonImage);
        loadImage(StringProperty.M55ChainImage);
        loadImage(StringProperty.M55AntennaDestroyedImage);
        loadImage(StringProperty.M55CannonDestroyedImage);
        loadImage(StringProperty.M55ChainDestroyedImage);
        loadImage(StringProperty.m55DestroyedImage);
        //MARS
        loadImage(StringProperty.MARSCannonDestroyedImage);
        loadImage(StringProperty.MARSCannonImage);
        loadImage(StringProperty.MARSChainDestroyedImage);
        loadImage(StringProperty.MARSChainImage);
        loadImage(StringProperty.marsImage);
        loadImage(StringProperty.marsDestroyedImage);
        loadImage(StringProperty.MARSAntennaDestroyedImage);
        loadImage(StringProperty.MARSAntennaImage);
        //Panzerhaubitze 2000
        loadImage(StringProperty.PANZERHAUBITZE2000AntennaDestroyedImage);
        loadImage(StringProperty.PANZERHAUBITZE2000AntennaImage);
        loadImage(StringProperty.PANZERHAUBITZE2000CannonDestroyedImage);
        loadImage(StringProperty.PANZERHAUBITZE2000CannonImage);
        loadImage(StringProperty.PANZERHAUBITZE2000ChainDestroyedImage);
        loadImage(StringProperty.PANZERHAUBITZE2000ChainImage);
        loadImage(StringProperty.haubitzeImage);
        loadImage(StringProperty.haubitzeDestroyedImage);

        loadImage(StringProperty.backgroundImage);
        loadImage(StringProperty.artilleryProjectileImage);
        loadImage(StringProperty.remotableRocketImage);
        loadImage(StringProperty.heatSeekerImage);
        loadImage(StringProperty.smallCarryImage);
        loadImage(StringProperty.bigCarryImage);
        loadImage(StringProperty.missileImage);
        loadImage(StringProperty.swarmImage);
        loadImage(StringProperty.fireworkRocket1Image);
        loadImage(StringProperty.fireworkRocket2Image);
        loadImage(StringProperty.fireworkRocket3Image);
        loadImage(StringProperty.fireworkRocket4Image);
        loadImage(StringProperty.fireworkRocket5Image);
        loadImage(StringProperty.blueshotAnimation);
        loadImage(StringProperty.redshotAnimation);
        loadImage(StringProperty.yellowshotAnimation);
        loadImage(StringProperty.violetshotAnimation);
        loadImage(StringProperty.gravBombAnimation);
        loadImage(StringProperty.hellfireAnimation);
        //loadImage(StringProperty.backgroundImage);
        //loadImage(StringProperty.backgroundImage);
    }

    /**
     * Loads an image file specified by the property argument and stores the image in the hash table of
     * all images.
     *
     * @param prop The property whose value in the configuration indicates the path of the image file.
     */
    private void loadImage(StringProperty prop){
        images.put(prop, loadImage(prop.value(props)));
    }

    /**
     * Returns an imahe that is specified by the property argument and must have been loaded previously using
     * {@Link #loadImagge(pp.spacetanks.properties.StringProperty)}.
     *
     * @param prop The property that has been used earlier to load the corresponding image file.
     * @return
     */
    public Image getImage(StringProperty prop){
        return images.get(prop);
    }

    /**
     * Safe way for loading images without crashing on invalid URL.
     * @param name file name of the image file
     * @return Image or null if name is null or the image could not be loaded.
     */
    private static Image loadImage(String name){
        logger.finer("trying to load image " + name);
        if(name == null) return null;

        //first try to load the image from a local file
        final File file = new File(name);
        if(file.exists() && file.isFile() && file.canRead()) {
            try (InputStream is = new FileInputStream(file)) {
                Image image = new Image(is);
                logger.info(() -> "loaded image " + name + " from local file");
                return image;
            } catch (FileNotFoundException e) {
                logger.info("failed to load local file: " + e.getMessage());
            } catch (IllegalArgumentException | NullPointerException | IOException e) {
                logger.log(Level.WARNING, "failed to load image " + name + ": " + e.getMessage(), e);
            }
        }

        // now try to load the image via the class loader
        try{
            Image image = new Image(name);
            logger.info(() -> "loaded image " + name + " via class loader");
            return image;
        }
        catch(IllegalArgumentException | NullPointerException e){
            logger.log(Level.WARNING, "fialed to load image " + name + e.getMessage(), e);
        }

        return null;
     }

}
