package map.populationHub;

import controller.Controller;
import player.Player;
import weapon.missile.Missile;

import javax.vecmath.Point2d;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by hayesj3 on 4/16/2015.
 */

public final class PopulationHub implements IPopulationHub {

    private static TreeMap<Player, ArrayList<PopulationHub>> allPopHubs;

    private EnumPopulationHub size = null;
    private EnumPopulationHub nextSize = null;
    private int pop = 0;
    private double missileProductionPerTurn = 0.0;
    private double missileProduction = 0.0;
    private double nationalPride = 0.0;
    private double fearAmount = 0.0;

    private String cityName = "";
    private Player owner = null;
    private Point2d pos = null;

    public PopulationHub(Player founder, Point2d pos) {
        this.fearAmount = 1.125 * (double)this.pop;
        this.cityName = "Boberto";
        this.owner = founder;
        this.pos = pos;

        if (founder.getCapital() == null) {
            this.size = EnumPopulationHub.town;
        } else {
            this.size = EnumPopulationHub.hamlet;
        }
        this.nextSize = EnumPopulationHub.values()[this.size.ordinal() + 1];
        System.out.println(this.cityName + " is of size " + this.size);
        this.pop = this.size.getMinPop();
        this.missileProductionPerTurn = this.size.getMissileProdPerTurn();
        getPlayersPopHubs(founder).add(this);
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
        if(this.size.compareTo(EnumPopulationHub.metropolis) < 0 && this.pop >= this.nextSize.getMinPop()) {
            this.size = this.nextSize;
            this.nextSize = EnumPopulationHub.values()[this.size.ordinal() + 1];}
        return this.pop;
    }

    public Point2d getpos() { return this.pos; }
    public Player getOwner() { return this.owner; }
    public static ArrayList<PopulationHub> getPlayersPopHubs (Player p) {
        if (allPopHubs == null) {
            allPopHubs = new TreeMap<Player, ArrayList<PopulationHub>>();
            for (Player player : Controller.getPlayers()) {
                if (player == null) { break; }
                allPopHubs.put(player, new ArrayList<>(2));
            }
        }
        return allPopHubs.get(p);
    }

    @Override
    public String toString() {
        return (cityName);
    }
}
