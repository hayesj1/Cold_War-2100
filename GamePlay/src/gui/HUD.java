package gui;

import controller.Controller;
import map.populationHub.PopulationHub;
import net.GEP;
import player.Player;
import weapon.missile.baseMissile.IMissile;
import weapon.missile.baseMissile.Missile;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by hayesj3 on 6/1/2015.
 */
public class HUD extends JDialog {

    private JPanel mainPanel;
    private JList allPlayerPopHubs;
    private JList allPlayerMissiles;
    private JTable diplomacy;
    private JPanel stats;
    private JPanel actions;
    private JPanel buttons;
    private JButton attack;
    private JButton found;
    private JButton moveMissile;
    private JButton endTurn;

    private Player user;
    private boolean populated = false;
    private DefaultListModel<PopulationHub> popHubModel;
    private DefaultListModel<IMissile> missilesModel;

    public DefaultListModel getMissilesModel() { return missilesModel; }
    public DefaultListModel getPopHubModel() { return popHubModel; }

    private HUD(){
        this(new Player());
    }
    public HUD(Player p) {
        this.user = p;

        this.found.addActionListener( e -> onFound() );
        this.attack.addActionListener( e -> onAttack() );
        this.moveMissile.addActionListener(e -> onMoveMissile() );
        this.endTurn.addActionListener(e -> onEndTurn());
    }

    private void onFound() {
        String coords = new EnterAString(EnterAStringTypes.ENTER_COORDS, null).getText();
        while(!coords.matches("\\d+,\\d+")) {
            coords = new EnterAString(EnterAStringTypes.ENTER_COORDS, null).getText();
        }
        String[] temp = coords.split(",");
        PopulationHub popHub = new PopulationHub(user, Integer.valueOf(temp[0]), Integer.valueOf(temp[1]));
        try {
            GEP.sendEvent(user.getClient().getOut(), GEP.EnumEvents.FOUNDED);
            popHub.writeObject(user.getClient().getObjectOut());
        } catch (IOException e) {}

        if (user.getCapital() == null) {
            user.setCapital(popHub);
        }
        //popHub.addToDS();
        allPlayerPopHubs.clearSelection();
        this.populateListModels();
    }
    private void onAttack() {
        PopulationHub target = (PopulationHub) allPlayerPopHubs.getSelectedValue();
        IMissile missile = Missile.getAllMissilesByPlayer(user).get(allPlayerMissiles.getSelectedIndex());
        user.getClient().getServerAddr();
        allPlayerPopHubs.clearSelection();
        allPlayerMissiles.clearSelection();
    }
    private void onMoveMissile() {
        IMissile m = Missile.getAllMissilesByPlayer(user).get(allPlayerMissiles.getSelectedIndex());
        PopulationHub newHomeBase = user.getOwnedCities().get(allPlayerPopHubs.getSelectedIndex());
        if (!(m.getHomeBase().equals(newHomeBase))) {
            try {
                GEP.sendEvent(user.getClient().getOut(), GEP.EnumEvents.MOVED_MISSLE);
                newHomeBase.writeObject(user.getClient().getObjectOut());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            allPlayerPopHubs.clearSelection();
            allPlayerMissiles.clearSelection();
        }
    }
    private void onEndTurn() {
        try {
            GEP.sendEvent(user.getClient().getOut(), GEP.EnumEvents.END_TURN);
        } catch (IOException e) {}
    }

    public void populateListModels() {
        this.popHubModel = new DefaultListModel<>();
        user.getOwnedCities().forEach(ph -> this.popHubModel.addElement(ph));
        this.missilesModel = new DefaultListModel<>();
        Missile.getAllMissilesByPlayer(user).forEach(m -> this.missilesModel.addElement(m));
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
        JFrame frame = new JFrame("HUD");
        Controller.getInstance();
        frame.setContentPane(new HUD(Controller.getPlayers().get(0)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
