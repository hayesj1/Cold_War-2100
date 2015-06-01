package util;

import map.populationHub.PopulationHub;
import player.Player;

import java.util.function.Predicate;

/**
 * Created by hayesj3 on 5/3/2015.
 *
 * Holds useful predicates for functions
 */
public final class Predicates {
    private static Predicates instance;
    private Predicates() {}

    public Predicate<PopulationHub> popHubsOwnedByPlayer(Player player) { return ph -> ph.getOwner().equals(player); }
    public Predicate<PopulationHub> ruinedPopHubs() { return ph -> ph.getRuined(); }

    public static Predicates getInstance() {
        if (instance == null) {
            instance = new Predicates();
        }
        return instance;
    }
}
