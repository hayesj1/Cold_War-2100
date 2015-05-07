package gui;

import controller.Controller;
import map.populationHub.PopulationHub;
import player.Player;
import util.Predicates;
import weapon.missile.baseMissile.IMissile;
import weapon.missile.baseMissile.Missile;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.event.*;
import java.util.ArrayList;

public class AttackPopHubScreen extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList popHubs;
    private JList missiles;
    private JScrollPane popHubScroller;
    private JScrollPane missileScroller;
    private DefaultTreeCellRenderer renderer;

    private Player activePlayer;

    /**
     * @param activePlayer the player whose turn it is
     */
    public AttackPopHubScreen(Player activePlayer) {
        this.activePlayer = activePlayer;
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PopulationHub target = (PopulationHub) popHubs.getSelectedValue();
                Missile.getAllMissilesByPlayer(activePlayer).get(missiles.getSelectedIndex()).launch(target);
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        setContentPane(contentPane);
        this.pack();
        this.setVisible(true);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Controller.getInstance().startGame();
        AttackPopHubScreen dialog = new AttackPopHubScreen(Controller.getPlayers().get(0));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
    // create JList popHubs
        DefaultListModel<PopulationHub> popHubListModel = new DefaultListModel<>();
        ArrayList<PopulationHub> popHubsToList = new ArrayList<PopulationHub>(PopulationHub.getAllPopHubs());
        popHubsToList.removeIf(Predicates.getInstance().popHubsOwnedByAnyoneBut(activePlayer));
        for (PopulationHub ph : popHubsToList) {
            popHubListModel.addElement(ph);
        }
        this.popHubs = new JList(popHubListModel);

    // create JList missiles
        DefaultListModel<IMissile> missileListModel = new DefaultListModel<>();
        for (IMissile m : Missile.getAllMissilesByPlayer(this.activePlayer)) {
            if (m.isLaunched()) { continue; }
            missileListModel.addElement(m);
        }
        this.missiles = new JList(missileListModel);
    }
}