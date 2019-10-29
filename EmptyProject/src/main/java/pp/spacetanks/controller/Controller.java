package pp.spacetanks.controller;


/**
 * Base class of all the Controllers from Spacetanks
 */

public class Controller {

    final GameEngine engine;

    Controller(GameEngine engine) {this.engine=engine;}

    /**
     * This Method is periodically called by the GameLoop
     */
    void update() {

    }

    /**
     * This Method is called, if the Game change the state and go to this Controller
     **/
    void entry() {

    }

    /**
     * This Method is called, if the state changes again and the Controller also have to change, so this Controller will be deactivated
     */
    void exit() {

    }


    public void handle(){

    }

}
