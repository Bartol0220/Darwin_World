package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int updateNumber = 0;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        synchronized (System.out) {
            System.out.println("\n" + updateNumber);
            System.out.println("Map " + worldMap.getId());
            System.out.println(message);
            System.out.println(worldMap);
            updateNumber++;
        }
    }
}
