package pp.spacetanks.model;

import pp.spacetanks.model.item.Asteroid;
import pp.spacetanks.model.item.Planet;
import pp.spacetanks.model.item.StationaryAsteroid;
import pp.spacetanks.model.item.Tank;
import pp.spacetanks.util.Circle;
import pp.spacetanks.util.DoubleVec;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class which creates new maps.
 */
public class MapCreator {
    private final GameModel model;
    private int fieldsizeX;
    private int fieldsizeY;
    private int space = 100;
    private double stAstLiklehood = 0.5;

    /**
     * creates a new MapCreator
     * @param model the model to the map
     */
    public MapCreator(GameModel model){
        this.model = model;
        fieldsizeX = model.getFieldSizeX();
        fieldsizeY = model.getFieldSizeY();
    }

    /**
     *
     * @return a new random Map
     */
    Map makeRandomMap(){
        fieldsizeX = model.getFieldSizeX();
        fieldsizeY = model.getFieldSizeY();
        int type = (int) (Math.random()*3) +model.getFieldSize().val;
        ArrayList<DoubleVec> coords = getCoords(type);
        Collections.shuffle(coords);
        Map map = new Map(model,fieldsizeX,fieldsizeY);
        Planet p1 = new Planet(model,coords.get(0),getMass(),40);
        map.add(p1);
        if(Math.random()>stAstLiklehood) addAsteroids(p1,map);
        Planet p2 = new Planet(model,coords.get(1),getMass(),60);
        map.add(p2);
        if(Math.random()>stAstLiklehood) addAsteroids(p2,map);
        Planet p3;
        Planet p4;
        if(type>1){
            p3 = new Planet(model,coords.get(2),getMass(),40);
            map.add(p3);
            if(Math.random()>stAstLiklehood) addAsteroids(p3,map);
        }
        if(type>3){
            p4 = new Planet(model,coords.get(3),getMass(),60);
            map.add(p4);
            if(Math.random()>stAstLiklehood) addAsteroids(p4,map);
        }
        Tank t1 = model.getPlayer1().getTank();
        map.add(t1);
        Tank t2 = model.getPlayer2().getTank();
        map.add(t2);
        t2.setOnMapCreation(p2,getAngle());
        t1.setOnMapCreation(p1,getAngle());
        return map;
    }

    private void addAsteroids(Planet p,Map map){
        int speed =(int) (Math.random()*10) + 5;
        int distance =(int) (Math.random()*30) + 50;
        int space =  360/((int)(Math.random()*30) + 9);
        for(int i = 0;i<360;i+=space){
            map.add(new StationaryAsteroid(model,new Circle(new DoubleVec(0,0),3),p,distance,i,speed));
        }
    }

    private int getMass(){return (int)(Math.random()*70000)+30000;}
    private int getAngle(){return (int)(Math.random()*360);}

