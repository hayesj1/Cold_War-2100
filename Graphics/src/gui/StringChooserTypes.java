package gui;

import player.EnumEmpires;
import player.Player;

/**
 * Created by hayesj3 on 5/7/2015.
 */
public enum StringChooserTypes {
    PLAYER("Enter a name:", "Player Creation",
            "Mike McMoneybags", "Vladimir The Uniter", "Edgar von Munich", "Abbas The Great", "George Chen-Yamamoto" , "Jacob The Frozen Hearted"),
    CAPITAL("Name your Capital:", "Capital Founded!",
            "Washington D.C." , "Moscow" , "Berlin" , "Pretoria" , "Sydney", "James Cook City"),
    CITY("Please name your city:", "New City Founded!",
            "Boston" , "Saint Petersburg" , "London" , "Capetown" , "Tokyo" , "Mt. Tyree Camp");

    private String message = null;
    private String title = null;
    private String[] defaultText = null;

    public String getMessage() { return this.message; }
    public String getTitle() { return this.title; }
    public String getDefaultTextForEmpire(EnumEmpires empire) { return this.defaultText[empire.ordinal()]; }
    public String getDefaultTextForPlayer(Player player) { return this.defaultText[player.getAllegiance().ordinal()]; }

    StringChooserTypes(String message, String title, String... defaultTextOptions) {
        this.message = message;
        this.title = title;
        this.defaultText = defaultTextOptions;
    }

}
