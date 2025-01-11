package agh.ics.oop.model.genes;

import agh.ics.oop.model.util.RandomGenes;
import java.util.Random;

public abstract class AbstractGeneMutator implements GeneMutator {
    protected final int minimumNumberOfMutations;
    protected final int maximumNumberOfMutations;
    protected Random random = new Random();

    protected AbstractGeneMutator(int minimumNumberOfMutations, int maximumNumberOfMutations) {
        this.minimumNumberOfMutations = minimumNumberOfMutations;
        this.maximumNumberOfMutations = maximumNumberOfMutations;
    }


    @Override
    public void mutate(int[] genes) {
        int howManyChanged = random.nextInt(minimumNumberOfMutations, maximumNumberOfMutations+1);
        RandomGenes genesToChange = new RandomGenes(genes.length, -1, howManyChanged);

        for (int geneToChange : genesToChange){
            mutateGene(geneToChange, genes);
        }
    }

    protected abstract void mutateGene(int geneToChange, int[] genes);
}
