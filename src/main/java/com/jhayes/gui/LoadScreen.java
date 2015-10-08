package main.java.com.jhayes.gui;

import main.java.com.jhayes.command.EnumCommands;
import main.java.com.jhayes.exception.CommandArgsMismatchException;
import main.java.com.jhayes.exception.InvalidCommandException;
import main.java.com.jhayes.game.ColdWar2100;
import main.java.com.jhayes.resource.VisualResources;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class LoadScreen extends JDialog {
    private JPanel contentPane;
    private JPanel mainWindow;
    private JPanel loadingStuff;
    private JProgressBar loadingBar;
    private JPanel consolePane;
    private JPanel logoPanel;
    private JTextField commandEntry;
    private JTextArea console;
    private JButton enterCmd;
    private JLabel logo;
    private JButton play;

    private boolean ready = false;
    private boolean disposed = true;
    private boolean isIndeterminate = false;

    private final InputStream stdIn = System.in;
    private final PrintStream stdOut = System.out;

    public LoadScreen(boolean indeterminate) {
        this.isIndeterminate = indeterminate;
        setModal(true);

        setContentPane(contentPane);
        loadingBar.addChangeListener(l -> checkPlayable());
        play.addActionListener(l -> onPlay());
        enterCmd.addActionListener(l -> onEnter());

        if (indeterminate) {
            play.setEnabled(true);
            play.setToolTipText("Accept current players, and start the game!");
        }

        // call onEnter() on ENTER
        contentPane.registerKeyboardAction(l -> onEnter(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        // call onClose() on ESCAPE
        contentPane.registerKeyboardAction(l -> onClose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // call onClose() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
    }

    private void checkPlayable() {
        if(this.isIndeterminate) { return; }
        boolean enable = (loadingBar.getPercentComplete() == 1.0) ? true : false;
        play.setEnabled(enable);
        play.setToolTipText((enable) ? "Ready to play!" : "Loading!");
    }
    private void onPlay() {
        if (play.isEnabled()) {
            if (ColdWar2100.getServerInstance() == null) {
            //multiplayer
                this.ready = true;
                this.play.setEnabled(false);
            } else {
            //singleplayer
                ColdWar2100.getInstance().run();
            }
        } else { return; }
    }
    private void onClose() {
        this.disposed = true;
        dispose();
    }
    private void onEnter() {
        String[] str = commandEntry.getText().split(" ");
        String[] args = new String[str.length-1];
        EnumCommands command;
        if (str[0].equals(""))
            return;
        try {
            command = EnumCommands.validateCommand(str[0]);
            System.arraycopy(str, 1, args, 0, args.length);
            if (args.length != (command.getArgs().length))
                throw new CommandArgsMismatchException("Wrong number of Args!",
                        new Throwable("args.length != .command.getArgs().length"));
        }catch (InvalidCommandException ice) {
            System.out.println(ice.getMessage() + EnumCommands.helpMessage);
            clearInput();
            return;
        } catch (CommandArgsMismatchException came) {
            System.out.println(came.getMessage() + EnumCommands.helpMessage);
            clearInput();
            return;
        }
        command.doAction(args);
        //System.out.println("echo: " + .command.getCmd());
        commandEntry.setText("");
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.disposed = false;
    }

    private void clearOutput() {
        console.setText("");
    }
    private void clearInput() {
        commandEntry.setText("");
    }
    protected void clearInputAndOutput() { clearInput(); clearOutput(); }

    public boolean getReady() { return ready; }
    public InputStream getStdIn() { return stdIn; }
    public PrintStream getStdOut() { return stdOut; }

    public void init() {
        this.pack();

        JTextFieldInputStream commandEntryIS = new JTextFieldInputStream(commandEntry);
        PrintStream consolePS = new PrintStream(new JTextAreaOutputStream(console), true);
        System.setIn(commandEntryIS);
        System.setOut(consolePS);
        System.setErr(consolePS);

        System.out.println("Loading...");
        System.out.println("Type \'stop\' into the textbox below followed by pressing \'Enter\' to abort startup");
        this.setVisible(true);
    }
    public static void main(String[] args) {
        LoadScreen dialog = new LoadScreen(false);
        dialog.init();
        Scanner s = new Scanner(System.in);
        System.out.println("s = " + s.next());
        System.exit(0);
    }

    private void createUIComponents() {
        this.logo = new JLabel(new ImageIcon(VisualResources.logo.getPath().toString()));
        this.console = new JTextArea(100, 100);
        this.commandEntry = new JTextField(100);
        this.loadingBar = new JProgressBar();
        this.loadingBar.setIndeterminate(this.isIndeterminate);
    }

    public class JTextFieldInputStream extends InputStream {
        private byte[] contents;
        private int pointer = 0;

        public JTextFieldInputStream(final JTextField text) {

            text.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if(e.getKeyChar()=='\n'){
                        contents = text.getText().getBytes();
                        pointer = 0;
                        text.setText("");
                    }
                    super.keyReleased(e);
                }
            });
        }

        @Override
        public int read() throws IOException {
            if(!disposed && pointer >= contents.length) return -1;
            return this.contents[pointer++];
        }

    }
    public class JTextAreaOutputStream extends OutputStream {
        private JTextArea textArea;

        public JTextAreaOutputStream(JTextArea textArea) { this.textArea = textArea; }

        @Override
        public void write(int b) throws IOException {
                // redirects data to the text area
                textArea.append(String.valueOf((char) b));
                // scrolls the text area to the end of data
                textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}
