package net.server;

import controller.Controller;

import java.io.*;
import java.net.Socket;

/**
 * Created by hayesj3 on 5/28/2015.
 */
public class ServerThread implements Runnable {

    private BufferedReader in;
    private BufferedWriter out;

    public ServerThread(Socket clientSocket) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }
    public void write(String message) throws IOException { out.write(message); }
    public String read() throws IOException { return in.readLine(); }
    public BufferedWriter getOut() { return out; }
    public BufferedReader getIn() { return in; }

    @Override
    public void run() {
        Controller.getInstance().startGame(this);
        System.out.println("Winner is: " + Controller.getWinners().get(0));
    }
}
