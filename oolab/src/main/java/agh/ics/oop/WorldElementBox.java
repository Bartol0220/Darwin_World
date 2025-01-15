package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldElementBox extends VBox {
    private static final Map<String, Image> imageMap = new HashMap<>();

    private static Image loadImage(String name) {
        return imageMap.computeIfAbsent(name, _ -> new Image("images/" + name));
    }

    public WorldElementBox(WorldElement element, Optional<Animal> selectedAnimal, int cellWidth) {
        String name = element.getName();
        if (selectedAnimal.isPresent() && (selectedAnimal.get() == element)) {
            name = "selected-" + name;
        }

        Image image = loadImage(name);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(cellWidth * 0.9);
        imageView.setFitWidth(cellWidth * 0.9);
        imageView.setPreserveRatio(true);

        this.getChildren().addAll(imageView);
        this.setAlignment(Pos.CENTER);
    }
}
