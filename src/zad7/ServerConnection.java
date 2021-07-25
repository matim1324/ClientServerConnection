package zad7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerConnection implements Runnable {
    private Socket serverSocket;
    private ObjectInputStream in;

    public ServerConnection(Socket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
        this.in = new ObjectInputStream(serverSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = (Message) in.readObject();
                System.out.println("Server response: ");
                message.displayMessage();
                System.out.println(">");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
