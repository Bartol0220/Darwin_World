package agh.ics.oop.model.grass;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.Vector2d;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GrassMakerDeadAnimal extends AbstractGrassMaker {

    public GrassMakerDeadAnimal(int startGrassNumber, int dayGrassNumber, GlobeMap map) {
        super(dayGrassNumber, map);

        for (int y=0; y < map.getHeight(); y++) {
            for (int x=0; x < map.getWidth(); x++) {
                freeWorseGrassPositions.add(new Vector2d(x, y));
            }
        }

        growNumberOfGrasss(startGrassNumber);
    }

    @Override
    public void deadAnimal(Animal animal) {
        for (int y=max(animal.getPosition().getY()-1, 0); y <= min(animal.getPosition().getY()+1, map.getHeight()-1); y++) {
            for (int x=animal.getPosition().getX()-1; x <= animal.getPosition().getX()+1; x++) {
                changePositionToFirstCategory(map.handleBoundsPositions(new Vector2d(x, y)));
            }
        }
    }
}
