package net.client;

import net.GEP;
import net.server.Server;
import net.server.ServerThread;
import player.Player;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by hayesj3 on 5/19/2015.
 */

public final class Client {
    private Socket serverAddr;
    private ServerThread server;

    private BufferedReader in;
    private BufferedWriter out;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private boolean connected = false;

    private Player player;

    public Socket getServerAddr() { return serverAddr; }
    public ServerThread getServer() { return server; }
    public BufferedReader getIn() { return in; }
    public BufferedWriter getOut() { return out; }
    public ObjectOutputStream getObjectOut() { return objectOut; }
    public ObjectInputStream getObjectIn() { return objectIn; }

    public void setServer(ServerThread serverThread) { server = serverThread; }

    public boolean connected() { return connected; }

    public boolean connect(InetAddress serverAddr) {
        try {
            if (serverAddr == null) { throw new NullPointerException("Server Address cannot be Null!"); }
            this.serverAddr = new Socket(serverAddr, Server.PORT);
            in = new BufferedReader(new InputStreamReader(this.serverAddr.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(this.serverAddr.getOutputStream()));
            connected = true;
        } catch (IOException ioe) { return false; }
        return true;
    }
    public void init() {
        this.player = new Player();
    }
    public void evaulateTurn() {
        try {
            String data;
            String[] parts;
            String title, message;
            GEP.EnumEvents event;
            while (true) {
                data = GEP.readEvent(in);
                parts = data.split("|");
                event = GEP.EnumEvents.valueOf(parts[0]);
                title = parts[1];
                message = parts[2];
                switch(event) {
                    case WELCOME:
                        break;
                    case POPHUB_STRUCK:
                        break;
                    case POPHUB_DESTROYED:
                        break;
                    case DEFEAT:
                        break;
                    case VICTOR:
                        break;
                    default:
                        break;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {}
        }
    }
}
