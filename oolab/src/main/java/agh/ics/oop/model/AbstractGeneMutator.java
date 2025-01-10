package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomGenes;

import java.util.Arrays;
import java.util.Random;

public abstract class AbstractGeneMutator implements GeneMutator {
    protected Random random = new Random();

    @Override
    public abstract void mutate(int[] genes);

    protected int[] randomGeneIndex(int[] genes){
        int howManyChanged = random.nextInt(0, genes.length);
        int[] indexes = new int[howManyChanged];
        RandomGenes randomPositionGenerator = new RandomGenes(genes.length, -1, howManyChanged);
        int i = 0;
        for(Integer value : randomPositionGenerator) {
            indexes[i] = value;
            i++;
        }
        return indexes;
    }
}
