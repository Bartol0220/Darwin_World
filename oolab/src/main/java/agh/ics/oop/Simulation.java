package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<MoveDirection> directions;
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();

    public Simulation(List<Vector2d> positions, WorldMap map, List<MoveDirection> directions) {
        this.directions = directions;
        this.map = map;

        for(Vector2d p : positions) {
            Animal animal = new Animal(p);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                System.err.println("Animal cannot be placed. " + e.getMessage());
            }
        }
    }

    List<Animal> getAnimals() {
        return List.copyOf(animals);
    }

    public void run(){
        if(animals.isEmpty()) return;
        int i = 0;
        for(MoveDirection direction : directions) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.move(animals.get(i), direction);
            i ++;
            if(i >= animals.size()) i = 0;
        }
    }
}
