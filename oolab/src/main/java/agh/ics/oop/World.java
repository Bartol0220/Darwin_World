package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static void main(String[] args) {
        try {
            // 2 maps
//            List<MoveDirection> directions = OptionsParser.parse(args);
//            RectangularMap map1 = new RectangularMap(5, 5, 0);
//            GrassField map2 = new GrassField(20, 1);
//            List<Vector2d> positions = List.of(new Vector2d(0,2), new Vector2d(4,4));
//
//            MapChangeListener observer = new ConsoleMapDisplay();
//            map1.registerObserver(observer);
//            map2.registerObserver(observer);
//
//            Simulation simulation1 = new Simulation(positions, map1, directions);
//            Simulation simulation2 = new Simulation(positions, map2, directions);
//
//            List<Simulation> simulations = List.of(simulation1, simulation2);
//
//
            // 1000 maps
            Random random = new Random();
            MapChangeListener observer = new ConsoleMapDisplay();
            List<Simulation> simulations = new ArrayList<>();

            for(int i=0; i < 1000; i++){

                List<MoveDirection> directions = OptionsParser.parse(args);
                int mapRandom = random.nextInt(2);
                if (mapRandom == 0) {
                    int size = random.nextInt(21 - 5) + 5;
                    RectangularMap map = new RectangularMap(size, size, i);
                    List<Vector2d> positions = List.of(new Vector2d(0, size/2), new Vector2d(size - 4, 4));
                    map.registerObserver(observer);

                    Simulation simulation = new Simulation(positions, map, directions);
                    simulations.add(simulation);
                } else {
                    int grassNumber = random.nextInt(41 - 5) + 15;
                    GrassField map = new GrassField(grassNumber, i);
                    List<Vector2d> positions = List.of(new Vector2d(0, grassNumber/5), new Vector2d(grassNumber/2, 4));
                    map.registerObserver(observer);

                    Simulation simulation = new Simulation(positions, map, directions);
                    simulations.add(simulation);
                }
            }

            // SimulationEngine
            SimulationEngine engine = new SimulationEngine(simulations);

            try {
                Instant startTime = Instant.now();
                engine.runAsyncInThreadPool();
                engine.awaitSimulationsEnd();
                Instant finishTime = Instant.now();

                System.out.println("Time: " + Duration.between(startTime, finishTime).toMillis() + "ms");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("The system has terminated.");
    }
}
