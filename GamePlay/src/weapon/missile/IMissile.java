package weapon.missile;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
import map.populationHub.PopulationHub;
import weapon.missile.Missile.MissileID;

public interface IMissile {

    MissileID getID();
    double getMaxRange();
    double getRangePerTurn();
    double getBlastRadius();
    double getFearEffect();

    Vector2d launch(PopulationHub var1);
    Point2d travel();
    PopulationHub move(PopulationHub var1);

    void strike();
    void intercepted(IMissile var1);
}

