package gui;

import player.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterAString extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField enteredStr;
    private JLabel enteredStrLabel;

    private String dest = "";
    private EnterAStringTypes type = null;
    private Player activePlayer = null;

    public EnterAString(EnterAStringTypes type, Player p) {
        this.type = type;
        this.activePlayer = p;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(this.type.getTitle());

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        this.enteredStrLabel.setText(type.getMessage());
        this.enteredStr.setText(this.type.getDefaultTextForPlayer(this.activePlayer));

        pack();
        setVisible(true);
    }

    private void onOK() {
        this.dest = enteredStr.getText();
        dispose();
    }

    public String getText() { return this.dest; }

    public static void main(String[] args) {
        EnterAString dialog = new EnterAString(EnterAStringTypes.NUM_PLAYERS, null);
        System.exit(0);
    }
}
