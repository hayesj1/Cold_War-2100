package weapon.missile.baseMissile;

import controller.Controller;
import map.populationHub.PopulationHub;
import player.Player;
import resource.Resources;
import weapon.missile.interceptionMissile.InterceptMissile;

import javax.swing.*;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Missile implements IMissile, Comparable<Missile> {

	private static TreeMap<MissileID, IMissile> allMissilesByID = null;
	private static TreeMap<Player, ArrayList<IMissile>> allMissilesByPlayer = null;
	private static int numMissiles = 0;

	private boolean isLaunched = false;
	protected MissileID id = null;

	protected MissileTiers tier = null;

	protected Point2d pos = null;
	protected PopulationHub homeBase = null;

	protected PopulationHub target = null;
	protected Vector2d path = null;
	protected Vector2d segment = null;
	protected Integer turnsToStrike = null;

	/** Be sure to immediatly call @see {@link Missile#storeInDataStructures()} after constructing*/
	protected Missile(PopulationHub homeBase, MissileTypes type) {
		this.homeBase = homeBase;
		this.pos = this.homeBase.getpos();
		this.id = new MissileID(type, ++numMissiles);
		this.tier = (MissileTiers) JOptionPane.showInputDialog(null, "Choose the tier of your missile:", "Missile Tier",
				JOptionPane.QUESTION_MESSAGE, Resources.iconII, MissileTiers.values(), MissileTiers.std);
	}
	/** Stores the Missile in the game's data structures */
	public void storeInDataStructures() {
		getAllMissilesByID().put(this.id, this);
		getAllMissilesByPlayer(homeBase.getOwner()).add(this);
	}
	public static TreeMap<MissileID, IMissile> getAllMissilesByID() {
		if (allMissilesByID == null) {
			allMissilesByID = new TreeMap<MissileID, IMissile>();
		}
		return allMissilesByID;
	}
	/**
	 *
	 * @param player the player whose missiles are to be gotten
	 * @return null if player is null; otherwise the arrayList of missiles controller by player
	 */
	public static ArrayList<IMissile> getAllMissilesByPlayer(Player player) {
		if (allMissilesByPlayer == null) {
			allMissilesByPlayer = new TreeMap<Player, ArrayList<IMissile>>();
			for (Player p : Controller.getPlayers())
				allMissilesByPlayer.put(p, new ArrayList<>(5));
		}
		return (player == null) ? null : allMissilesByPlayer.get(player);
	}

	@Override
	public MissileID getID() { return this.id; }
	@Override
	public MissileTiers getTier() { return this.tier; }
	@Override
	public double getMaxRange() { return this.tier.getMaxRange(); }
	@Override
	public double getRangePerTurn() { return this.tier.getRangePerTurn(); }
	@Override
	public double getBlastRadius() { return this.tier.getBlastRadius(); }
	@Override
	public double getFearEffect() { return this.tier.getFearEffect(); }
	@Override
	public boolean isLaunched() { return this.isLaunched; }
	@Override
	public PopulationHub getHomeBase() { return this.homeBase; }
	@Override
	public PopulationHub getTarget() { return this.target; }
	public Point2d getPos() { return this.pos; }
	public Vector2d getSegment() { return this.segment; }

	@Override
	public int launch (PopulationHub target) {
		this.target = target;
		this.target.targetedByMissile(this);
		this.turnsToStrike = this.calcRoute(this.target);
		this.homeBase.getMissilesBasedHere().remove(this);
		this.isLaunched = true;
		return this.turnsToStrike;
	}

	/**
	 * Moves the missle toward it's target
	 * @return the turns left to strike, or -1 if it should strike() on this turns
	 */
	@Override
	public int travel() {
		if (this.pos.equals(this.target.getpos())) {
			return -1;
		}
		this.pos.add(this.segment);
		return this.turnsToStrike--;
	}
	@Override
	public PopulationHub move(PopulationHub newHomeBase) {
		this.homeBase.getMissilesBasedHere().remove(this);
		this.homeBase = newHomeBase;
		this.pos = newHomeBase.getpos();
		this.homeBase.getMissilesBasedHere().add(this);
		return newHomeBase;
	}

	/** applies fear to the target then deletes the current missile from all data structures holding missiles; call at the very end of any overrides */
	@Override
	public void strike() {
		this.target.fearChange(this.tier.getFearEffect());
		allMissilesByID.remove(this.id);
		allMissilesByPlayer.get(this.homeBase.getOwner()).remove(this);
	}

	/** deletes the current and the intercepting missiles from all data structures holding missiles; call at the very end of any overrides */
	@Override
	public void intercepted(InterceptMissile interceptor) {
		allMissilesByID.remove(this.id);
		allMissilesByPlayer.get(this.homeBase.getOwner()).remove(this);
		allMissilesByID.remove(interceptor.id);
		allMissilesByPlayer.get(this.homeBase.getOwner()).remove(interceptor);
	}

	protected int calcRoute(PopulationHub target) {
		this.path = new Vector2d(target.getpos().getX(), target.getpos().getY());
		double rangeX = this.getRangePerTurn() * Math.cos(this.path.angle(this.path));
		double rangeY = this.getRangePerTurn() * Math.sin(this.path.angle(this.path));
		this.segment = new Vector2d(rangeX, rangeY);
		return this.calcTurnsToStrike();
	}
	protected int calcRoute(Missile target) {
		this.path = new Vector2d(target.pos.getX(), target.pos.getY());
		double rangeX = this.getRangePerTurn() * Math.cos(this.path.angle(this.path));
		double rangeY = this.getRangePerTurn() * Math.sin(this.path.angle(this.path));
		this.segment = new Vector2d(rangeX, rangeY);
		return this.calcTurnsToStrike();
	}
	protected int calcTurnsToStrike() {
		this.turnsToStrike = Integer.valueOf((int) (Math.ceil(this.path.length() / this.segment.length())));
		return turnsToStrike;
	}
	@Override
	public int compareTo(Missile other) {
		int strSorting = this.id.getType().toString().compareToIgnoreCase(other.id.getType().toString());
		return ((strSorting == 0) ? this.id.getMissileNumber().compareTo(other.id.getMissileNumber()) : strSorting);
	}
	@Override
	public String toString() { return this.id.toString(); }
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Missile))return false;
		Missile otherM = (Missile)other;
		return this.id.equals(otherM.id);
	}

	final static class MissileID implements Comparable<MissileID>{
		private MissileTypes type = null;
		private int missileNumber = -1;

		public MissileID(MissileTypes id, int number) {
			this.type = id;
			this.missileNumber = number;
		}
		public MissileTypes getType() { return this.type; }
		public Integer getMissileNumber() { return this.missileNumber; }
		@Override
		public String toString() { return (this.type.toString() + " #" + this.missileNumber ); }
		@Override
		public int compareTo(MissileID other) { return Integer.compare(this.missileNumber, other.missileNumber); }
		@Override
		public boolean equals(Object other) {
			if (other == null) return false;
			if (other == this) return true;
			if (!(other instanceof MissileID)) return false;
			MissileID otherID = (MissileID)other;
			return Integer.valueOf(this.missileNumber).equals(Integer.valueOf(otherID.missileNumber));
		}
	}

	public enum MissileTypes {
		BioMissile("Bio Missile"),
		NuclearMissile("Nuclear Missile"),
		ConcentrationMissile("Concentration Missile"),
		InterceptionMissile("Interception Missile");

		private String typeName;

		MissileTypes(String name) {
			typeName = name;
		}
		public String getTypeName() { return this.typeName; }
		@Override
		public String toString() {
			return this.typeName;
		}
	}
}