package agh.ics.oop.model.grass;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GrassMakerDeadAnimal extends AbstractGrassMaker {

    public GrassMakerDeadAnimal(int startGrassNumber, int dayGrassNumber, int height, int width) {
        super(dayGrassNumber, height, width);

        for (int y=0; y < height; y++) {
            for (int x=0; x < width; x++) {
                secondCategoryPositions.add(new Vector2d(x, y));
            }
        }

        growStartGrass(startGrassNumber);
    }

    @Override
    public void deadAnimal(Animal animal) {
        for (int y=max(animal.getPosition().getY()-1, 0); y <= min(animal.getPosition().getY()+1, height-1); y++) {
            for (int x=animal.getPosition().getX()-1; x <= animal.getPosition().getX()+1; x++) {
                changePositionToFirstCategory(new Vector2d((x+width)%width, y));
            }
        }
    }
}
