package zad7;

public class TimeFormatException extends Exception {
    String time;

    public TimeFormatException(String s, String time){
        super(s);
        this.time = time;
    }

    public String getTime(){
        return time;
    }
}
