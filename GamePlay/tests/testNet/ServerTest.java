package testNet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hayesj3 on 5/19/2015.
 */
public class ServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    System.out.println("listener = " + listener);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Hiya");
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}
