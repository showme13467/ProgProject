package pp.spacetanks.model.item;

/**
 * The FuelTank of a tank.
 */
public class FuelTank {
    public final int maxCapacity;
    private double remainingFuel;

    /**
     *  used to create a used FuelTank
     * @param maxCapacity       maximum health of the component.
            */
    public FuelTank(int maxCapacity,double remainingFuel){
        this.maxCapacity = maxCapacity;
        this.remainingFuel = Math.min(remainingFuel,maxCapacity);
    }

    /**
     *  used to create a new FuelTank
     * @param maxCapacity       maximum health of the component.
     */
    public FuelTank(int maxCapacity){
        this.maxCapacity = maxCapacity;
        this.remainingFuel = maxCapacity;
    }

    /**
     * refuels the tank
     */
    public void refuel(){
        remainingFuel = maxCapacity;}

    /**
     *
     * @return  missing fuel
     */
    public double fuelNeeded(){return maxCapacity - remainingFuel;}

    /**
     *
     * @param amount  amount to be consumed
     */
    public void consumeFuel(double amount){
        if(remainingFuel -amount>0) remainingFuel -= amount;
        else{ remainingFuel = 0; }
    }

    /**
     * @return  the remaining fuel
     */
    public double getRemainingFuel(){
        return remainingFuel;
    }

    /**
     * @return  whether the FuelTank is empty
     */
    public boolean isEmpty(){return remainingFuel ==0;}


}
