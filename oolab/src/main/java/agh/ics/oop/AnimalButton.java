package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.Vector2d;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AnimalButton extends Button {
    private final Animal animal;
    private static final Map<String, Image> imageMap = new HashMap<>();

    private static Image loadImage(String name) {
        return imageMap.computeIfAbsent(name, _ -> new Image("images/" + name));
    }

    public Animal getAnimal() {
        return animal;
    }

    public AnimalButton(Animal animal, Optional<Animal> selectedAnimal, int cellWidth, Set<Vector2d> positions, GlobeMap map) {
        String name = animal.getName();
        if (map.areMultipleAnimalsOnField(animal.getPosition())) {
            if (selectedAnimal.isPresent() && (selectedAnimal.get() == animal)) {
                name = "wolfs-selected.png";
            } else if (positions.contains(animal.getPosition())) {
                name = "wolfs-gene.png";
            } else {
                name = "wolfs.png";
            }
        } else if (selectedAnimal.isPresent() && (selectedAnimal.get() == animal)) {
            name = "selected-" + name;
        } else if (positions.contains(animal.getPosition())) {
            name = "gene-" + name;
        }

        Image image = loadImage(name);
        BackgroundSize backgroundSize = new BackgroundSize(cellWidth * 0.9, cellWidth * 0.9, false, false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);

        this.setPadding(new Insets(0,0.5,0,0.5));
        this.setBackground(background);
        this.setAlignment(Pos.CENTER);
        this.setPrefSize(cellWidth * 0.9, cellWidth * 0.9);
        this.setMaxWidth(cellWidth * 0.9);
        this.setMaxHeight(cellWidth * 0.9);
        this.animal = animal;
    }
}
