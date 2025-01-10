package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;


import java.util.ArrayList;


import java.util.List;

public class World {
    public static void main(String[] args) {

        int width = 4;
        int height = 3;
        GlobeMap globeMap = new GlobeMap(width, height, 0);
        AbstractGrassMaker grassMaker;
        if (false) {
            grassMaker = new GrassMakerDeadAnimal(1, 1, globeMap);
        } else {
            grassMaker = new GrassMakerEquator(1, 1, globeMap);
        }
        List<MoveDirection> directions1 = new ArrayList<>();
        MapChangeListener listener = new ConsoleMapDisplay();
        globeMap.registerObserver(listener);
        List<Vector2d> positions1 = List.of(new Vector2d(0,1), new Vector2d(2,2));
        GeneMutator geneMutator = new ClassicMutation();
        Simulation simulation1 = new Simulation(positions1, globeMap, directions1, 10, 5, grassMaker, geneMutator, 0, 1);
        simulation1.run();
}
}
