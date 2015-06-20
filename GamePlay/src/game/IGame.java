package game;

/**
 * Interface to for creating the different game types; there is an enum with default ones
 *
 * Created by hayesj3 on 6/20/2015.
 */
public interface IGame {

    enum GameTypes {
        STANDARD("Standard","Regular, basic game without predefined player limits; best way to learn Cold War-2100",
                -1, -1),
        SHOW_DOWN("Show Down","A 1-vs-1 game type with a moderate starting missile stockpile; quickest way into the action, but harder than Standard",
                2, 2),
        LAST_STAND("Last Stand","The quintessential 1-vs-many game type where one player is all alone against many." +
                " With both sides starting with huge missile stockpiles, can you survive and and then counter the onslaught? Only if you setup your defenses well!",
                3, 6),
        FINEST_HOUR("Finest Hour","If you conquer this game type, your name will go down in history as the greatest commander ever to grace the Earth!" +
                " Starting with limited missiles and population, you must repel, then conquer your foe(s), who start with a large stockpile of missiles," +
                " to achieve victory. Playable as 1-vs-1 or 1-vs-2.",
                2, 3);

        private String typeName;
        private String description;
        private int minPlayers = 0;
        private int maxPlayers = 0;

        public String getTypeName() { return this.typeName; }
        public String getDescription() { return this.description; }
        public int getMinPlayers() { return this.minPlayers; }
        public int getMaxPlayers() { return this.maxPlayers; }

        GameTypes(String typeName, String description, int minPlayers, int maxPlayers) {
            this.typeName = typeName;
            this.description = description;
            this.minPlayers = minPlayers;
            this.maxPlayers = maxPlayers;
        }
    }
    /**
     * Initializes all server-side dataStructures
     */
    void initDS();
    /**
     * Initializes the players for a game:
     * create player
     * store it in the main player DataStructure
     * @see player.Player
     */
    void initPlayers();
    /**
     * Main game eventloop: sends events to clients and read events received from the clients; all necessary events are contained in {@link net.GEP.EnumEvents}
     * @see net.GEP.EnumEvents
     */
    void eventLoop();
    /**
     * this method calls all <code>init*()</code> methods and then {@link IGame#eventLoop()}
     */
    default void startGame() { initPlayers(); eventLoop(); }

}
