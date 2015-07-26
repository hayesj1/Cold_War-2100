package game;

import gui.EnterAString;
import gui.EnterAStringTypes;
import map.populationHub.PopulationHub;
import net.GEP;
import net.server.ServerThread;
import player.Player;
import util.Predicates;
import weapon.missile.baseMissile.IMissile;
import weapon.missile.baseMissile.Missile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public final class ColdWar2100 implements Runnable {

	private static ColdWar2100 instance;
	private static Turn turnInstance;
	private static ServerThread serverInstance;
	private static int numPlayers = -1;
	private static ArrayList<Player> players = null;
	private static ArrayList<Player> winners = null;

	private static TreeMap<String, File> savedGames = new TreeMap<>();
	private static ArrayList<String> previoususers;
	private static File playerNamesCache;

	public ColdWar2100(ServerThread serverThread) {
		serverInstance = serverThread;
		numPlayers = serverInstance.getConnections().size();
		//initialize all data structures
		instance = this.initDS();
	}
	private ColdWar2100(int numPlayers) {
		this.numPlayers = numPlayers;
		//initialize all data structures
		this.initDS();
	}
	private ColdWar2100 initDS() {
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
	private ColdWar2100 initPlayers() {
		int counter = 0;
		for (Player p : getPlayers()) {
			counter++;
			//p.setCapital(new PopulationHub(p).addToDS());
			//PopulationHub.getAllPopHubsByPlayer(p).add(p.getCapital());
			p.getCapital().getMissilesBasedHere();
			//p.setMainGUI(new HUD(p));
		}
		return this;
	}
	public ColdWar2100 startGame(ServerThread server) {
		this.initPlayers();
		//do {} while (!ColdWar2100.getTurnInstance().doTurn(server));
		ColdWar2100.winners = new ArrayList<>();
		winners.add(getPlayers().get(0));
		return this;
	}
	@Override
	public void run() {
		//this.initPlayers();
		players.addAll(serverInstance.getPlayers());
		ArrayList<ServerThread.Connection> fails = (ArrayList<ServerThread.Connection>) serverInstance.broadcastEvent(GEP.EnumEvents.WELCOME);
		do {
			for (Player p : getPlayers()) {
				boolean continueTurn = true;
				while (continueTurn) {
					try {
						serverInstance.broadcastEvent(GEP.EnumEvents.WELCOME);
						String data = GEP.readEvent(serverInstance.getConnection(p.getID()).getIn());
						if (!data.matches("\\*|\\*|\\*")) {
							switch(data) {
								case "pophubs":
									serverInstance.getConnection(p.getID()).getPlayersPophubNames();
									break;
								case "missiles":
									serverInstance.getConnection(p.getID()).getPlayersMissileIDs();
									break;
								case "allegiance":
									serverInstance.getConnection(p.getID()).playerAllegiance();
									break;
								default:
									System.out.println("invalid message sent! " + data);
									break;
							}
						}
						String[] parts = data.split("|");
						GEP.EnumEvents event = GEP.EnumEvents.valueOf(parts[0]);
						String title = parts[1];
						String message = parts[2];


						String coords = null;
						String name = null;
						String missileID = null;
						String targetName = null;
						String newHomebaseName = null;

						IMissile missile = null;
						PopulationHub target = null;
						PopulationHub newHomebase = null;
						switch(event) {
							case FOUNDED:
								coords = serverInstance.getConnection(p.getID()).getIn().readLine();
								name = serverInstance.getConnection(p.getID()).getIn().readLine();
								String[] temp = coords.split(",");

								PopulationHub popHub = new PopulationHub(p, name, Integer.valueOf(temp[0]), Integer.valueOf(temp[1]));
								if (p.getCapital() == null) {
									p.setCapital(popHub);
								}
								popHub.addToDS();
								break;
							case MOVED_MISSLE:
								missileID = serverInstance.getConnection(p.getID()).getIn().readLine();
								newHomebaseName = serverInstance.getConnection(p.getID()).getIn().readLine();

								for (IMissile m : Missile.getAllMissilesByID().values()) {
									if (m.getIDasString().equals(missileID)) {
										missile = m;
										break;
									}
								}
								for (PopulationHub ph : PopulationHub.getAllPopHubs()) {
									if(ph.getName().equals(newHomebaseName)) {
										newHomebase = ph;
										break;
									}
								}
								missile.move(newHomebase);
								break;
							case ATTACKING:
								missileID = serverInstance.getConnection(p.getID()).getIn().readLine();
								targetName = serverInstance.getConnection(p.getID()).getIn().readLine();

								for (IMissile m : Missile.getAllMissilesByID().values()) {
									if (m.getIDasString().equals(missileID)) {
										missile = m;
										break;
									}
								}
								for (PopulationHub ph : PopulationHub.getAllPopHubs()) {
									if(ph.getName().equals(targetName)) {
										target = ph;
										break;
									}
								}
								missile.launch(target);
								target.targetedByMissile(missile);
								GEP.sendEvent(serverInstance.getConnection(p.getID()).getOut(), GEP.EnumEvents.ATTACKED);
								break;
							case END_TURN:
								continueTurn = false;
								break;
						}
					} catch (IOException e) { e.printStackTrace(); }
				}
			}
			getPlayers().removeIf(Predicates.getInstance().playerDefeated());
		} while(getPlayers().size() > 1);
		ColdWar2100.winners = new ArrayList<>();
		winners.add(getPlayers().get(0));
	}
	/**
	 * Gets the next player id
	 * <br>
	 * if a players loses, his/her id is <b>not</b> reused in the current game
	 * @return the next id
	 */
	public static int getNextPlayerID() { return serverInstance.getConnections().size(); }
	public static ServerThread getServerInstance() { return serverInstance; }
	public static int getNumPlayers() { return numPlayers; }
	public static ArrayList<Player> getWinners() { return ColdWar2100.winners; }
	public static ArrayList<Player> getPlayers () {
		if (ColdWar2100.players == null) {
			players = new ArrayList<>(serverInstance.getConnections().size());
			serverInstance.getConnections().forEach( (ServerThread.Connection connection) -> players.add(connection.getPlayer()) );
		}
		return players;
	}
	public static ColdWar2100 getInstance() {
		if (instance == null) {
			Integer numPlayers = Integer.valueOf(new EnterAString(EnterAStringTypes.NUM_PLAYERS).getText());
			instance = new ColdWar2100(numPlayers);
		}
		return instance;
	}
	public static Turn getTurnInstance() {
		if (turnInstance == null) {
			turnInstance = Turn.getInstance();
		}
		return turnInstance;
	}
	public static void setServerInstance(ServerThread serverThread) { serverInstance = serverThread; }
}
