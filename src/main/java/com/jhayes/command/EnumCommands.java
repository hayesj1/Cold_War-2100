package main.java.com.jhayes.command;

import main.java.com.jhayes.exception.InvalidCommandException;
import main.java.com.jhayes.net.client.Client;
import main.java.com.jhayes.net.server.Server;

/**
 * Created by hayesj3 on 9/28/2015.
 */
public enum EnumCommands {

    CONNECT("connect", new String[] {"<ip_address>"}, "Connects to the server running at <ip address>"),
    START("start", new String[] { "<max_players>" }, "Starts a singleplayer game against an AI"),
    STOP("stop", new String[] {"<Y|N>"}, "Terminates the loading process or running game"),
    HELP("help", new String[] {}, "Lists all commands");

    public static final String helpMessage = "\nType \'help\' or \'?\' for a list of commands!";

    private String cmd;
    private String[] args;
    private String description;

    public String getCmd() { return cmd; }
    public String[] getArgs() { return args; }
    public String getDescription() { return description; }

    EnumCommands(String cmd, String[] args, String desc) {
        this.cmd = cmd;
        this.args = args;
        this.description = desc;
    }
    private void printUsage(EnumCommands com) {
        System.out.print(com.cmd);
        for (String arg : com.args) {
            if (arg == null)
                break;
            System.out.print(" " + arg);
        }
    }
    public static EnumCommands validateCommand(String str)
            throws InvalidCommandException {
        EnumCommands command = null;
        switch(str) {
            case "connect":
                command = CONNECT;
                break;
            case "start":
                command = START;
                break;
            case "stop":
                command = STOP;
                break;
            case "help":
            case "?":
                command = HELP;
                break;
            default:
                throw new InvalidCommandException(str + " is not a valid command!",
                        new Throwable("There is no command constant for: " + str));
        }
        return command;
    }

    public void doAction(String[] args) {
        switch(this) {
            case CONNECT:
                Client.getInstance().init(args[0]);
                break;
            case START:
                Server.getInstance(Integer.valueOf(args[0]));
                break;
            case STOP:
                if (args[0].toUpperCase().equals("Y"))
                    System.exit(0);
                break;
            case HELP:
                for(EnumCommands com : EnumCommands.values()) {
                    System.out.println("Command: " + com.cmd);
                    System.out.println(com.description);
                    System.out.print("Usage: ");
                    printUsage(com);
                    System.out.println("\n");
                }
                break;
            default:
                System.out.println("Command does not exist; something must be very broken!");
        }
    }
}
