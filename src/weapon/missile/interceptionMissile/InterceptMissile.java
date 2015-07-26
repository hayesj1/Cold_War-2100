package weapon.missile.interceptionMissile;

import map.populationHub.PopulationHub;
import weapon.missile.baseMissile.Missile;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

/**
 * Created by hayesj3 on 4/24/2015.
 */
public class InterceptMissile extends Missile {

    Integer turnsToIntercept = null;
    public InterceptMissile(PopulationHub homeBase) {
        super(homeBase, MissileTypes.InterceptionMissile);
    }

    public int intercept(Missile target) {
        this.turnsToIntercept = super.calcRoute(target);
        Vector2d tempInterPath = null;
        Vector2d tempPath = null;
        Tuple2d temp = null;
        int i = 0;
        do {
            this.getSegment().scale(this.turnsToIntercept + i, temp);
            this.getPos().add(temp, tempInterPath);
            target.getSegment().scale(this.turnsToIntercept + i, temp);
            target.getPos().add(temp, tempPath);
            i++;
        } while (!tempInterPath.epsilonEquals(tempPath, this.getBlastRadius()));
        this.path = tempInterPath;
        double rangeX = this.getRangePerTurn() * Math.cos(this.path.angle(this.path));
        double rangeY = this.getRangePerTurn() * Math.sin(this.path.angle(this.path));
        this.segment = new Vector2d(rangeX, rangeY);
        return (this.turnsToIntercept += i);
    }

    @Override
    public void applyEffect() {}
}
