package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;

import java.util.Arrays;

public class AnimalStats {
    private final Genes genes;
    private int energy;
    private int childrenCount=0;
    private int age=0;
    private int eatenGrass = 0;
    private int deathDate = -1;
    private Animal parent1;
    private Animal parent2;
    private int succesorCount = 0;

    public AnimalStats(Genes genes, int energy, Animal parent1, Animal parent2){
        this.genes = genes;
        this.energy = energy;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public void increaseSuccesorCount(){
        if (parent1 != null){
            parent1.getAnimalStats().succesorCount++;
            parent2.getAnimalStats().succesorCount++;
            parent1.getAnimalStats().increaseSuccesorCount();
            parent2.getAnimalStats().increaseSuccesorCount();
        }
    }

    public int[] getGenotypeArray(){
        return genes.getGenes();
    }

    public int getGeneIndex(){
        return genes.getGeneIndex();
    }

    public int getChildrenCount(){
        return childrenCount;
    }

    public int getEnergy(){
        return energy;
    }

    public int getAge(){
        return age;
    }

    public Genes getGenes() {
        return genes;
    }

    public void increaseEnergy(int value){
        energy += value;
    }

    public void decreaseEnergy(int value){
        energy -= value;
    }

    public void increaseChildrenCount(){
        childrenCount++;
    }

    public void increaseEatenGrass(){
        eatenGrass++;
    }

    public void increaseAge(){
        age++;
    }

    public void setDeathDate(int deathDate) {
        this.deathDate = deathDate;
    }

    @Override
    public String toString(){
        return "genotype: " + Arrays.toString(getGenotypeArray()) +
                "\ngeneIndex: %d\nenergy: %d\nchildren: %d\nsuccesors: %d\nage: %d\neatenGrass: %d\ndeathDate: %d"
                        .formatted(getGeneIndex(), energy, childrenCount, succesorCount, age, eatenGrass, deathDate);
    }

}
