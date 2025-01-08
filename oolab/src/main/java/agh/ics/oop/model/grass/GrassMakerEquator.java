package agh.ics.oop.model.grass;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;

import static java.lang.Math.max;


public class GrassMakerEquator extends AbstractGrassMaker {

    public GrassMakerEquator(int startGrassNumber, int dayGrassNumber, int height, int width) {
        super(dayGrassNumber, height, width);
        double equatorHeight = max(0.2*height, 1);
        int startHeight = (int) ((double) height / 2 - equatorHeight / 2);

        for (int y=0; y < height; y++) {
            for (int x=0; x < width; x++) {
                if (y >= startHeight && y < startHeight + equatorHeight) {
                    firstCategoryPositions.add(new Vector2d(x, y));
                } else {
                    secondCategoryPositions.add(new Vector2d(x, y));
                }
            }
        }

        growStartGrass(startGrassNumber);
    }

    @Override
    public void deadAnimal(Animal animal) {
        // ignore
    }
}
