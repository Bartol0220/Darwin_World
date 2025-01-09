package agh.ics.oop.model.grass;


import agh.ics.oop.model.Animal;

public interface GrassMaker {
    void grow();

    void deadAnimal(Animal animal);

    void grassEaten(Grass grass);
}
