package zad7;

import java.time.LocalTime;
import java.util.Comparator;

public class TimeComparator implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        LocalTime first = LocalTime.parse(o1.getTime());
        LocalTime second = LocalTime.parse(o2.getTime());

        if (first.isBefore(second)) return -1;
        else if (first.isAfter(second)) return 1;

        return 0;
    }
}

