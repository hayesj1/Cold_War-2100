package controller;

import gui.EnterAString;
import gui.EnterAStringTypes;
import gui.HUD;
import map.populationHub.PopulationHub;
import net.GEP;
import net.server.ServerThread;
import player.Player;
import weapon.missile.baseMissile.Missile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public final class Controller implements Runnable {

	private static Controller instance;
	private static Turn turnInstance;
	private static ServerThread serverInstance;
	private static int numPlayers = -1;
	private static ArrayList<Player> players = null;
	private static ArrayList<Player> winners = null;

	private static TreeMap<String, File> savedGames = new TreeMap<>();
	private static TreeMap<String, Player> previousPlayerNames;
	private static File playerNamesCache;

	private Controller () { this(2); }
	private Controller (int initialPlayers) {
		numPlayers = initialPlayers;
		//initialize all data structures
		this.initDS();
	}
	private Controller initDS() {
		getPlayers();
		PopulationHub.getAllPopHubs();
		Missile.getAllMissilesByID();
		for (Player p : getPlayers()) {
			p.getOwnedCities();
			PopulationHub.getAllPopHubsByPlayer(p);
			Missile.getAllMissilesByPlayer(p);
		}
		System.out.println("Data Structures initialized!");
		return this;
	}
	private Controller initPlayers() {
		int counter = 0;
		for (Player p : getPlayers()) {
			counter++;
			p.setCapital(new PopulationHub(p).addToDS());
			//PopulationHub.getAllPopHubsByPlayer(p).add(p.getCapital());
			p.getCapital().getMissilesBasedHere();
			p.setMainGUI(new HUD(p));
		}
		return this;
	}
	public Controller startGame(ServerThread server) {
		this.initPlayers();
		do {} while (!Controller.getTurnInstance().doTurn(server));
		Controller.winners = new ArrayList<>();
		winners.add(getPlayers().get(0));
		return this;
	}
	@Override
	public void run() {
		this.initPlayers();
		while(getPlayers().size() > 1) {
			for (Player p : getPlayers()) {
				boolean continueTurn = true;
				while (continueTurn) {
					try {
						String data = GEP.readEvent(serverInstance.getIn());
						String[] parts = data.split("|");
						GEP.EnumEvents event = GEP.EnumEvents.valueOf(parts[0]);
						String title = parts[1];
						String message = parts[2];
						switch(event) {
							case END_TURN:
								continueTurn = false;
								break;
							case ATTACKED:
								//onAttack();
								break;
							case FOUNDED:
								//onFound();
								PopulationHub popHub = new PopulationHub(getServerInstance().getObjectIn());
								popHub.addToDS();
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {}
				}
			}
		}
		Controller.winners = new ArrayList<>();
		winners.add(getPlayers().get(0));
	}
	/**
	 * Gets the next player id/free-index in the array players
	 * <br>
	 * if a players loses, his/her id is <b>not</b> reused in the current game
	 * @return the next free space in the array of players
	 */
	public static ServerThread getServerInstance() { return serverInstance; }
	public static int getNextPlayerID() { return getPlayers().size(); }
	public static int getNumPlayers() { return numPlayers; }
	public static ArrayList<Player> getWinners() { return Controller.winners; }
	public static ArrayList<Player> getPlayers () {
		if (Controller.players == null) {
			players = new ArrayList<>(numPlayers);
			for (int i = 0; i < numPlayers; i++) {
				players.add(new Player());
			}
		}
		return players;
	}
	public static Controller getInstance() {
		if (instance == null) {
			Integer numPlayers = Integer.valueOf(new EnterAString(EnterAStringTypes.NUM_PLAYERS, null).getText());
			instance = new Controller(numPlayers);
		}
		return instance;
	}
	public static Turn getTurnInstance() {
		if (turnInstance == null) {
			turnInstance = Turn.getInstance();
		}
		return turnInstance;
	}
	public void setServerInstance(ServerThread serverThread) {
		Controller.serverInstance = serverThread;
	}
}
