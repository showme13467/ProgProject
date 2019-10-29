package pp.spacetanks;

import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import pp.spacetanks.controller.GameEngine;
import pp.spacetanks.notifications.Notification;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of the game.
 */
public class SpaceTanksApp extends Application{

    private static final Logger logger = Logger.getLogger(SpaceTanksApp.class.getName());
    private static final String PROPERTIES_FILE = "spacetanks.properties";

    private final Properties properties = new Properties();

    static{
        //initialize logging if not initialized by logging config file or config class
        if(System.getProperty("java.util.logging.config.file") == null &&
                System.getProperty("java.util.logging.config.class") == null){
            final ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.ALL);
            final Logger parent = Logger.getLogger("pp");
            parent.addHandler(handler);
            parent.setLevel(Level.FINE);
        }
    }

    public SpaceTanksApp(){load(PROPERTIES_FILE);
         }

    /**
     * Main method of the spacetanks app.
     * @param args input args
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Starts the game. This mehtod is automatically called when JavaFX starts up.
     * @param stage The main stage provided bei JavaFX
     */
    @Override
    public void start(Stage stage){
        final GameEngine engine = new GameEngine(stage, properties);
        engine.getModel().notifyReceivers(Notification.BACKGROUND_MUSIC);
        stage.show();
        engine.gameLoop();
    }

    private void load(String fileName) {
        //first load properties using class loader
        try {
            final InputStream ressource = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            if (ressource == null) {
                logger.info("Class loader cannot find " + fileName);
            } else {
                properties.load(ressource);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
/*
        // and now try to read the properties file
        try (FileReader reader = new FileReader(fileName)) {
            properties.load(reader);
        }
        catch (FileNotFoundException e) {
            logger.log(Level.INFO, e.getMessage(), e);
        }
        catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        } */
        logger.fine(() -> "properties: " + properties);
    }
}






