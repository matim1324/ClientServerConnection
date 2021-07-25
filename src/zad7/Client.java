package zad7;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            ServerConnection connection = new ServerConnection(socket);

            start(connection, socket);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void start(ServerConnection connection, Socket socket) {
        try {
            Scanner input = new Scanner(System.in);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            new Thread(connection).start();

            while (true) {
                System.out.println("Input the message (quit to stop connection):");
                String text = input.nextLine();
                String time;

                if (text.equals("quit")) {
                    time = "";
                } else {
                    System.out.println("Input the time to send notification back (format -> 'hh:mm'):");
                    time = readTime(input);
                }

                Message message = new Message(text, time);
                out.writeInt(1);
                out.writeObject(message);
                if (text.equals("quit")) break;
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readTime(Scanner input) {
        String time;

        while (true) {
            time = input.nextLine();
            try {
                checkTimeFormat(time);
            } catch (TimeFormatException e) {
                System.out.println(e.getMessage());
                System.out.println("Input time: " + e.getTime() + ", input valid time (hh:mm):");
                continue;
            }

            try {
                checkTimeRange(time);
            } catch (TimeRangeException e) {
                System.out.println(e.getMessage());
                System.out.println("Input current time:");
                continue;
            }
            break;
        }

        return time;
    }

    public static void checkTimeFormat(String time) throws TimeFormatException {
        if (!time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            throw new TimeFormatException("Invalid time format", time);
        }
    }

    public static void checkTimeRange(String time) throws TimeRangeException {
        LocalTime currentTime = LocalTime.now();
        LocalTime checkTime = LocalTime.parse(time);
        if (!checkTime.isAfter(currentTime)) {
            throw new TimeRangeException("Entered invalid past time");
        }

    }
}
