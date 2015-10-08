package main.java.com.jhayes.weapon.missile.baseMissile;

/**
 * Created by hayesj3 on 5/15/2015.
 */
public enum EnumPayloadTiers {
    mild(-250),
    medium(-750),
    deadly(-1500),
    interceptLoad(0);

    private int popLoss = 0;

    EnumPayloadTiers(int popLoss) {
        this.popLoss = popLoss;
    }

    public int getPopLoss() { return popLoss; }
}
