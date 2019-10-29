package pp.spacetanks.model.item;

import pp.spacetanks.util.DoubleVec;

/**
 * Enumeration representing the different tanks with all attributes.
 */
public enum TankType {
    M55(100,100,50,25,1200,100,220,"M55",new DoubleVec(-50,-200),new DoubleVec(0,-70.5),new DoubleVec(0,-200),new DoubleVec(-300,-340),747,252,440,44,637,141,9,107){//todo mult by scale

    },
    MARS(200,200,100,50,800,500,580,"Mars",new DoubleVec(0,-200),new DoubleVec(0,-70),new DoubleVec(-300,-135),new DoubleVec(70,-340),747,252,450,180,637,141,9,107){

    },
    PANZERHAUBITZE2000(300,300,150,75,600,2500,2560,"Panzerhaubitze 2000",new DoubleVec(0,-200),new DoubleVec(0,-80),new DoubleVec(0,-200),new DoubleVec(-350,-350),747,252,550,80,637,141,9,107){

    };

    /**
     * Creates a new TankType
     * @param healthChassis maximum Chassis health
     * @param healthCannon  maximum Cannon health
     * @param healthChain   maximum Chain health
     * @param healthAntenna maximum Antenna health
     * @param fuelCapacity  maximum fuel capacity
     * @param price         price of the tank
     * @param priceWithFuel price of the tank with fuel
     * @param name          name of the tank
     * @param relPosChassis relative position to the tank position where the chassis has to be located
     * @param relPosChain   relative position to the tank position where the chain has to be located
     * @param relPosCannon  relative position to the tank position where the cannon has to be located
     * @param relPosAntenna relative position to the tank position where the antenna has to be located
     * @param chassisWidth  width of the chassis
     * @param chassisHeight height of the chassis
     * @param cannonWidth   width of the cannon
     * @param cannonHeight  height of the cannon
     * @param chainWidth    width of the chain
     * @param chainHeight   height of the chain
     * @param antennaWidth  width of the antenna
     * @param antennaHeight height of the antenna
     */
    TankType(int healthChassis, int healthCannon, int healthChain, int healthAntenna, int fuelCapacity, int price,int priceWithFuel,
             String name,DoubleVec relPosChassis, DoubleVec relPosChain, DoubleVec relPosCannon, DoubleVec relPosAntenna,
             int chassisWidth,int chassisHeight,int cannonWidth,int cannonHeight,int chainWidth, int chainHeight,int antennaWidth,int antennaHeight){
        this.fuelCapacity = fuelCapacity;
        this.healthAntenna = healthAntenna;
        this.healthCannon = healthCannon;
        this.healthChain = healthChain;
        this.healthChassi = healthChassis;
        this.price = price;
        this.priceWithFuel = priceWithFuel;
        this.name = name;
        this.relPosChassi = relPosChassis.mult(scale);
        this.relPosChain = relPosChain.mult(scale);
        this.relPosCannon = relPosCannon.mult(scale);
        this.relPosAntenna = relPosAntenna.mult(scale);
        this.chassiWidth = chassisWidth*scale;
        this.chassiHeight = chassisHeight*scale;
        this.cannonWidth = cannonWidth*scale;
        this.cannonHeight = cannonHeight*scale;
        this.chainWidth = chainWidth*scale;
        this.chainHeight = chainHeight*scale;
        this.antennaWidth = antennaWidth*scale;
        this.antennaHeight = antennaHeight*scale;
    }
    private  final double scale = .06;
    public final int healthChain;
    public final double chainWidth;
    public final double chainHeight;
    public final int healthChassi;
    public final double chassiWidth;
    public final double chassiHeight;
    public final int healthAntenna;
    public final double antennaWidth;
    public final double antennaHeight;
    public final int healthCannon;
    public final double cannonWidth;
    public final double cannonHeight;
    public final int fuelCapacity;
    public final int price;
    public final int priceWithFuel;
    public final DoubleVec relPosChassi;
    public final DoubleVec relPosChain;
    public final DoubleVec relPosCannon;
    public final DoubleVec relPosAntenna;
    public final String name;
}
