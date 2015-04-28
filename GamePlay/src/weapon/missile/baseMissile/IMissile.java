package weapon.missile.baseMissile;

import map.populationHub.PopulationHub;
import weapon.missile.baseMissile.Missile.MissileID;

public interface IMissile {

    MissileID getID();
    double getMaxRange();
    double getRangePerTurn();
    double getBlastRadius();
    double getFearEffect();
    PopulationHub getHomeBase();
    PopulationHub getTarget();

    int launch(PopulationHub target);
    int travel();
    PopulationHub move(PopulationHub newBase);

    /** applies the effect of the misisle */
    void applyEffect();
    /** called when missile has struck it's target; missile should be removed from any data structures keeping track of all missiles; call applyEffect()*/
    void strike();
    /** called when missile has been intercepted by another missile; both missiles should be removed from any data structures keeping track of all missiles;
     * call applyEffect() on both missiles*/
    void intercepted(InterceptMissile inteceptor);
}

