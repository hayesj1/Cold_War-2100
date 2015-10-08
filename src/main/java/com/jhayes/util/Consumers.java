package main.java.com.jhayes.util;

import main.java.com.jhayes.map.populationHub.PopulationHub;

import java.util.function.Consumer;

/**
 * Created by hayesj3 on 5/6/2015.
 *
 * Holds useful Consumers for functions such as forEach(Consumer)
 */
public final class Consumers {
    private static Consumers instance = null;
    private Consumers() {}

    public static Consumer<PopulationHub> missilesInPopHub(PopulationHub ph) { return missiles -> { ph.getMissilesBasedHere(); }; }
    public static Consumers getInstance() {
        if (instance == null) {
            instance = new Consumers();
        }
        return instance;
    }

}
