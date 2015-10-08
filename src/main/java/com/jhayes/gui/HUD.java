package main.java.com.jhayes.gui;

import main.java.com.jhayes.game.ColdWar2100;
import main.java.com.jhayes.net.GEP;
import main.java.com.jhayes.net.client.Client;
import main.java.com.jhayes.player.EnumEmpires;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by hayesj3 on 6/1/2015.
 */
public class HUD extends JDialog {

    private JPanel mainPanel;
    private JList<String> allPlayerPopHubs;
    private JList<String> allPlayerMissiles;
    private JTable diplomacy;
    private JPanel stats;
    private JPanel actions;
    private JPanel buttons;
    private JButton attack;
    private JButton found;
    private JButton moveMissile;
    private JButton endTurn;

    private Client user;
    private boolean populated = false;
    private DefaultListModel<String> popHubModel;
    private DefaultListModel<String> missilesModel;

    public DefaultListModel getMissilesModel() { return missilesModel; }
    public DefaultListModel getPopHubModel() { return popHubModel; }

    private HUD() {
        this(Client.getInstance());
    }
    public HUD(Client c) {
        this.user = c;

        this.found.addActionListener( e -> onFound() );
        this.moveMissile.addActionListener(e -> onMoveMissile());
        this.attack.addActionListener(e -> onAttack());
        this.endTurn.addActionListener(e -> onEndTurn());
    }

    private void onFound() {
        String coords = new EnterAString(EnterAStringTypes.ENTER_COORDS).getText();
        while(!coords.matches("\\d+,\\d+")) {
            coords = new EnterAString(EnterAStringTypes.ENTER_COORDS).getText();
        }
        String name;
        String str = "";
        try {
            user.getOut().write("allegiance");
            str = user.getIn().readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        name = new EnterAString(EnterAStringTypes.POPHUB, EnumEmpires.valueOf(str)).getText();

        try {
            GEP.sendEvent(user.getOut(), GEP.EnumEvents.FOUNDED);
            //user.getObjectOut().writeObject(user);
            user.getOut().write(coords);
            user.getOut().newLine();
            user.getOut().write(name);
            user.getOut().newLine();
        } catch (IOException e) {}

        allPlayerPopHubs.clearSelection();
        this.populateListModels();
    }
    private void onMoveMissile() {
        String missileID = allPlayerMissiles.getSelectedValue();
        String newHomeBaseName = allPlayerPopHubs.getSelectedValue();
            try {
                GEP.sendEvent(user.getOut(), GEP.EnumEvents.MOVED_MISSLE);
                user.getOut().write(missileID);
                user.getOut().newLine();
                user.getOut().write(newHomeBaseName);
                user.getOut().newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        allPlayerPopHubs.clearSelection();
        allPlayerMissiles.clearSelection();
    }
    private void onAttack() {
        String missileID = allPlayerMissiles.getSelectedValue();
        String targetName = allPlayerPopHubs.getSelectedValue();
        try {
            GEP.sendEvent(user.getOut(), GEP.EnumEvents.ATTACKING);
            user.getOut().write(missileID);
            user.getOut().newLine();
            user.getOut().write(targetName);
            user.getOut().newLine();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        allPlayerPopHubs.clearSelection();
        allPlayerMissiles.clearSelection();
    }
    private void onEndTurn() {
        try {
            GEP.sendEvent(user.getOut(), GEP.EnumEvents.END_TURN);
        } catch (IOException e) {}
    }

    public void populateListModels() {
        this.popHubModel = new DefaultListModel<>();
        this.missilesModel = new DefaultListModel<>();

        int size = -1;
        try {
            user.getOut().write("pophubs");
            size = Integer.valueOf(user.getIn().readLine());
            for (int i = 0; i < size; i++) {
                popHubModel.addElement(user.getIn().readLine());
            }
            user.getOut().write("missiles");
            size = Integer.valueOf(user.getIn().readLine());
            for (int i = 0; i < size; i++) {
                missilesModel.addElement(user.getIn().readLine());
            }
        } catch (IOException e) { e.printStackTrace(); }

        if (!populated) {
            this.populated = true;
        } else {
            allPlayerPopHubs.setModel(popHubModel);
            allPlayerMissiles.setModel(missilesModel);
        }
    }

    public void display() {
        this.pack();
        this.setVisible(true);
    }
    private void createUIComponents() {
        this.populateListModels();
        this.allPlayerPopHubs = new JList(popHubModel);
        this.allPlayerMissiles = new JList(missilesModel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HUD TEST");
        ColdWar2100.getInstance();
        frame.setContentPane(new HUD(Client.getInstance()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
