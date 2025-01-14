package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldElementButton extends Button {
    private final Optional<Animal> animal;
    private static final Map<String, Image> imageMap = new HashMap<>();

    private static Image loadImage(String name) {
        return imageMap.computeIfAbsent(name, _ -> new Image("images/" + name));
    }

    public Optional<Animal> getAnimal() {
        return animal;
    }

    public WorldElementButton(WorldElement element, int cellWidth) {
        Image image = loadImage(element.getName());
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(cellWidth * 0.9);
        imageView.setFitWidth(cellWidth * 0.9);
        imageView.setPreserveRatio(true);

        this.setBackground(Background.EMPTY);
        this.setGraphic(imageView);
        this.setAlignment(Pos.CENTER);

        if (element instanceof Animal) {
            this.animal = Optional.of((Animal) element);
        } else {
            this.animal = Optional.empty();
        }
    }
}
