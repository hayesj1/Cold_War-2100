package gui;

import player.EnumEmpires;
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
    public EnterAString(EnterAStringTypes type) {
        this.type = type;

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
        this.enteredStr.setText(this.type.getDefaultTextPlayerIndependent());

        pack();
        setVisible(true);
    }
    public EnterAString(EnterAStringTypes type, Player p) {
        this(type, p.getAllegiance()); }
    public EnterAString(EnterAStringTypes type, EnumEmpires empire) {
        this.type = type;

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
        this.enteredStr.setText(this.type.getDefaultTextForEmpire(empire));

        pack();
        setVisible(true);
    }

    private void onOK() {
        this.dest = enteredStr.getText();
        dispose();
    }

    public String getText() { return this.dest; }

    public static void main(String[] args) {
        EnterAString dialog = new EnterAString(EnterAStringTypes.NUM_PLAYERS, new Player());
        System.exit(0);
    }

    private void createUIComponents() {
        buttonOK = new JButton("OK");
    }
}
