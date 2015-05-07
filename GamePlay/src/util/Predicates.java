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

    public Predicate<PopulationHub> popHubsOwnedByAnyoneBut(Player player) { return ph -> !ph.getOwner().equals(player); }

    public static Predicates getInstance() {
        if (instance == null) {
            instance = new Predicates();
        }
        return instance;
    }
}
