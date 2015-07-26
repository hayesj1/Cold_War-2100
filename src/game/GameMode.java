package game;

/**
 * By extending this class, you can create your own game modes; all gamemode s should extends this class if they are not already constants in
 * {@link game.GameMode.DefaultGameModes}
 *
 * Created by hayesj3 on 6/26/2015.
 */
public abstract class GameMode {

    private String modeName;
    private String description;
    private int minPlayers = 0;
    private int maxPlayers = 0;

    public void setGameMode(GameMode mode) {
        setModeName(mode.getModeName());
        setDescription(mode.getDescription());
        setMaxPlayers(mode.getMaxPlayers());
        setMinPlayers(mode.getMinPlayers());
    }
    public void setModeName(String modeName) { this.modeName = modeName; }
    public void setDescription(String description) { this.description = description; }
    public void setMinPlayers(int minPlayers) { this.minPlayers = minPlayers; }
    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }

    public abstract String getModeName();
    public abstract String getDescription();
    public abstract int getMinPlayers();
    public abstract int getMaxPlayers();

    public enum DefaultGameModes {
        STANDARD("Standard","Regular, basic game without predefined player limits; best way to learn Cold War-2100",
                -1, -1),
        SHOW_DOWN("Show Down","A 1-vs-1 game mode with a moderate starting missile stockpile; quickest way into the action, but harder than Standard",
                2, 2),
        LAST_STAND("Last Stand","The quintessential 1-vs-many game mode where one player is all alone against many." +
                " With both sides starting with huge missile stockpiles, can you survive and and then counter the onslaught? Only if you setup your defenses well!",
                3, 6),
        FINEST_HOUR("Finest Hour","If you conquer this game mode, your name will go down in history as the greatest commander ever to grace the Earth!" +
                " Starting with limited missiles and population, you must repel, then conquer your foe(s), who start with a large stockpile of missiles," +
                " to achieve victory. Playable as 1-vs-1 or 1-vs-2.",
                2, 3);

        private String modeName;
        private String description;
        private int minPlayers = 0;
        private int maxPlayers = 0;

        public String getModeName() { return this.modeName; }
        public String getDescription() { return this.description; }
        public int getMinPlayers() { return this.minPlayers; }
        public int getMaxPlayers() { return this.maxPlayers; }

        DefaultGameModes(String modeName, String description, int minPlayers, int maxPlayers) {
            this.modeName = modeName;
            this.description = description;
            this.minPlayers = minPlayers;
            this.maxPlayers = maxPlayers;
        }
    }
}
