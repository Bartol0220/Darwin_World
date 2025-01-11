package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.grass.AbstractGrassMaker;

import java.util.ArrayList;
import java.util.List;


public class Simulation implements Runnable {
    private final GlobeMap map;
    private final List<Animal> animals = new ArrayList<>();
    private int dayNumber = 0;
    private final AbstractGrassMaker grassMaker;
    private final Breeding breeding;


    public Simulation(List<Vector2d> positions, GlobeMap map, int startingEnergy, AbstractGrassMaker grassMaker, Breeding breeding, GenesFactory genesFactory) {
        this.map = map;
        this.grassMaker = grassMaker;
        this.breeding = breeding;

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
//        map.findAnimalsToBreed(energyNeededForBreeding, energyUsedWhileBreeding, dayNumber);
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
