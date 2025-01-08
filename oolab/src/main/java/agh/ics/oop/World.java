package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {

        int width = 10;
        int height = 10;
        AbstractGrassMaker grassMaker;
        if (false) {
            grassMaker = new GrassMakerDeadAnimal(1, 1, height, width);
        } else {
            grassMaker = new GrassMakerEquator(1, 1, height, width);
        }
        Globe globe = new Globe(width, height, 0, grassMaker);
        List<MoveDirection> directions1 = new ArrayList<>();
        MapChangeListener listener = new ConsoleMapDisplay();
        globe.registerObserver(listener);
        List<Vector2d> positions1 = List.of(new Vector2d(0,1), new Vector2d(2,2));
        Simulation simulation1 = new Simulation(positions1, globe, directions1, 10, 5, grassMaker);
        simulation1.run();
}
}
