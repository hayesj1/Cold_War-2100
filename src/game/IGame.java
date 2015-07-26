package game;

/**
 * Interface for creating a game; the enum with default modes is {@link game.GameMode.DefaultGameModes}, and all other game modes must extend {@link game.GameMode}
 *
 * @see game.GameMode
 * @see game.GameMode.DefaultGameModes
 * Created by hayesj3 on 6/20/2015.
 */
public interface IGame {
    /**
     * sets the gamemode for the current game
     * @param mode the game mode to use
     *
     * @return the instance of the current Game; allows for method chaining
     */
    IGame setGameMode(GameMode mode);
    /**
     * Initializes all server-side dataStructures
     *
     * @return the instance of the current Game; allows for method chaining
     */
    IGame initDS();
    /**
     * Initializes the players for a game:
     * create player
     * store it in the main player DataStructure
     *
     * @return the instance of the current Game; allows for method chaining
     * @see player.Player
     */
    IGame initPlayers();
    /**
     * Main game eventloop: sends events to clients and read events received from the clients; all necessary events are contained in {@link net.GEP.EnumEvents
     *
     * @return the current game Status via a GameStatus object
     * @see GEP.EnumEvents
     */
    GameStatus eventLoop();
    /**
     * this method calls all <code>init*()</code> methods and then {@link IGame#eventLoop()}
     */
    default void startGame() { initPlayers(); eventLoop(); }

}
