package agh.ics.oop.model;

public class SlightCorrection extends AbstractGeneMutator{
    @Override
    public void mutate(int[] genes) {
        int[] genesToChange = randomGeneIndex(genes);
        for (int geneToChange : genesToChange){
            int increase = random.nextInt(0, 2);
            int value;

            if (increase == 1) value = 1;
            else value = -1;

            genes[geneToChange] = (genes[geneToChange]+value+8)%8;
        }
    }
}
