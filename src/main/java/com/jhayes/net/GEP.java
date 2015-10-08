package main.java.com.jhayes.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by hayesj3 on 5/19/2015.
 * <p>
 * GEP (pronounced jep) stands for Game Event Protocol and is used to send events between clients and servers.
 */
public final class GEP {

    public static void sendEvent(BufferedWriter out, EnumEvents event) throws IOException {
        out.write(event.toString());
        out.write("|");
        out.write(event.getTitle());
        out.write("|");
        out.write(event.getMessage());
        out.newLine();
    }
    public static String readEvent(BufferedReader in) throws IOException {
        return (new String(in.readLine()));
    }

	/**
	 * Enum for all the events that can occur pre-main.java.com.jhayes.game, during the main.java.com.jhayes.game, and post-main.java.com.jhayes.game. <br>
	 *     <p>
	 *          The Server -> Client Events are:<br>
	 *          &nbsp WELCOME<br>
     *          &nbsp ATTACKED<br>
	 *          &nbsp POPHUB_STRUCK<br>
	 *          &nbsp POPHUB_DESTROYED<br>
	 *          &nbsp DEFEAT<br>
	 *          &nbsp VICTOR<br>
	 *     </p>
	 *     <p>
	 *          The Client -> ServerEvents are:<br>
	 *          &nbsp FOUNDED<br>
     *          &nbsp MOVED_MISSLE<br>
     *          &nbsp ATTACKING<br>
	 *          &nbsp END_TURN<br>
	 *     </p>
	 *     <p>
	 *         Events
	 *     </p>
	 */
    public enum EnumEvents {
        WELCOME("Welcome to the Cold War!", "The year is 2100, and with the Cold war nearing it's 200th Anniversary tensions are at an all time high.\n" +
                "Nuclear war seems just over the horizon, and new commanders have been appointed to oversee nuclear operations.\n\n" +
                "You, Commander, have been chosen to oversee a vast nuclear arsenal as well as standard armed forces. " +
                "The research and development team has some grand ideas too... If you invest the time and finances..."),
        MOVED_MISSLE("Missile  Moved!", "Your missile has been stationed in a new Pophub."),
        FOUNDED("Pophub Founded", "A new Pophub has been founded!"),
        ATTACKING("Missile Launched!", "You have launched a missile at an enemy Pophub"),
        ATTACKED("Pophub Attacked", "Missile launched at Pophub"),
        POPHUB_STRUCK("Pophub Struck!", "Your Pophub has been struck by an enemy missile!"),
        POPHUB_DESTROYED("Pophub Destroyed!", "Your Pophub has been leveled by enemy missiles!"),
        END_TURN("Turn over", "Please wait for the other players to finish their turns"),
        DEFEAT("LOSER!", "Your mighty civilization has fallen at the hands of its enemies."),
        VICTOR("WINNER!", "Congratulations Commander! You have defeated all your opponents with your strategic prowess and arsenal of missiles!");

        private String title, message;

        public String getTitle() { return title; }
        public String getMessage() { return message; }
        EnumEvents(String title, String message) {
            this.title = title;
            this.message = message;
        }
    }
}
