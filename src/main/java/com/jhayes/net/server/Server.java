package main.java.com.jhayes.net.server;

import main.java.com.jhayes.gui.LoadScreen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by hayesj3 on 5/19/2015.
 */
public final class Server implements Runnable {
    private ServerSocket listener;
    private int maxPlayers;
    public static final int PORT = 55655;
    public static final int LOCAL_PORT = 8080;

    private static Server instance;

    private Server() throws IOException { this(2);}
    private Server(int maxplayers) throws IOException { this(LOCAL_PORT, maxplayers); }
    private Server(int maxPlayers, int port)throws IOException {
        listener = new ServerSocket(port);
        this.maxPlayers = maxPlayers;
    }

    public void listen() throws IOException {
        try {
            ServerThread serverThread = new ServerThread();
            //BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            //System.out.println("Type \'DONE\' when all your players have connected");
            do {
                serverThread.addClient(listener.accept());
                System.out.println("Player Accepted!");
                //System.out.println("More players? (Y/N)");
            } while (serverThread.getNumConnections() < maxPlayers);
            serverThread.run();
        } finally {
            listener.close();
        }
    }
    public static InetAddress getServerAddress() {
        InetAddress serverIP = null;

        Enumeration<NetworkInterface> NIs = null;
        try {
            NIs = NetworkInterface.getNetworkInterfaces();
            while (NIs.hasMoreElements()) {
                NetworkInterface NI = NIs.nextElement();
                Enumeration<InetAddress> IPs = NI.getInetAddresses();
                while (IPs.hasMoreElements()) {
                    InetAddress IP = IPs.nextElement();
                    System.out.println(IP.getHostName() + ":" + IP);
                    if (IP.isAnyLocalAddress()) {
                        serverIP = IP;
                        break;
                    }
                }
            }
            if (serverIP == null) { serverIP = InetAddress.getLoopbackAddress(); }
        } catch (SocketException se) {
            se.printStackTrace();
        }
        return serverIP;
    }

    public void init() {
        LoadScreen console = new LoadScreen(true);
        console.init();
    }

    public static void main(String[] args) throws IOException {
        Server server;
        if (args.length == 1){
            server = Server.getInstance(Integer.valueOf(args[0]), Server.PORT);
            server.init();
        } else if (args.length == 2) {
            server = getInstance(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
            server.init();
            System.out.println("serverIP = " + Server.getServerAddress());
        } else {
            System.out.println("Usage: java -jar Cold_War_2100_Server.jar < max # of players > [ " + Server.PORT + " | other port # ]");
            return;
        }
        server.run();
    }
    @Override
    public void run() {
        System.out.println("This is the server!");
        try {
            this.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Server getInstance() { return Server.getInstance(2); }
    public static Server getInstance(int maxPlayers) { return Server.getInstance(maxPlayers, Server.LOCAL_PORT); }
    public static Server getInstance(int maxPlayers, int port) {
        if (instance == null) {
            try {
                instance = new Server(maxPlayers, port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
