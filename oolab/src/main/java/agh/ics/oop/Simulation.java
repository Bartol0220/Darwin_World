package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Simulation implements Runnable {
    private final List<MoveDirection> directions;
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private final int startingEnergy;
    private int dayNumber = 0;
    private final int numberOfGenes;



    public Simulation(List<Vector2d> positions, WorldMap map, List<MoveDirection> directions, int startingEnergy, int numberOfGenes) {
        this.directions = directions;
        this.map = map;
        this.startingEnergy = startingEnergy;
        this.numberOfGenes = numberOfGenes;

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

    List<Animal> getAnimals() {
        return List.copyOf(animals);
    }

    private void runDay(){
        //usmierc zwierzaki z listy (sprawdz, czy energia nadal 0)
        for (Animal animal : animals) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                // ignore
            }
            map.move(animal);
            /*if energia rowna 0 to dodaj do listy usmiercania*/
        }
        // jedzenie

        // wzrost roslin
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
