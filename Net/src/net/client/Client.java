package net.client;

import net.GEP;
import net.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by hayesj3 on 5/19/2015.
 */

public class Client {
    private Socket clientAddr;

    private BufferedReader in;
    private BufferedWriter out;
    private boolean connected = false;

    public boolean connected() { return connected; }

    public boolean connect(InetAddress serverAddr) {
        try {
            if (serverAddr == null) { throw new NullPointerException("Server Address cannot be Null!"); }
            clientAddr = new Socket(serverAddr, Server.PORT);
            in = new BufferedReader(new InputStreamReader(clientAddr.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientAddr.getOutputStream()));
            connected = true;
        } catch (IOException ioe) { return false; }
        return true;
    }

    public void beginGame() {
        try {
            String data;
            String[] lines;
            String title, message;
            GEP.EnumEvents event;
            while (true) {
                data = GEP.readEvent(in);
                lines = data.split("|");
                event = GEP.EnumEvents.valueOf(lines[0]);
                title = lines[1];
                message = lines[2];


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
