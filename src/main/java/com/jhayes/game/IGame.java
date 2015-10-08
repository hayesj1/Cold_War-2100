package main.java.com.jhayes.game;

import main.java.com.jhayes.net.GEP;

/**
 * Interface for creating a main.java.com.jhayes.game; the enum with default modes is {@link main.java.com.jhayes.game.GameMode.DefaultGameModes}, and all other main.java.com.jhayes.game modes must extend {@link main.java.com.jhayes.game.GameMode}
 *
 * @see main.java.com.jhayes.game.GameMode
 * @see main.java.com.jhayes.game.GameMode.DefaultGameModes
 * Created by hayesj3 on 6/20/2015.
 */
public interface IGame {
    /**
     * sets the gamemode for the current main.java.com.jhayes.game
     * @param mode the main.java.com.jhayes.game mode to use
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
     * Initializes the players for a main.java.com.jhayes.game:
     * create main.java.com.jhayes.player
     * store it in the main main.java.com.jhayes.player DataStructure
     *
     * @return the instance of the current Game; allows for method chaining
     * @see main.java.com.jhayes.player.Player
     */
    IGame initPlayers();
    /**
     * Main main.java.com.jhayes.game eventloop: sends events to clients and read events received from the clients; all necessary events are contained in {@link GEP.EnumEvents
     *
     * @return the current main.java.com.jhayes.game Status via a GameStatus object
     * @see GEP.EnumEvents
     */
    GameStatus eventLoop();
    /**
     * this method calls all <code>init*()</code> methods and then {@link IGame#eventLoop()}
     */
    default void startGame() { initPlayers(); eventLoop(); }

}