    private ArrayList<DoubleVec> getCoords(int type){
        ArrayList<DoubleVec> coords = new ArrayList<>();
        switch (type){
            case 0:{ //rectangles up and down
                double x = Math.random()*(fieldsizeX-4*space) +2*space;
                double y = Math.random()*(fieldsizeY*0.5-3*space) + 2*space;
                coords.add(new DoubleVec(x,y));
                x = Math.random()*(fieldsizeX-4*space) +2*space;
                y = Math.random()*(fieldsizeY*0.5-4*space) + fieldsizeY*0.5+space;
                coords.add(new DoubleVec(x,y));
                return coords;
            }
            case 1:{ //rectangles left right
                double y = Math.random()*(fieldsizeY-4*space) + 2*space;
                double x = Math.random()*(fieldsizeX*0.5-3*space) +2*space;
                coords.add(new DoubleVec(x,y));
                y = Math.random()*(fieldsizeY-4*space) + 2*space;
                x = Math.random()*(fieldsizeX*0.5 -4*space) + fieldsizeX*0.5+space;
                coords.add(new DoubleVec(x,y));
                return coords;
            }
            case 2:{//3 rectangles left right
                double x = Math.random()*(fieldsizeX*0.3-3*space)+2*space;
                double y = Math.random()*(fieldsizeY-4*space) +2*space;
                coords.add(new DoubleVec(x,y));
                x = Math.random()*(fieldsizeX*0.3-2*space)+fieldsizeX*0.3+space;
                y = Math.random()*(fieldsizeY-4*space) +2*space;
                coords.add(new DoubleVec(x,y));
                x = Math.random()*(fieldsizeX*0.3-3*space)+fieldsizeX*0.6+space;
                y = Math.random()*(fieldsizeY-4*space) +2*space;
                coords.add(new DoubleVec(x,y));
                return coords;
            }
            case 3:{//3 rectangles up down
                double x = Math.random()*(fieldsizeX-4*space)+2*space;
                double y = Math.random()*(fieldsizeY*0.3-3*space)+2*space;
                coords.add(new DoubleVec(x,y));
                x = Math.random()*(fieldsizeX-4*space)+2*space;
                y = Math.random()*(fieldsizeY*0.3-3*space)+fieldsizeY*0.3+2*space;
                coords.add(new DoubleVec(x,y));
                x = Math.random()*(fieldsizeX-4*space)+2*space;
                y = Math.random()*(fieldsizeY*0.3 - space)+fieldsizeY*0.6+space;
                coords.add(new DoubleVec(x,y));
                return coords;
            }
            case 4:{
                double x = Math.random()*(fieldsizeX*0.5-3*space)+2*space;
                double y = Math.random()*(fieldsizeY*0.5-3*space)+2*space;
                coords.add(new DoubleVec(x,y));
                x = Math.random()*(fieldsizeX*0.5-3*space)+fieldsizeX*0.5+space;
                y = Math.random()*(fieldsizeY*0.5-2*space)+fieldsizeY*0.5+space;
                coords.add(new DoubleVec(x,y));
                x = Math.random()*(fieldsizeX*0.5-3*space)+2*space;
                y = Math.random()*(fieldsizeY*0.5-2*space)+fieldsizeY*0.5+space;
                coords.add(new DoubleVec(x,y));
                x = Math.random()*(fieldsizeX*0.5-3*space)+fieldsizeX*0.5+space;
                y = Math.random()*(fieldsizeY*0.5-3*space)+2*space;
                coords.add(new DoubleVec(x,y));
                return coords;
            }
        }
        return coords;
    }

    /**
     * Demo Map for testing or presentation
     * @return demo map
     */
    public Map makeDemoMap(){
        Map map = new Map(model,model.getFieldSizeX(),model.getFieldSizeY());
        Planet p1 = new Planet(model,new DoubleVec(238.7,549.9),91712,40,4);
        Planet p2 = new Planet(model,new DoubleVec(889,322),78475,60,7);
        Planet p3 = new Planet(model,new DoubleVec(552.8,513.9),82420,40,8);
        for(int i = 0;i<360;i+=30){
            map.add(new StationaryAsteroid(model,new Circle(new DoubleVec(0,0),3),p1,50,i,10));
        }
        for(int i = 0;i<360;i+=10){
            map.add(new StationaryAsteroid(model,new Circle(new DoubleVec(0,0),3),p3,20,i,20));
        }
        map.add(new Asteroid(model,new DoubleVec(50,50),DoubleVec.NULL,25));
        map.add(new Asteroid(model,new DoubleVec(60,50),DoubleVec.NULL,25));
        map.add(new Asteroid(model,new DoubleVec(50,60),DoubleVec.NULL,25));
        map.add(new Asteroid(model,new DoubleVec(60,60),DoubleVec.NULL,25));
        map.add(p1);
        map.add(p2);
        map.add(p3);
        Tank t1 = model.getPlayer1().getTank();
        t1.setOnMapCreation(p1,180);
        map.add(t1);
        Tank t2 = model.getPlayer2().getTank();
        t2.setOnMapCreation(p2,0);
        map.add(t2);
        return map;
    }
}
