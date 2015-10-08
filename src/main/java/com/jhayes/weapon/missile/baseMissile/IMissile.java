package main.java.com.jhayes.weapon.missile.baseMissile;

import main.java.com.jhayes.map.populationHub.PopulationHub;
import main.java.com.jhayes.weapon.missile.baseMissile.Missile.MissileID;
import main.java.com.jhayes.weapon.missile.interceptionMissile.InterceptMissile;

public interface IMissile {

    MissileID getID();
    String getIDasString();
    MissileTiers getTier();
    double getMaxRange();
    double getRangePerTurn();
    double getBlastRadius();
    double getFearEffect();
    boolean isLaunched();
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

