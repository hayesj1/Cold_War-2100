package map.populationHub;

import controller.Controller;
import player.Player;
import resource.Resources;
import weapon.missile.baseMissile.IMissile;
import weapon.missile.baseMissile.Missile;
import weapon.missile.interceptionMissile.BioInterMissile;
import weapon.missile.interceptionMissile.ConcentrationInterMissile;
import weapon.missile.interceptionMissile.NuclearInterMissile;
import weapon.missile.strikeMissile.BioMissile;
import weapon.missile.strikeMissile.ConcentrationMissile;
import weapon.missile.strikeMissile.NuclearMissile;

import javax.swing.*;
import javax.vecmath.Point2d;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by hayesj3 on 4/16/2015.
 */

public final class PopulationHub implements IPopulationHub {

    private static TreeMap<Player, ArrayList<PopulationHub>> allPopHubs;

    private ArrayList<IMissile> missilesBasedHere;
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
        this.cityName = (String) JOptionPane.showInputDialog(null, "Choose a name:", "PopHub Founded",
                JOptionPane.QUESTION_MESSAGE, Resources.iconII, null, null);
        this.owner = founder;
        this.pos = pos;

        if (this.owner.getCapital() == null) {
            this.size = EnumPopulationHub.town;
        } else {
            this.size = EnumPopulationHub.hamlet;
        }
        this.nextSize = EnumPopulationHub.values()[this.size.ordinal() + 1];
        System.out.println(this.cityName + " is of size " + this.size);
        this.pop = this.size.getMinPop();
        this.missileProductionPerTurn = this.size.getMissileProdPerTurn();
        getPlayersPopHubs(this.owner).add(this);
        this.owner.getOwnedCities().add(this);

    }
    @Override
    public void produce() {
        this.missileProduction += this.missileProductionPerTurn;
        if (this.missileProduction >= 1.0) {
            Missile.MissileTypes missileType = (Missile.MissileTypes) JOptionPane.showInputDialog(null, "Choose a Missile:",
                    "Missile Produced!", JOptionPane.QUESTION_MESSAGE, Resources.iconII, Missile.MissileTypes.values(), null);
            this.missileProduction--;
            switch(missileType) {
                case BioMissile:
                    missilesBasedHere.add(new BioMissile(this));
                    break;
                case ConcentrationMissile:
                    missilesBasedHere.add(new ConcentrationMissile(this));
                    break;
                case NuclearMissile:
                    missilesBasedHere.add(new NuclearMissile(this));
                    break;
                case BioInterMissile:
                    missilesBasedHere.add(new BioInterMissile(this));
                    break;
                case ConcentrationInterMissile:
                    missilesBasedHere.add(new ConcentrationInterMissile(this));
                    break;
                case NuclearInterMissile:
                    missilesBasedHere.add(new NuclearInterMissile(this));
                    break;
            }
        }
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
        if (this.size.compareTo(EnumPopulationHub.metropolis) < 0 && this.pop >= this.nextSize.getMinPop()) {
            this.size = this.nextSize;
            this.nextSize = EnumPopulationHub.values()[this.size.ordinal() + 1];
        }
        return this.pop;
    }
    public Point2d getpos() { return this.pos; }
    public Player getOwner() { return this.owner; }
    public static ArrayList<PopulationHub> getPlayersPopHubs (Player p) {
        if (allPopHubs == null) {
            allPopHubs = new TreeMap<>();
            for (Player player : Controller.getPlayers()) {
                allPopHubs.put(player, new ArrayList<>());
            }
        }
        return allPopHubs.get(p);
    }
    public ArrayList<IMissile> getMissilesBasedHere() {
        if (this.missilesBasedHere == null) {
            this.missilesBasedHere = new ArrayList<>();
            for (IMissile m : Missile.getAllMissilesByPlayer(this.owner)) {
                if (m.getHomeBase().equals(this)) { this.missilesBasedHere.add(m); }
                else { continue; }
            }
        }
        return missilesBasedHere;
    }
    @Override
    public String toString() {
        return (cityName);
    }
    public Boolean equals(PopulationHub other) {
        if (other == null) { return null; }
        return (this.cityName.equals(other.cityName) && this.equalStats(other));
    }
    /** called by @see {@link PopulationHub#equals} as a helper function; checks the following stats of a PopulationHub:
     * <br>
     * @see {@link PopulationHub#size} <br>
     * @see {@link PopulationHub#owner} <br>
     * @see {@link PopulationHub#pos} <br>
     * @see {@link PopulationHub#pop} <br>
     * @see {@link PopulationHub#fearAmount} <br>
     * @see {@link PopulationHub#nationalPride} <br>
     * <br>
     * @param other the other PopulationHub
     * @return true if other has the same stats listed above as this; false otherwise
     */
    private boolean equalStats(PopulationHub other) {
        boolean ret;
        if (!this.size.equals(other.size)) { ret = false; }
        else if (!this.owner.equals(other.owner)) { ret = false; }
        else if (!this.pos.equals(other.pos)) { ret = false; }
        else if (this.pop != other.pop) { ret = false; }
        else if (this.fearAmount != other.fearAmount) { ret = false; }
        else if (this.nationalPride != other.nationalPride) { ret = false; }
        else { ret = true; }
        return ret;
    }
}
