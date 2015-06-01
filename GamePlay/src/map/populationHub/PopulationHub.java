package map.populationHub;

import controller.Controller;
import gui.EnterAString;
import gui.EnterAStringTypes;
import player.Player;
import resource.Resources;
import weapon.missile.baseMissile.IMissile;
import weapon.missile.baseMissile.Missile;
import weapon.missile.interceptionMissile.InterceptMissile;
import weapon.missile.strikeMissile.BioMissile;
import weapon.missile.strikeMissile.ConcentrationMissile;
import weapon.missile.strikeMissile.NuclearMissile;

import javax.swing.*;
import javax.vecmath.Point2d;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by hayesj3 on 4/16/2015.
 * Class to represent settlements of various sizes. Possible Sizes are defined in @see {@link EnumPopHubSizes}.
 */

public final class PopulationHub implements IPopulationHub, Serializable {

    private static TreeMap<Player, ArrayList<PopulationHub>> allPopHubsByPlayer;
    private static ArrayList<PopulationHub> allPopHubs;

    private ArrayList<IMissile> missilesBasedHere;
    private EnumPopHubSizes size = null;
    private EnumPopHubSizes nextSize = null;

    private int pop = 0;
    private double missileProductionPerTurn = 0.0;
    private double missileProduction = 0.0;
    private double nationalPride = 0.0;
    private double fearAmount = 0.0;

    private String cityName = "";
    private Player owner = null;
    private Point2d pos = null;
    private boolean ruined = false;

    public PopulationHub(Player founder) { this(founder, 0, 0); }
    public PopulationHub(Player founder, double x, double y) { this(founder, new Point2d(x, y)); }
    public PopulationHub(Player founder, Point2d pos) {
        this.owner = founder;
        this.pos = pos;
        this.fearAmount = this.pop * IPopulationHub.fearConstant;
        if (this.owner.getCapital() == null) {
            this.size = EnumPopHubSizes.town;
            this.cityName = (new EnterAString(EnterAStringTypes.CAPITAL, this.owner)).getText();
        } else {
            this.size = EnumPopHubSizes.hamlet;
            this.cityName = (new EnterAString(EnterAStringTypes.CITY, this.owner)).getText();
        }
        this.nextSize = EnumPopHubSizes.values()[this.size.ordinal() + 1];
        this.pop = this.size.getMinPop();
        this.missileProductionPerTurn = this.size.getMissileProdPerTurn();
        System.out.println(this.cityName + " is of size " + this.size);
        getAllPopHubs().add(this);
        this.owner.getOwnedCities().add(this);
    }
    public void processTurn(Player p) {
        this.populationChange(Math.toIntExact(Math.round(this.pop * IPopulationHub.popGrowthConstant)));
        this.fearChange(this.pop * IPopulationHub.fearConstant);
        this.nationalisticChange(this.pop * IPopulationHub.nationalismBonus * getAllPopHubsByPlayer(p).size());
        this.produce();
    }
    public void nationalisticChange(double change) { this.nationalPride += change; }
    public void fearChange(double change) { this.fearAmount += change; }
    public void populationChange(int change) {
        this.pop += change;
        if (this.pop <= 0) {
            this.pop = 0;
            System.out.println(this + " has been destroyed!");
            this.ruined = true;
        } else if (this.size.compareTo(EnumPopHubSizes.metropolis) < 0 && this.pop >= this.nextSize.getMinPop()) {
            this.size = this.nextSize;
            this.nextSize = (this.size.equals(EnumPopHubSizes.metropolis)) ? null : EnumPopHubSizes.values()[this.size.ordinal() + 1];
        }
    }
    private void produce() {
        if (this.missileProduction >= 1.0) {
            int temp = JOptionPane.showOptionDialog(null, "Choose a Missile:", "Missile Produced!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, Resources.iconII, Missile.MissileTypes.values(), Missile.MissileTypes.BioMissile);
            Missile.MissileTypes missileType = Missile.MissileTypes.values()[(temp == -1) ? 0 : temp];
            this.missileProduction--;
            switch(missileType) {
                case BioMissile:
                    BioMissile bioMissile = new BioMissile(this);
                    missilesBasedHere.add(bioMissile);
                    bioMissile.storeInDataStructures();
                    break;
                case ConcentrationMissile:
                    ConcentrationMissile concenMissile = new ConcentrationMissile(this);
                    missilesBasedHere.add(concenMissile);
                    concenMissile.storeInDataStructures();
                    break;
                case NuclearMissile:
                    NuclearMissile nuclearMissile = new NuclearMissile(this);
                    missilesBasedHere.add(nuclearMissile);
                    nuclearMissile.storeInDataStructures();
                    break;
                case InterceptionMissile:
                    InterceptMissile interceptMissile = new InterceptMissile(this);
                    missilesBasedHere.add(interceptMissile);
                    interceptMissile.storeInDataStructures();
                    break;
            }
        }
        this.missileProduction += this.missileProductionPerTurn;
    }

    public void targetedByMissile(Missile m) { this.fearChange(m.getFearEffect()); }

    public Point2d getpos() { return this.pos; }
    public Player getOwner() { return this.owner; }
    public boolean getRuined() { return this.ruined; }
    /**
     * NOTE: the returned arraylist is a reference to {@link Player#ownedCities} for the specific player p.
     * Therefore, Player#ownedCities must be non-null at the time of this function call
     * @param p the player whose missiles are to be gotten
     * @return null if p is null; otherwise the arrayList of PopHubs owned by p
     */
    public static ArrayList<PopulationHub> getAllPopHubsByPlayer(Player p) {
        if (allPopHubsByPlayer == null) {
            allPopHubsByPlayer = new TreeMap<>();
            for (Player player : Controller.getPlayers()) allPopHubsByPlayer.put(player, player.getOwnedCities());
        }
        return ((p == null) ? null : allPopHubsByPlayer.get(p));
    }
    public ArrayList<IMissile> getMissilesBasedHere() {
        if (this.missilesBasedHere == null) {
            this.missilesBasedHere = new ArrayList<>();
            for (IMissile m : Missile.getAllMissilesByPlayer(this.owner)) {
                if (m == null) { break; }
                else if (m.getHomeBase().equals(this)) { this.missilesBasedHere.add(m); }
                else { continue; }
            }
        }
        return missilesBasedHere;
    }
    public static ArrayList<PopulationHub> getAllPopHubs() {
        if (allPopHubs == null) { allPopHubs = new ArrayList<>(Controller.getNumPlayers()); }
        return allPopHubs;
    }

    @Override
    public String toString() {
        return (cityName);
    }
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof PopulationHub))return false;
        PopulationHub otherPh = (PopulationHub) other;
        return (this.cityName.equals(otherPh.cityName) && this.equalStats(otherPh));
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
