package player;

/**
 * Created by hayesj3 on 4/22/2015.
 */
public enum EnumEmpires {
    USA("Untied States of the Americas"),
    USCR("Union of Soviet Communist Republics"),
    ECAT("European Coalition Against Terrorists"),
    PRA("People's Republic of Africa and Madagascar"),
    AAA("Austral-Asian Alliance"),
    GAWD("Group for Antarctica's World Domination");

    private String fullTitle;

    EnumEmpires(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String toDisplayString() {
        return this.fullTitle;
    }
}
