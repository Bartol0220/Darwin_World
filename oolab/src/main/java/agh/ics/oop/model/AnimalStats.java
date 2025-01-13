package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;

import java.util.Arrays;

public class AnimalStats {
    private final Genes genes;
    private int energy;
    private int childrenCount=0;
    private int age=0;
    private int eatenGrass=0;
    private int deathDate = -1;

    public AnimalStats(Genes genes, int energy){
        this.genes = genes;
        this.energy = energy;
    }

    public int[] getGenotype(){
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

    public void increseEnergy(int value){
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
        return "genotype: " + Arrays.toString(getGenotype()) +
                "\nenergy: %d\nchildren: %d\nage: %d\neatenGrass: %d\ndeathDate: %d"
                        .formatted(energy, childrenCount, age, eatenGrass, deathDate);
    }

}
