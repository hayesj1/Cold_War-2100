package player;

/**
 * Created by hayesj3 on 4/22/2015.
 */
public enum EnumEmpires {
    USA("Untied States of America"),
    USSR("Union of Soviet Socialist Republics"),
    EU("Europian Union"),
    PRA("People's Republic of Africa"),
    AAA("Austral-Asian Alliance"),
    GAWD("Group for Antarctica's World Domination");

    private String fullTitle = "";

    private EnumEmpires(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    @Override
    public String toString() {
        return this.fullTitle;
    }
}
