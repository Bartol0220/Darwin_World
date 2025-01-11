package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.grass.AbstractGrassMaker;

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
    private final AbstractGrassMaker grassMaker;
    private final int energyNeededForBreeding;
    private final int energyUsedWhileBreeding;
    private final Breeding breeding;


    public Simulation(List<Vector2d> positions, GlobeMap map, List<MoveDirection> directions, int startingEnergy, AbstractGrassMaker grassMaker, int energyNeededForBreeding, int energyUsedWhileBreeding, GenesFactory genesFactory) {
        this.directions = directions;
        this.map = map;
        this.startingEnergy = startingEnergy;
        this.grassMaker = grassMaker;
        this.energyNeededForBreeding = energyNeededForBreeding;
        this.energyUsedWhileBreeding = energyUsedWhileBreeding;

        breeding = new Breeding(energyNeededForBreeding, energyUsedWhileBreeding, map);

        for(Vector2d position : positions) {
            Genes genes = genesFactory.makeStartingGenes();
            Animal animal = new Animal(position, startingEnergy, genes, dayNumber, genesFactory);
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
        // rozmnazanie najedzonych
//        map.breedAnimals(energyNeededForBreeding, energyUsedWhileBreeding, dayNumber);
        breeding.breedAnimals(dayNumber);
        // jedzenie
        // TODO pozbyc sie fora (przesniesc go gdzies indziej)
        List<Vector2d> positionsOfAnimalsOnGrass = map.getPositionsOfAnimalsOnGrass();
        for (Vector2d position : positionsOfAnimalsOnGrass) {
            // wybierz animala do nakarmienia
            // nakarm animala
        }
        // wzrost roslin
        grassMaker.grow();
    }

    public void run(){
        while (!animals.isEmpty()) {
            dayNumber++;
            runDay();
//            if (dayNumber == 5){
//                animals.clear();
//            }
        }
    }
}
