package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Simulation implements Runnable {
    private final List<MoveDirection> directions;
    private final GlobeMap map;
    private final List<Animal> animals = new ArrayList<>();
    private final int startingEnergy;
    private int dayNumber = 0;
    private final int numberOfGenes;

    private final AbstractGrassMaker grassMaker;


    public Simulation(List<Vector2d> positions, GlobeMap map, List<MoveDirection> directions, int startingEnergy, int numberOfGenes, AbstractGrassMaker grassMaker) {
        this.directions = directions;
        this.map = map;
        this.startingEnergy = startingEnergy;
        this.numberOfGenes = numberOfGenes;
        this.grassMaker = grassMaker;

        for(Vector2d position : positions) {
            // własny zestaw genów dla każdego zwierzaka
            Genes genes = new Genes(numberOfGenes);
            Animal animal = new Animal(position, startingEnergy, genes, dayNumber);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                System.err.println("Animal cannot be placed. " + e.getMessage());
            }
        }
    }

    private void runDay() {
        map.clearMapOfAnimalsOnGrass();
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
            // wybierz animala do nakarmienia
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
