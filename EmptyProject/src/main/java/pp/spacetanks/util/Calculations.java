package pp.spacetanks.util;

import pp.spacetanks.model.item.Projectiles.ProjectileType;

import java.util.ArrayList;

public class Calculations {
    /**
     * Normalizes the specified angle such the returned angle lies in the range -180 degrees
     * to 180 degrees.
     *
     * @param angle an angle in degrees
     * @return returns an angle equivalent to {@code angle} that lies in the range -180
     * degrees to 180 degrees.
     */
    public static double normalizeAngle(double angle) {
        final double res = angle % 360.;
        if (res < -180.) return res + 360.;
        else if (res > 180.) return res - 360.;
        return res;
    }

    /**
     *
     * @param ints integers to find the minimum of
     * @return minimum of ints or MAX_VALUE if ints.length == 0
     */
    public static int min(int ... ints){
        int tmp = Integer.MAX_VALUE;
        for(int i:ints)
            tmp = Math.min(tmp,i);
        return tmp;
    }

    public static int minPrice(ArrayList<ProjectileType> list){
        int tmp = Integer.MAX_VALUE;
        for(ProjectileType p:list)
            tmp = Math.min(tmp,p.price);
        return tmp;
    }
}
