package zad7;

import java.io.Serializable;

public class Message implements Serializable {
    private String textMessage;
    private String time;

    Message(String textMessage, String time) {
        this.textMessage = textMessage;
        this.time = time;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getTime() {
        return time;
    }

    public void displayMessage() {
        System.out.println("Message: " + textMessage + ",  time: " + time);
    }
}
