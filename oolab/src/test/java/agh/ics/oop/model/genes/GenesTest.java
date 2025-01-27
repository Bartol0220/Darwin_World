package agh.ics.oop.model.genes;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenesTest {
    @Test
    void usesGeneCorrectly() {
        Genes genes = new Genes(5);
        Animal animal = new Animal(new Vector2d(0, 0), genes, 10, 10);
        int oldIndex = genes.getGeneIndex();

        int usedGene = genes.useCurrentGene();


        assertTrue(genes.getGeneIndex() - oldIndex == 1 || oldIndex - genes.getGeneIndex() == 4);
        assertTrue(usedGene >= 0 && usedGene <= 7);
    }
}