package pp.spacetanks.sound;

import javafx.animation.AnimationTimer;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;
import pp.spacetanks.model.GameModel;
import pp.spacetanks.notifications.Notification;
import pp.spacetanks.notifications.NotificationReceiver;
import pp.spacetanks.properties.StringProperty;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that manages all audio clips in the Droid game in a hash table and allows to play them.
 */
public class Sound implements NotificationReceiver {
        private static final Logger logger = Logger.getLogger(Sound.class.getName());
        private final GameModel model;
        private final Map<StringProperty, AudioClip> audioClips = new EnumMap<>(StringProperty.class);
        private boolean isMutedSound = false;
        private boolean isMutedEffects = false;


        public AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                if (!getAudioClip(StringProperty.ingameSound).isPlaying()) {
                    Sound.this.stop(StringProperty.ingameSound);
                    Sound.this.play(StringProperty.ingameSound);
                }
                else if(getAudioClip(StringProperty.backgroundMusic).isPlaying()){
                    timer.stop();
                    Sound.this.stop(StringProperty.ingameSound);
                }
            }
        };

        /**
         * Creates a new instance for the specified game model.
         */
        public Sound(GameModel model) {
            this.model = model;
            //load sounds
            loadAudioClip(StringProperty.cannonMovingSound);
            loadAudioClip(StringProperty.tankMovingSound);
            loadAudioClip(StringProperty.launchSound1);
            loadAudioClip(StringProperty.launchSound2);
            loadAudioClip(StringProperty.launchSound3);
            loadAudioClip(StringProperty.artilleryImpactSound);
            loadAudioClip(StringProperty.rocketImpactSound);
            loadAudioClip(StringProperty.explosionSoundPlanet);
            loadAudioClip(StringProperty.backgroundMusic);
            loadAudioClip(StringProperty.rocketSound);
            loadAudioClip(StringProperty.ingameSound);
            model.addReceiver(this);
        }

    /**
     * Return us our private boolean is muted
     * @return boolean for muted or not
     */
    public boolean getMutedSound(){return isMutedSound;}

    public boolean getMutedEffects(){return isMutedEffects;}

    /**
     * isMuted can be changed because of this method
     * @param x change is muted to x
     */
    public void setMutedSound(boolean x){isMutedSound = x;}

    public void setMutedEffects(boolean x){isMutedEffects = x;}


        /**
         * Loads a sound file specified by the property argument and stores its audio clip in the hash table of
         * all audio clips.
         *
         * @param prop the property whose value in the configuration indicates the path of the sound file.
         */
        private void loadAudioClip(StringProperty prop) {
            audioClips.put(prop, loadAudioClip(prop.value(model.getProperties())));
        }



        /**
         * Returns an audio clip that is specified by the property argument and must have been loaded previously using
         * {@link #//loadAudioClip(pp.droids.properties.StringProperty)}
         *
         * @param prop the property that has been used earlier to load the corresponding sound file.
         */
        public AudioClip getAudioClip(StringProperty prop) {
            return audioClips.get(prop);
        }

        /**
         * Plays an audio clip that is specified by the property argument and must have been loaded previously using
         * {@link #//loadAudioClip(pp.droids.properties.StringProperty)}
         *
         * @param prop the property that has been used earlier to load the corresponding sound file.
         */
        public boolean play(StringProperty prop) {
            final AudioClip audioClip = audioClips.get(prop);
            if (audioClip == null || model.isMuted())
                return false;
            else {
                audioClip.play();
                return true;
            }
        }

        public boolean stop(StringProperty prop){
            final AudioClip audioClip = audioClips.get(prop);
            if (audioClip == null)
                return false;
            else{
                audioClip.stop();
                return true;
            }
        }

        /**
         * Safe way for loading an audio clips from resource folder without
         * crashing on invalid url
         *
         * @param name file name of the audio clip
         * @return audio clip or null if name is null or the image could not be loaded.
         */
        private static AudioClip loadAudioClip(String name) {
            logger.finer("loading audio clip " + name);
            if (name == null) return null;

            // first try to load the audio clip from a local file
            final File file = new File(name);
            if (file.exists() && file.isFile() && file.canRead())
                try {
                    final AudioClip clip = new AudioClip(file.toURI().toURL().toString());
                    logger.info(() -> "loaded audio clip " + name + " from local file");
                    return clip;
                }
                catch (MediaException e) {
                    logger.info("failed to load local file " + name + ": " + e.getMessage());
                }
                catch (IllegalArgumentException | NullPointerException | IOException e) {
                    logger.log(Level.WARNING,
                            "failed to load audio clip " + name + ": " + e.getMessage(),
                            e);
                }

            // now try to load the audio clip via the class loader
            try {
                final URL resource = ClassLoader.getSystemClassLoader().getResource(name);
                if (resource == null) {
                    logger.fine("no resource " + name);
                    return null;
                }
                final AudioClip clip = new AudioClip(resource.toString());
                logger.info(() -> "loaded audio clip " + name + " via class loader");
                return clip;
            }
            catch (IllegalArgumentException | NullPointerException e) {
                logger.log(Level.WARNING,
                        "failed to load audio clip " + name + ": " + e.getMessage(),
                        e);
            }
            return null;
        }

    /**
         * Subscriber method according to the subscriber pattern. The method plays audio clips depending on the
         * specified notification if sound is not muted.
         */
        @Override
        public void notify(Notification notification) {
            logger.finer("received " + notification);
            if (!isMutedSound) {
                switch (notification) {
                    case BACKGROUND_MUSIC:
                        stop(StringProperty.ingameSound);
                        stop(StringProperty.backgroundMusic);
                        play(StringProperty.backgroundMusic);
                        break;
                    case INGAME_MUSIC:
                        stop(StringProperty.backgroundMusic);
                        stop(StringProperty.ingameSound);
                        play(StringProperty.ingameSound);
                        timer.start();
                        break;
                }
            }
            else{
                timer.stop();
                stop(StringProperty.ingameSound);
                stop(StringProperty.backgroundMusic);
            }
            if(!isMutedEffects) {
                switch (notification) {
                    case ROCKET_LAUNCHED:
                        play(StringProperty.rocketSound);
                        break;
                    case PROJECTILE_FIRED:
                        play(StringProperty.launchSound1);
                        break;
                    case TANK_MOVED:
                        play(StringProperty.tankMovingSound);
                        break;
                    case EXPLOSION:
                        play(StringProperty.explosionSoundPlanet);
                        break;
                    case CANNON_ADJUSTED:
                        play(StringProperty.cannonMovingSound);
                        break;
                    case ARTILLERY_IMPACT:
                        play(StringProperty.artilleryImpactSound);
                        break;
                    case ROCKET_IMPACT:
                        play(StringProperty.rocketImpactSound);
                        break;
                    case TANK_MOVED_STOP:
                        stop(StringProperty.tankMovingSound);
                        break;
                    case CANNON_ADJUST_STOP:
                        stop(StringProperty.cannonMovingSound);
                        break;
                }
            }
                switch(notification){
                    case MUTE:
                        stop(StringProperty.backgroundMusic);
                        stop(StringProperty.ingameSound);
                        break;
                    case NO_MUTE:
                        stop(StringProperty.ingameSound);
                        stop(StringProperty.backgroundMusic);
                        play(StringProperty.backgroundMusic);
                        break;

                }



        }




}
