package agh.ics.oop.model.grass;

import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.Vector2d;

import static java.lang.Math.max;


public class GrassMakerEquator extends AbstractGrassMaker {

    public GrassMakerEquator(int startGrassNumber, int dayGrassNumber, GlobeMap map) {
        super(dayGrassNumber, map);
        double equatorHeight = max(0.2*map.getHeight(), 1);
        int startHeight = (int) ((double) map.getHeight() / 2 - equatorHeight / 2);

        for (int y=0; y < map.getHeight(); y++) {
            for (int x=0; x < map.getWidth(); x++) {
                if (y >= startHeight && y < startHeight + equatorHeight) {
                    Vector2d position = new Vector2d(x, y);
                    freeBetterGrassPositions.add(position);
                    map.changeFieldToBetter(position);
                } else {
                    freeWorseGrassPositions.add(new Vector2d(x, y));
                }
            }
        }
        allBetterGrassPositions.addAll(freeBetterGrassPositions);
        growNumberOfGrasss(startGrassNumber);
    }
}
