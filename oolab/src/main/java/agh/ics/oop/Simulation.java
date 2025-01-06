package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<MoveDirection> directions;
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private final int startingEnergy;


    public Simulation(List<Vector2d> positions, WorldMap map, List<MoveDirection> directions, int startingEnergy) {
        this.directions = directions;
        this.map = map;
        this.startingEnergy = startingEnergy;


        int[] arr = {1, 1};
        for(Vector2d position : positions) {
            Animal animal = new Animal(position, startingEnergy, arr);
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
        for(int j = 0; j<10;j++) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                // ignore
            }
            map.move(animals.get(i % animals_size));
            i ++;
        }
    }
}
