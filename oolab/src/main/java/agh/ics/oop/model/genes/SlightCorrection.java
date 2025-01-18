package agh.ics.oop.model.genes;

public class SlightCorrection extends AbstractGeneMutator {

    public SlightCorrection(int minimumNumberOfMutations, int maximumNumberOfMutations) {
        super(minimumNumberOfMutations, maximumNumberOfMutations);
    }

    @Override
    protected void mutateGene(int geneToChange, int[] genes) {
        int increase = random.nextInt(0, 2);
        int value;

        if (increase == 1) value = 1;
        else value = -1;

        genes[geneToChange] = (genes[geneToChange]+value+8)%8;
    }
}
