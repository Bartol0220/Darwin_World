package agh.ics.oop.model.observers;

import java.io.IOException;

public interface NewDayObserver {
    void newDay(int day) throws IOException;
}
