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

        for(Vector2d position : positions) {
            Animal animal = new Animal(position);
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
        int animals_size = animals.size();
        int i = 0;
        for(MoveDirection direction : directions) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                // ignore
            }
            map.move(animals.get(i % animals_size), direction);
            i ++;
        }
    }
}
