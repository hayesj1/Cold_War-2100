package weapon.missile;

import map.populationHub.PopulationHub;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
import java.util.TreeMap;

public abstract class Missile implements IMissile, Comparable<Missile> {

	private static TreeMap<MissileID, IMissile> allMissiles = null;
	protected MissileID id = null;
	protected double maxRange = 0.0;
	protected double rangePerTurn = 0.0;
	protected double fearEffect = -1.0;
	protected double blastRadius = 0.0;
	protected Point2d pos = null;
	protected PopulationHub homeBase = null;
	protected PopulationHub target = null;
	private int numMissiles = 0;

	protected Missile(PopulationHub homeBase, IMissile missile) {
		this.homeBase = homeBase;
		this.pos = this.homeBase.getpos();
		this.id = new MissileID(MissileTypes.valueOf(missile.getClass().getName()), ++numMissiles);
		if (allMissiles == null) {
			allMissiles = new TreeMap<MissileID, IMissile>();
		}
		allMissiles.put(this.id, this);
	}

	public static TreeMap<MissileID, IMissile> getAllMissiles () { return allMissiles; }

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

	@Override
	public Vector2d launch (PopulationHub target) {
		target.targettedByMissle(this);
		return (new Vector2d(target.getpos().getX(), target.getpos().getY()));
	}
	@Override
	public Point2d travel() { pos.scale(rangePerTurn); return pos; }
	@Override
	public PopulationHub move(PopulationHub newHomeBase) { this.homeBase = newHomeBase; this.pos = homeBase.getpos(); return this.homeBase; }

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