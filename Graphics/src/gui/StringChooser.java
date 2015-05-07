package gui;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by hayesj3 on 4/29/2015.
 */
public class StringChooser extends JDialog {

    private JTextField choose;
    private JLabel chooseLabel;
    private JPanel contentPane;
    private JButton buttonOK;

    private String destination = "";

    public StringChooser() {

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    // call onOK() on ENTER
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

    }

    private void onOK() {
        destination = this.choose.getText();
        dispose();
    }
    private void onCancel() {
        destination = "Defacto";
        dispose();
    }

    public String getValue() { return this.destination; }
}
