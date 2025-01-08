package agh.ics.oop.model;

import java.util.*;

public class Genes {
    private final int[] genes;
    private int geneIndex;
    private Random random = new Random();
    private int numberOfGenes;

    public Genes(int numberOfGenes){
        this.numberOfGenes = numberOfGenes;
        genes = random.ints(numberOfGenes, 0, 8).toArray();
        geneIndex = random.nextInt(genes.length);
    }

    public Genes(Animal stronger, Animal weaker, GeneMutator geneMutator, int numberOfGenes){
        this.numberOfGenes = numberOfGenes;
        geneIndex = random.nextInt(numberOfGenes);

        int energySum = stronger.getEnergy() + weaker.getEnergy();
        int strongerOnTheLeft = random.nextInt(0,2);
        int weakerGenesCount = (int) ((double) weaker.getEnergy() / energySum * numberOfGenes);
        int strongerGenesCount = numberOfGenes - weakerGenesCount;


        int[] strongerGenes = stronger.getGenes();
        int[] weakerGenes = weaker.getGenes();

        int[] genes = new int[numberOfGenes];
        if (strongerOnTheLeft == 1){
            for (int i = 0; i < strongerGenesCount; i++){
                genes[i] = strongerGenes[i];
            }
            for (int j = strongerGenesCount; j < numberOfGenes; j++){
                genes[j] = weakerGenes[j];
            }
        } else if (strongerOnTheLeft == 0){
            for (int i = 0; i < weakerGenesCount; i++){
                genes[i] = weakerGenes[i];
            }
            for (int j = weakerGenesCount; j < numberOfGenes; j++){
                genes[j] = strongerGenes[j];
            }
        }
        System.out.println("przed mutacja " + Arrays.toString(genes));
        geneMutator.mutate(genes);
        System.out.println("po mutacji  " + Arrays.toString(genes));
        this.genes = genes;
    }

    public int[] getGenes() {
        return Arrays.copyOf(genes, genes.length);
    }

    public int getNumberOfGenes(){
        return numberOfGenes;
    }

    public int useCurrentGene(){
        int currentGene = genes[geneIndex];
        geneIndex = (geneIndex + 1)%genes.length;
        return currentGene;
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
