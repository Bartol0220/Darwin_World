package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.Grass;
import agh.ics.oop.model.util.RandomVector2d;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class Simulation implements Runnable {
    private final GlobeMap map;
    private final List<Animal> animals = new LinkedList<>();
    private final AbstractGrassMaker grassMaker;
    private final Breeding breeding;
    private int dayNumber = 0;


    public Simulation(GlobeMap map, AbstractGrassMaker grassMaker, Breeding breeding, AnimalCreator animalCreator, int startNumberOfAnimals) {
        this.map = map;
        this.grassMaker = grassMaker;
        this.breeding = breeding;

        createAnimals(map, startNumberOfAnimals, animalCreator);
    }

    private void createAnimals(GlobeMap map, int startNumberOfAnimals, AnimalCreator animalCreator) {
        RandomVector2d positions = new RandomVector2d(map.getWidth(), map.getHeight(), startNumberOfAnimals);
        for(Vector2d position : positions) {
            Animal animal = animalCreator.createStartingAnimal(position, dayNumber);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                System.err.println("Animal cannot be placed. " + e.getMessage());
            }
        }
    }

    public void run(){
        while (!animals.isEmpty()) {
            dayNumber++;
            runDay();
        }
    }

    private void runDay() {
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        breeding.breedAnimals(dayNumber);
        grassMaker.grow();
    }

    private void removeDeadAnimals() {
        animals.removeIf(animal -> animal.getEnergy() < 1);
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                // TODO zamienić z ignore
            }
            map.move(animal);
        }
    }

    private void feedAnimals() {
        List<Optional<Grass>> grassList = map.feedAnimals();
        for (Optional<Grass> grass : grassList) {
            grass.ifPresent(grassMaker::grassEaten);
        }
    }
}
