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
        RectangularMap map1 = new RectangularMap(5, 5, 0);
        List<MoveDirection> directions1 = new ArrayList<>();
        MapChangeListener listener = new ConsoleMapDisplay();
        map1.registerObserver(listener);
        List<Vector2d> positions1 = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation1 = new Simulation(positions1, map1, directions1, 10);
        simulation1.run();
}
}
