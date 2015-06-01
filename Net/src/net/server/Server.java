package net.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by hayesj3 on 5/19/2015.
 */
public class Server {
    private ServerSocket listener;
    public static final int PORT = 55655;


    public Server() throws IOException {
        listener = new ServerSocket(PORT);
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
    public static void main(String[] args) {

    }

}
