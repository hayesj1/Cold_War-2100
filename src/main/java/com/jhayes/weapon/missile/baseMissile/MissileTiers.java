package main.java.com.jhayes.weapon.missile.baseMissile;

/**
 * Created by hayesj3 on 5/19/2015.
 */
public enum MissileTiers{
    low(10, 1, 25.0, 1.5F, 3.0F, 0.25F, EnumPayloadTiers.mild),
    std(15, 3, 50.0, 3.0F, 5.0F, 1.0F, EnumPayloadTiers.medium),
    high(25, 6, 100.0, 6.0F, 7.5F, 1.25F, EnumPayloadTiers.deadly),
    elite(Integer.MAX_VALUE, 50, 250.0, 7.5F, 15.0F, 1.5F, EnumPayloadTiers.deadly),
    intercept(15, 10, 5.0, 2.5F, 0.0F, 5.0F, EnumPayloadTiers.interceptLoad);

    private int maxRange;
    private int rangePerTurn;
    private double fearEffect;
    private float blastRadius;
    private float effectRange;
    private float density;
    private EnumPayloadTiers payload;

    public int getMaxRange() { return maxRange; }
    public int getRangePerTurn() { return rangePerTurn; }
    public double getFearEffect() { return fearEffect; }
    public float getBlastRadius() { return blastRadius; }
    /** @return The range of the effect on the terrain */
    public double getRange() { return effectRange; }
    /** @return The density/concentration-level of the effect */
    public double getDensity() { return density; }
    /** @return The payload Tier */
    public EnumPayloadTiers getPayload() { return payload; }

    MissileTiers(int maxRange, int rangePerTurn, double fearEffect, float blastRadius, float effectRange, float density, EnumPayloadTiers payload) {
        this.maxRange = maxRange;
        this.rangePerTurn = rangePerTurn;
        this.fearEffect = fearEffect;
        this.blastRadius = blastRadius;
        this.effectRange = effectRange;
        this.density = density;
        this.payload = payload;
    }
}
