package gui;

import controller.Controller;
import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.IMissile;
import weapon.missile.baseMissile.Missile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.*;

public class AttackPopHubScreen extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTree popHubs;
    private JList missiles;
    private DefaultTreeCellRenderer renderer;

    private Player activePlayer;

    /**
     * @param activePlayer the player whose turn it is
     */
    public AttackPopHubScreen(Player activePlayer) {
        this.activePlayer = activePlayer;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        DefaultListModel<IMissile> listModel = new DefaultListModel<>();
        for (IMissile m : Missile.getAllMissilesByPlayer(activePlayer)) {
            listModel.addElement(m);
            System.out.println(m + " added to list!");
        }
        this.missiles = new JList(listModel);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PopulationHub target = onOK();

                Missile.getAllMissilesByPlayer(activePlayer);

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
    }

    private PopulationHub onOK() {
        PopulationHub target = (PopulationHub) this.popHubs.getLastSelectedPathComponent();
        dispose();
        return target;
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Controller.getInstance();
        AttackPopHubScreen dialog = new AttackPopHubScreen(Controller.getPlayers().get(0));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
    // create popHubs
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Players", true);
        DefaultMutableTreeNode playerNode;
        for (Player p : Controller.getInstance().getPlayers()) {
            playerNode = new DefaultMutableTreeNode(p, true);
            for (PopulationHub ph : PopulationHub.getPlayersPopHubs(p))
                playerNode.add(new DefaultMutableTreeNode(ph, false));
            root.add(playerNode);
        }
        this.popHubs = new JTree(root);
        this.popHubs.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.renderer = new DefaultTreeCellRenderer();
        this.renderer.setLeafIcon(null);
        //TODO set renderer values for closed and open branch nodes
        this.popHubs.setCellRenderer(this.renderer);

    }
}
