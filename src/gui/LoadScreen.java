package gui;

import resource.VisualResources;

import javax.swing.*;
import java.awt.event.*;

public class LoadScreen extends JDialog {
    private JPanel contentPane;
    private JPanel mainWindow;
    private JPanel loadingStuff;
    private JProgressBar loadingBar;


    public LoadScreen() {
        setModal(true);

        JLabel logo = new JLabel(new ImageIcon(VisualResources.logo.getImage()));
        mainWindow.add(logo);
        contentPane.add(mainWindow);
        contentPane.add(loadingStuff);

        setContentPane(contentPane);
// call onClose() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

// call onClose() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void onClose() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        LoadScreen dialog = new LoadScreen();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
