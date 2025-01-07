package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class World {
    public static void main(String[] args) {
        Globe globe = new Globe(3, 3, 0);
        List<MoveDirection> directions1 = new ArrayList<>();
        MapChangeListener listener = new ConsoleMapDisplay();
        globe.registerObserver(listener);
        List<Vector2d> positions1 = List.of(new Vector2d(0,1), new Vector2d(2,2));
        Simulation simulation1 = new Simulation(positions1, globe, directions1, 10, 3);
        simulation1.run();
}
}
