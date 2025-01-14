package agh.ics.oop.model;

import agh.ics.oop.model.grass.AbstractGrassMaker;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Stats {
    private final HashMap<ArrayHashMap, Integer> genesRecords = new HashMap<>();
    private final AbstractGrassMaker grassMaker;
    private final GlobeMap map;
    private int currentAnimalCount = 0;
    private int deadAnimalCount = 0;
    private int maximumAnimalCount = 0;
    private int minimumAnimalCount = Integer.MAX_VALUE;
    private int allAnimalCount = 0;
    private int bornAnimalCount = 0;
    private int grassCount;
    private int freeSpace = 0;
    private ArrayHashMap mostCommonGenes;
    private double averageEnergy = 0;
    private double averageLifespan = 0;
    private double averageBirthrate = 0;

    public Stats(GlobeMap map, AbstractGrassMaker grassMaker, int startingGrassCount, int startingEnergy, int startingAnimalCount){
        this.map = map;
        this.grassMaker = grassMaker;
        this.minimumAnimalCount = startingAnimalCount;
        grassCount = startingGrassCount;
        calculateFreeSpace();
        averageEnergy = startingEnergy;
    }

    private void increaseDeadAnimalCount(){
        deadAnimalCount++;
    }

    private void decreaseCurrentAnimalCount(){
        minimumAnimalCount = min(minimumAnimalCount, currentAnimalCount);
    }

    private void updateGrassCount(){
        grassCount = grassMaker.getCurrentGrassNumber();
    }

    private void calculateFreeSpace(){
        freeSpace = map.getFreeSpace();
    }

    private void calculateNewAverageEnergy(List<Animal> animals){
        averageEnergy = animals.stream().mapToInt(Animal::getEnergy).average().orElse(0.0);
    }

    private void addGenesToHashMap(int[] genes){
        ArrayHashMap arrayHashMap = new ArrayHashMap(genes);
        if (genesRecords.containsKey(arrayHashMap)){
            genesRecords.replace(arrayHashMap, genesRecords.get(arrayHashMap) + 1);
        } else {
            genesRecords.put(arrayHashMap, 1);
        }
        setMostCommonGenes();
    }

    private void deleteGenesFromHashMap(int[] genes){
        ArrayHashMap arrayHashMap = new ArrayHashMap(genes);
        genesRecords.replace(arrayHashMap, genesRecords.get(arrayHashMap) - 1);
        if (genesRecords.get(arrayHashMap) < 1){
            genesRecords.remove(arrayHashMap);
        }
        setMostCommonGenes();
    }

    private void setMostCommonGenes(){
        this.mostCommonGenes = genesRecords.entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void calculateNewAverageLifeSpan(int ageWhenDied){
        averageLifespan = averageLifespan + (ageWhenDied - averageLifespan)/deadAnimalCount;
    }

    public void calculateAverageBirthrate(List<Animal> animals){
        // TODO policz kiedy sie dzieciak rodzi - 2*bornAnimals/allAnimals cos tam
        // averageBirthrate = (double) (2 * bornAnimalCount) /allAnimalCount+1;
        averageBirthrate = animals.stream().mapToInt(animal -> animal.getAnimalStats().getChildrenCount()).average().orElse(0.0);
    }

    public void newAnimalPlaced(int[] genes){
        allAnimalCount++;
        maximumAnimalCount = max(maximumAnimalCount, currentAnimalCount);
        addGenesToHashMap(genes);
    }

    public void newAnimalBorn(int[] genes){
        bornAnimalCount++;
        newAnimalPlaced(genes);
    }

    public void animalNotPlaced(int[] genes){
        decreaseCurrentAnimalCount();
        deleteGenesFromHashMap(genes);
        maximumAnimalCount--;
    }

    public void animalDied(int[] genes){
        decreaseCurrentAnimalCount();
        increaseDeadAnimalCount();
        deleteGenesFromHashMap(genes);
    }

    public void updateUponEating(){
        updateGrassCount();
        calculateFreeSpace();
    }


    public void updateGeneralStats(List<Animal> animals){
        this.currentAnimalCount = animals.size();
        updateGrassCount();
        calculateFreeSpace();
        calculateNewAverageEnergy(animals);
    }

    @Override
    public String toString(){
        return "ANIMALS: %d\nGRASS: %d\nSPACE: %d\nGENE: "
                .formatted(currentAnimalCount, grassCount, freeSpace) + mostCommonGenes + "\nENERGY: %f\nLIFESPAN: %f\nCHILDREN %f".formatted(averageEnergy, averageLifespan, averageBirthrate);
    }
}
