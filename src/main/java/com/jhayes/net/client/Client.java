package main.java.com.jhayes.net.client;

import main.java.com.jhayes.gui.HUD;
import main.java.com.jhayes.gui.LoadScreen;
import main.java.com.jhayes.net.GEP;
import main.java.com.jhayes.net.server.Server;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by hayesj3 on 5/19/2015.
 */

public final class Client {
    private Socket serverAddr;

    private BufferedReader in;
    private BufferedWriter out;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private boolean connected = false;

    private LoadScreen console;
    private HUD hud;
    private static Client instance;

    private Client() {}

    public final Socket getServerAddr() { return serverAddr; }
    public final BufferedReader getIn() { return in; }
    public final BufferedWriter getOut() { return out; }
    public final ObjectOutputStream getObjectOut() { return objectOut; }
    public final ObjectInputStream getObjectIn() { return objectIn; }

    public static Client getInstance() {
        if (Client.instance == null) {
            Client.instance = new Client();
        }
        return Client.instance;
    }
    public final boolean connected() { return connected; }
    public final boolean connect(InetAddress serverAddr) {
        try {
            if (serverAddr == null) { throw new NullPointerException("Server Address cannot be Null!"); }
            this.serverAddr = new Socket(serverAddr, Server.PORT);
            in = new BufferedReader(new InputStreamReader(this.serverAddr.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(this.serverAddr.getOutputStream()));
            connected = true;
        } catch (IOException ioe) { return false; }
        return true;
    }
    public final void init() {
        connected = true;
    }
    public final boolean init(String serverAddr) {
        this.init();
        try {
            connect(InetAddress.getByName(serverAddr));
            System.out.println("Connected!");
        } catch (UnknownHostException uhe) {
            this.connected = false;
            uhe.printStackTrace();
            return false;
        }
        return true;
    }
    public final void evaluateTurn() {
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
                        JOptionPane.showMessageDialog(this.hud, message, title, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case POPHUB_STRUCK:
                        JOptionPane.showMessageDialog(this.hud, message, title, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case POPHUB_DESTROYED:
                        JOptionPane.showMessageDialog(this.hud, message, title, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case DEFEAT:
                        JOptionPane.showMessageDialog(this.hud, message, title, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case VICTOR:
                        JOptionPane.showMessageDialog(this.hud, message, title, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        break;
                }
                System.out.println("Turn Over!");
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
    public static void main(String[] args) throws InterruptedException {
        Client client = Client.getInstance();
        client.console = new LoadScreen(false);
        client.console.init();

        while(!client.console.getReady()) {
            client.wait();
        }
        try {
            client.init();
            //instance.connect(InetAddress.getByName(JOptionPane.showInputDialog("Enter the IP Address of the server:")));
            do {
                client.hud = new HUD(client);
                client.evaluateTurn();
                if (client.connected) {

                }
            } while(!client.connected);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
