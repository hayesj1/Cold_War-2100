package weapon.missile.baseMissile;

import controller.Controller;
import map.populationHub.PopulationHub;
import player.Player;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Missile implements IMissile, Comparable<Missile> {

	private static TreeMap<MissileID, IMissile> allMissilesByID = null;
	private static TreeMap<Player, ArrayList<IMissile>> allMissilesByPlayer = null;
	private static int numMissiles = 0;

	protected MissileID id = null;

	protected double maxRange = 0.0;
	protected double rangePerTurn = 0.0;
	protected double fearEffect = -1.0;
	protected double blastRadius = 0.0;

	protected Point2d pos = null;
	protected PopulationHub homeBase = null;

	protected PopulationHub target = null;
	protected Vector2d path = null;
	protected Vector2d segment = null;
	protected Integer turnsToStrike = null;

	protected Missile(PopulationHub homeBase, IMissile missile) {
		this.homeBase = homeBase;
		this.pos = this.homeBase.getpos();
		this.id = new MissileID(MissileTypes.valueOf(missile.getClass().getName()), ++numMissiles);
		getAllMissilesByID().put(this.id, this);
		getAllMissilesByPlayer().get(homeBase.getOwner()).add(missile);
	}

	public static TreeMap<MissileID, IMissile> getAllMissilesByID() {
		if (allMissilesByID == null) {
			allMissilesByID = new TreeMap<MissileID, IMissile>();
		}
		return allMissilesByID;
	}

	public static TreeMap<Player, ArrayList<IMissile>> getAllMissilesByPlayer() {
		if (allMissilesByPlayer == null) {
		allMissilesByPlayer = new TreeMap<Player, ArrayList<IMissile>>();
			for (Player p : Controller.getPlayers())
				allMissilesByPlayer.put(p, new ArrayList<>(0));
		}
		return allMissilesByPlayer;
	}

	@Override
	public MissileID getID() { return this.id; }
	@Override
	public double getMaxRange() { return this.maxRange;}
	@Override
	public double getRangePerTurn() { return this.rangePerTurn; }
	@Override
	public double getBlastRadius() { return this.blastRadius; }
	@Override
	public double getFearEffect() { return this.fearEffect; }
	public Point2d getPos() { return this.pos; }
	public Vector2d getSegment() { return this.segment; }

	@Override
	public int launch (PopulationHub target) {
		this.target = target;
		this.target.targettedByMissle(this);
		this.turnsToStrike = this.calcRoute(this.target);
		return this.turnsToStrike;
	}
	@Override
	public int travel() {
		if (this.pos.equals(this.target.getpos())) {
			this.strike();
		}
		this.pos.add(this.segment);
		return this.turnsToStrike--;
	}
	@Override
	public PopulationHub move(PopulationHub newHomeBase) { this.homeBase = newHomeBase; this.pos = homeBase.getpos(); return this.homeBase; }

	/** deletes the current missile from all data structures holding missiles; call at the very end of any overrides */
	@Override
	public void strike() {
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
		double rangeX = this.rangePerTurn * Math.cos(this.path.angle(this.path));
		double rangeY = this.rangePerTurn * Math.sin(this.path.angle(this.path));
		this.segment = new Vector2d(rangeX, rangeY);
		return this.calcTurnsToStrike();
	}
	protected int calcRoute(Missile target) {
		this.path = new Vector2d(target.pos.getX(), target.pos.getY());
		double rangeX = this.rangePerTurn * Math.cos(this.path.angle(this.path));
		double rangeY = this.rangePerTurn * Math.sin(this.path.angle(this.path));
		this.segment = new Vector2d(rangeX, rangeY);
		return this.calcTurnsToStrike();
	}
	protected int calcTurnsToStrike() {
		this.turnsToStrike = Integer.valueOf((int) (Math.ceil(this.path.length() / this.segment.length())));
		return turnsToStrike;
	}
	@Override
	public int compareTo(Missile other) {
		int strSorting = this.id.getID().toString().compareToIgnoreCase(other.id.getID().toString());
		return ((strSorting == 0) ? this.id.getMissileNumber().compareTo(other.id.getMissileNumber()) : strSorting);
	}

	final static class MissileID {
		private MissileTypes ID = null;
		private int missileNumber = -1;

		public MissileID(String id, int number) {
			this.ID = MissileTypes.valueOf(id);
			this.missileNumber = number;
		}
		public MissileID(MissileTypes id, int number) {
			this.ID = id;
			this.missileNumber = number;
		}
		public MissileTypes getID() { return this.ID; }
		public Integer getMissileNumber() { return this.missileNumber; }
	}

	public enum MissileTypes {
		StdBMissile("Std B Missile"),
		StdNMissile("Std N Missile,"),
		StdCMissile("Std C Missile");

		private String typeName;

		private MissileTypes(String id) {
			typeName = id;
		}
		public MissileTypes parseStr(String type) {
			String[] typeSplit = type.split(" ");
			String typeNoSpaces = typeSplit.toString();
			return MissileTypes.valueOf(typeNoSpaces);
		}

		public String getTypeName() { return this.typeName; }
	}
}