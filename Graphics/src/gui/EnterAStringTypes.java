package gui;

import player.EnumEmpires;
import player.Player;

/**
 * Created by hayesj3 on 5/7/2015.
 */
public enum EnterAStringTypes {
    PLAYER("Enter a name:", "Player Creation",
            "Mike McMoneybags", "Vladimir The Uniter", "Edgar von Munich", "Abbas The Great", "George Chen-Yamamoto" , "Jacob The Frozen Hearted"),
    CAPITAL("Name your Capital:", "Capital Founded!",
            "Washington D.C." , "Moscow" , "Berlin" , "Pretoria" , "Sydney", "James Cook City"),
    CITY("Name your city:", "New City Founded!",
            "Boston" , "Saint Petersburg" , "London" , "Capetown" , "Tokyo" , "Mt. Tyree Camp"),

    NUM_PLAYERS("Enter the number of players:", "How many players?", "2"),
    ENTER_COORDS("Enter some Coordinates in the format x,y", "Enter Coordinates", "0,0");

    private String message = null;
    private String title = null;
    private String[] defaultText = null;

    public String getMessage() { return this.message; }
    public String getTitle() { return this.title; }
    public String getDefaultTextForPlayer(Player player) {
        if (player == null) {
            return this.defaultText[0];
        } else {
            return this.getDefaultTextForEmpire(player.getAllegiance());
        }
    }
    private String getDefaultTextForEmpire(EnumEmpires empire) { return this.defaultText[empire.ordinal()]; }

    EnterAStringTypes(String message, String title, String... defaultTextOptions) {
        this.message = message;
        this.title = title;
        this.defaultText = defaultTextOptions;
    }

}
