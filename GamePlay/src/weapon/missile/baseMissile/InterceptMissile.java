package weapon.missile.baseMissile;

import map.populationHub.PopulationHub;

import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;

/**
 * Created by hayesj3 on 4/24/2015.
 */
public abstract class InterceptMissile extends Missile {

    Integer turnsToIntercept = null;
    protected InterceptMissile(PopulationHub homeBase, MissileTypes type) {
        super(homeBase, type);
    }

    protected int intercept(Missile target) {
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
        }
        while (tempInterPath.epsilonEquals(tempPath, this.blastRadius));
        this.path = tempInterPath;
        double rangeX = this.rangePerTurn * Math.cos(this.path.angle(this.path));
        double rangeY = this.rangePerTurn * Math.sin(this.path.angle(this.path));
        this.segment = new Vector2d(rangeX, rangeY);
        return (this.turnsToIntercept += i);
    }
}
