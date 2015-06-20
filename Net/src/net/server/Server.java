package net.server;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by hayesj3 on 5/19/2015.
 */
public final class Server {
    private ServerSocket listener;
    public static final int PORT = 55655;
    public static final int LOCAL_PORT = 8080;

    public Server() throws IOException { this(LOCAL_PORT ); }
    public Server(int port)throws IOException {
        listener = new ServerSocket(port);
    }

    public void listen() throws IOException {
        try {
            while (true) {
                ServerThread serverThread = new ServerThread(listener.accept());
                serverThread.run();
            }
        } finally {
            listener.close();
        }
    }
    public InetAddress getServerAddress() {
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
    public static void main(String[] args) throws InvalidArgumentException, IOException {
        if (args.length != 1) {
            System.out.println("java -jar Cold_War_2100.jar " + Server.PORT);
            throw new InvalidArgumentException(new String[] {"Server must have the port number ( " + Server.PORT + ") passed as an argument!"} );
        }
        Server server = new Server(Integer.valueOf(args[0]));
        System.out.println("serverIP = " + server.getServerAddress());
        server.listen();
    }

}
