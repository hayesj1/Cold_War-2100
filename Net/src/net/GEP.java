package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by hayesj3 on 5/19/2015.
 * <p>
 * GEP (pronounced jep) stands for Game Event Protocol and is used to send event between clients and servers.
 */
public final class GEP {

    public static void sendEvent(BufferedWriter out, EnumEvents event) throws IOException {
        out.write(event.toString());
        out.newLine();
        out.write(event.getTitle());
        out.newLine();
        out.write(event.getMessage());
    }
    public static String readEvent(BufferedReader in) throws IOException {
        String ret = "";
        String temp = "";
        for (int i = 0; (temp += in.readLine()) != null; i++, ret += temp, temp = "|");
        return ret;
    }
    public enum EnumEvents {
        FOUND("Founding Phase", "Found a new Pophub?"),
        ATTACK("Attack Phase", "Launch a missile?"),
        POPHUB_STRUCK("PopHub Attacked!", "Your Pophub has been struck by an enemy Missile!"),
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
