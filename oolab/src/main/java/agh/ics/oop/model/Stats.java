package agh.ics.oop.model;

import agh.ics.oop.HashArray;
import agh.ics.oop.model.grass.AbstractGrassMaker;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Stats {
    private final HashMap<HashArray, Integer> genesRecords = new HashMap<>();
    private final AbstractGrassMaker grassMaker;
    private final GlobeMap map;
    private int currentAnimalCount = 0;
    private int deadAnimalCount = 0;
    private int maximumAnimalCount = 0;
    private int minimumAnimalCount = Integer.MAX_VALUE;
    private int allAnimalCount = 0;
    private int grassCount;
    private int freeSpace = 0;
    private HashArray mostCommonGenes;
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

    public int getCurrentAnimalCount() { return  currentAnimalCount;}
  
    public int getDeadAnimalCount() { return  deadAnimalCount;}
  
    public int getMaximumAnimalCount() { return  maximumAnimalCount;}
  
    public int getMinimumAnimalCount() { return  minimumAnimalCount;}
  
    public int getAllAnimalCount() { return  allAnimalCount;}

    public int getBornAnimalCount() { return  bornAnimalCount;}
  
    public int getGrassCount() { return  grassCount;}
  
    public int getFreeSpace() { return  freeSpace;}
  
    public HashArray getMostCommonGenes() { return  mostCommonGenes;}
  
    public double getAverageEnergy() { return  averageEnergy;}
  
    public double getAverageLifespan() { return  averageLifespan;}
  
    public double getAverageBirthrate() { return  averageBirthrate;}

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

    private void addGenesToHashMap(Animal animal){
        HashArray arrayHashMap = new HashArray(animal.getGenes());
        if (genesRecords.containsKey(arrayHashMap)){
            genesRecords.replace(arrayHashMap, genesRecords.get(arrayHashMap) + 1);
        } else {
            genesRecords.put(arrayHashMap, 1);
        }
        setMostCommonGenes();
    }

    private void deleteGenesFromHashMap(Animal animal){
        HashArray arrayHashMap = new HashArray(animal.getGenes());
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

    public void calculateAverageBirthRate(List<Animal> animals){
        averageBirthrate = animals.stream().mapToInt(animal -> animal.getAnimalStats().getChildrenCount()).average().orElse(0.0);
    }

    public void newAnimalPlaced(Animal animal){
        allAnimalCount++;
        maximumAnimalCount = max(maximumAnimalCount, currentAnimalCount);
        addGenesToHashMap(animal);
    }

    public void newAnimalBorn(Animal animal){
        bornAnimalCount++;
        newAnimalPlaced(animal);
    }

    public void animalNotPlaced(Animal animal){
        decreaseCurrentAnimalCount();
        deleteGenesFromHashMap(animal);
        maximumAnimalCount--;
    }

    public void animalDied(Animal animal){
        decreaseCurrentAnimalCount();
        increaseDeadAnimalCount();
        deleteGenesFromHashMap(animal);
    }

    public void updateUponEating(){
        updateGrassCount();
        calculateFreeSpace();
    }


    public void updateGeneralStats(List<Animal> animals){
        this.currentAnimalCount = animals.size();
        allAnimalCount = max(allAnimalCount, currentAnimalCount);
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
