package zad7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 1234;

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(PORT);
        start(socket);
    }

    public static void start(ServerSocket socket) {
        System.out.println("SERVER");
        System.out.println("Waiting for client...");

        try {
            while (true) {
                Socket client = socket.accept();
                System.out.println("New client connected!");
                ClientHandler clientThread = new ClientHandler(client);

                executor.execute(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        stop(socket);
    }

    public static void stop(ServerSocket socket) {
        try {
            socket.close();
        } catch (Exception e) {
            System.out.println("Error (stop)");
        }
    }
}
