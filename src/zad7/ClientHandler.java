package zad7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private static Socket clientSocket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    private static final ArrayList<Message> messages = new ArrayList<>();
    private static final TimeComparator comparator = new TimeComparator();

    public ClientHandler(Socket socket) throws IOException {
        clientSocket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                int temp = in.readInt();
                Message message = (Message) in.readObject();
                if (message.getTextMessage().equals("quit")) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.close();
                    break;
                }
                messages.add(message);
                messages.sort(comparator);

                System.out.println("Notification: " + message.getTextMessage() + ", " + message.getTime());
                while (true) {
                    if (temp == 1 && messages.size() > 0) {
                        LocalTime time = LocalTime.parse(messages.get(0).getTime());
                        LocalTime current = LocalTime.now();
                        Thread.sleep(1000);
                        if (!time.isAfter(current)) {
                            out.writeObject(messages.get(0));
                            messages.remove(0);
                            System.out.println("Sent notification");
                        }
                    } else break;
                }

                temp = 0;
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
