package net.server;

import controller.Controller;

import java.io.*;
import java.net.Socket;

/**
 * Created by hayesj3 on 5/28/2015.
 */
public class ServerThread implements Runnable {

    private Socket clientAddr;
    private BufferedReader in;
    private BufferedWriter out;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    public ServerThread(Socket clientSocket) throws IOException {
        clientAddr = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        objectIn = new ObjectInputStream(clientSocket.getInputStream());
        objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
    }
    public void write(String message) throws IOException { out.write(message); }
    public String read() throws IOException { return in.readLine(); }
    public BufferedWriter getOut() { return out; }
    public BufferedReader getIn() { return in; }
    public ObjectOutputStream getObjectOut() { return objectOut; }
    public ObjectInputStream getObjectIn() { return objectIn; }
    public Socket getClientSocket() { return clientAddr; }
/*
    private void onFound() {
        String coords = new EnterAString(EnterAStringTypes.ENTER_COORDS, null).getText();
        while(!coords.matches("\\d+,\\d+")) {
            coords = new EnterAString(EnterAStringTypes.ENTER_COORDS, null).getText();
        }
        String[] temp = coords.split(",");
        PopulationHub popHub = new PopulationHub(user, Integer.valueOf(temp[0]), Integer.valueOf(temp[1]));
        if (user.getCapital() == null) {
            user.setCapital(popHub);
        }
        popHub.addToDS();
        allPlayerPopHubs.clearSelection();
        this.populateListModels();
    }
    private void onAttack(PopulationHub target, IMissile missile) {
        PopulationHub target = (PopulationHub) allPlayerPopHubs.getSelectedValue();
        Missile.getAllMissilesByPlayer(user).get(allPlayerMissiles.getSelectedIndex()).launch(target);
        allPlayerPopHubs.clearSelection();
        allPlayerMissiles.clearSelection();
    }
    private void onMoveMissile() {
        IMissile m = Missile.getAllMissilesByPlayer(user).get(allPlayerMissiles.getSelectedIndex());
        PopulationHub newHomeBase = user.getOwnedCities().get(allPlayerPopHubs.getSelectedIndex());
        if (!(m.getHomeBase().equals(newHomeBase))) { m.move(newHomeBase); }
        else {
            allPlayerPopHubs.clearSelection();
            allPlayerMissiles.clearSelection();
        }
    }*/

    @Override
    public void run() {
        Controller.getInstance().setServerInstance(this);
        Controller.getInstance().run();
        System.out.println("Winner is: " + Controller.getWinners().get(0));
        notifyAll();
    }

}
