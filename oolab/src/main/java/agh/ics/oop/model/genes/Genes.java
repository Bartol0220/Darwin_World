package agh.ics.oop.model.genes;

import agh.ics.oop.model.Animal;

import java.util.*;

public class Genes {
    private final int[] genes;
    private int geneIndex;
    private final Random random = new Random(); // static?

    public Genes(int numberOfGenes) {
        genes = random.ints(numberOfGenes, 0, 8).toArray();
        geneIndex = random.nextInt(genes.length);
    }

    public Genes(Animal stronger, Animal weaker, GeneMutator geneMutator, int numberOfGenes) {
        geneIndex = random.nextInt(numberOfGenes);

        int[] genes = getGenesFromParents(stronger, weaker, numberOfGenes);
        geneMutator.mutate(genes);
        this.genes = genes;
    }

    private int[] getGenesFromParents(Animal stronger, Animal weaker, int numberOfGenes) {
        int energySum = stronger.getEnergy() + weaker.getEnergy();
        int strongerOnTheLeft = random.nextInt(0, 2);
        int weakerGenesCount = (int) ((double) weaker.getEnergy() / energySum * numberOfGenes);
        int strongerGenesCount = numberOfGenes - weakerGenesCount;

        int[] strongerGenes = stronger.getGenes();
        int[] weakerGenes = weaker.getGenes();
        int[] genes = new int[numberOfGenes];

        if (strongerOnTheLeft == 1) {
            if (strongerGenesCount >= 0) System.arraycopy(strongerGenes, 0, genes, 0, strongerGenesCount);
            if (numberOfGenes - strongerGenesCount >= 0)
                System.arraycopy(weakerGenes, strongerGenesCount, genes, strongerGenesCount, numberOfGenes - strongerGenesCount);
        } else {
            if (weakerGenesCount >= 0) System.arraycopy(weakerGenes, 0, genes, 0, weakerGenesCount);
            if (numberOfGenes - weakerGenesCount >= 0)
                System.arraycopy(strongerGenes, weakerGenesCount, genes, weakerGenesCount, numberOfGenes - weakerGenesCount);
        }

        return genes;
    }

    public int getGeneIndex() {
        return geneIndex;
    }

    public int[] getGenes() {
        return Arrays.copyOf(genes, genes.length);
    }

    public int useCurrentGene() {
        int currentGene = genes[geneIndex];
        geneIndex = (geneIndex + 1) % genes.length;
        return currentGene;
    }

    public int getCurrentGene() {
        return genes[geneIndex];
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
