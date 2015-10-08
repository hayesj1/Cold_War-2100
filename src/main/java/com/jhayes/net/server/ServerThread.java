package main.java.com.jhayes.net.server;

import main.java.com.jhayes.game.ColdWar2100;
import main.java.com.jhayes.net.GEP;
import main.java.com.jhayes.player.Player;
import main.java.com.jhayes.weapon.missile.baseMissile.Missile;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hayesj3 on 5/28/2015.
 */
public final class ServerThread implements Runnable {

    private ArrayList<Connection> clients = null;

    public ServerThread() throws IOException {
        clients = new ArrayList<>();
        ColdWar2100 game = new ColdWar2100(this);
    }

    public void addClient(Socket clientSocket) {
        try {
            clients.add(new Connection(clientSocket));
        } catch (IOException e) { e.printStackTrace(); }
    }
    public Collection<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>(clients.size());
        clients.forEach( connection -> players.add(connection.getPlayer()) );
        return players;
    }
    public int getNumConnections(){ return this.clients.size(); }
    public ArrayList<Connection> getConnections() { return this.clients; }
    public Connection getConnection(int index) { return clients.get(index); }
    /**
     * @param message the message to broadcast to all Connections
     * @return any and all connections which failed threw exceptions when sending the message
     */
    public Collection<Connection> broadcastMessage(String message) {
        ArrayList<Connection> fails = new ArrayList<>(clients.size());
        for (Connection c : clients) {
            try {
                c.getOut().write(message);
            } catch (IOException e) {
                fails.add(c);
            }
        }
        return fails;
    }
    public Collection<Connection> broadcastEvent(GEP.EnumEvents event) {
        ArrayList<Connection> fails = new ArrayList<>(clients.size());
        for (Connection c : clients) {
            try {
                GEP.sendEvent(c.getOut(), event);
            } catch (IOException e) {
                fails.add(c);
                System.out.println("Failure!");
            }
        }
        return fails;
    }
    public Collection<Connection> broadcastMessage(String message, GEP.EnumEvents event) {
        if (event != null) {
            this.broadcastEvent(event);
        }
        ArrayList<Connection> fails = new ArrayList<>(clients.size());
        for (Connection c : clients) {
            try {
                GEP.sendEvent(c.getOut(), event);
            } catch (IOException e) {
                fails.add(c);
                System.out.println("Failure!");
            }
        }
        return fails;
    }
    @Override
    public void run() {
        ColdWar2100.getInstance().run();
        do {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (ColdWar2100.getWinners() == null);
        System.out.println("Winner is: " + ColdWar2100.getWinners().get(0));
        notifyAll();
    }
    public static final class Connection {
        private Socket clientAddr;
        private BufferedReader in;
        private BufferedWriter out;
        private ObjectInputStream objectIn;
        private ObjectOutputStream objectOut;

        private Player player = null;

        public Connection(Socket clientSocket) throws IOException {
            clientAddr = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //objectIn = new ObjectInputStream(clientSocket.getInputStream());
            //objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
            player = new Player();
        }
        public void getPlayersPophubNames() throws IOException {
            out.write(String.valueOf(player.getOwnedCities().size()));
            out.newLine();
            player.getOwnedCities().forEach(ph -> {
                try {
                    out.write(ph.getName());
                    out.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        public void getPlayersMissileIDs() throws IOException {
            out.write(String.valueOf(Missile.getAllMissilesByPlayer(player).size()));
            out.newLine();
            Missile.getAllMissilesByPlayer(player).forEach(m -> {
                try {
                    out.write(m.toString());
                    out.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        public void playerAllegiance() throws IOException { out.write(player.getAllegiance().toDisplayString()); out.newLine(); }
        public void write(String message) throws IOException { out.write(message); }
        public String read() throws IOException { return in.readLine(); }

        public Player getPlayer() { return this.player; }
        public BufferedWriter getOut() { return out; }
        public BufferedReader getIn() { return in; }
        public ObjectOutputStream getObjectOut() { return objectOut; }
        public ObjectInputStream getObjectIn() { return objectIn; }
        public Socket getClientSocket() { return clientAddr; }
    }

}
