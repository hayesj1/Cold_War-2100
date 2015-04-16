package map.populationHub;

import player.Player;
import map.populationHub.EnumPopulationHub;
import map.populationHub.IPopulationHub;
import weapon.missile.Missile;

import javax.vecmath.Point2d;

/**
 * Created by hayesj3 on 4/16/2015.
 */

public final class PopulationHub implements IPopulationHub {
    private EnumPopulationHub size = null;
    private int pop = 0;
    private double missileProductionPerTurn = 0.0;
    private double missileProduction = 0.0;
    private double nationalPride = 0.0;
    private double fearAmount;
    private Player owner;
    private Point2d pos;

    public PopulationHub(Player founder, Point2d pos) {
        this.fearAmount = 1.125 * (double)this.pop;
        this.owner = null;
        this.pos = null;
        this.owner = founder;
        this.size = EnumPopulationHub.hamlet;
        this.pop = this.size.getMinPop();
        this.missileProductionPerTurn = this.size.getMissileProdPerTurn();
        this.fearAmount = 0.0;
        this.nationalPride = 0.0;
        this.pos = pos;
    }
    @Override
    public void produce () {
        this.missileProduction += this.missileProductionPerTurn;

    }
    public double targettedByMissle(Missile m) {
        return this.fearChange(m.getFearEffect());
    }

    public double nationalisticShift(double shift) {
        return this.nationalPride += (double)this.pop * 1.25 == this.nationalPride?shift:shift + (double)this.pop * 1.25;
    }
    public double fearChange(double change) {
        return this.fearAmount += change;
    }
    public int populationChange(int change) {
        this.pop += change;
        if(this.size.compareTo(EnumPopulationHub.metropolis) != 0 && this.pop >= EnumPopulationHub.values()[this.size.ordinal() + 1].getMinPop()) {
            ;
        }

        this.size = EnumPopulationHub.values()[this.size.ordinal() + 1];
        return this.pop;
    }

    public Point2d getpos() {
        return this.pos;
    }
}
