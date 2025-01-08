package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.grass.AbstractGrassMaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable {
    private final List<MoveDirection> directions;
    private final AbstractWorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private final int startingEnergy;
    private int dayNumber = 0;
    private final AbstractGrassMaker grassMaker;


    public Simulation(List<Vector2d> positions, AbstractWorldMap map, List<MoveDirection> directions, int startingEnergy, int numberOfGenes, AbstractGrassMaker grassMaker) {
        this.directions = directions;
        this.map = map;
        this.startingEnergy = startingEnergy;
        this.grassMaker = grassMaker;

        for(Vector2d position : positions) {
            // własny zestaw genów dla każdego zwierzaka
            int[] genes = new Random().ints(numberOfGenes, 0, 8).toArray();

            Animal animal = new Animal(position, startingEnergy, genes);
            System.out.println(Arrays.toString(genes));
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

    private void runDay() {
        map.clearAnimalsOnGrass();
        //usmierc zwierzaki z listy (sprawdz, czy energia nadal 0)
        for (Animal animal : animals) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                // ignore
            }
            map.move(animal);
            // if energia rowna 0 to dodaj do listy usmiercania
        }
        // jedzenie
        List<Vector2d> positionsOfAnimalsOnGrass = map.getPositionsOfAnimalsOnGrass();
        for (Vector2d position : positionsOfAnimalsOnGrass) {
            // nakarm animala
        }
        // rozmnazanie najedzonych
        // wzrost roslin
        grassMaker.grow();
    }

    public void run(){
        while (!animals.isEmpty()) {
            dayNumber++;
            runDay();
            if (dayNumber == 5){
                animals.clear();
            }
        }
    }
}
