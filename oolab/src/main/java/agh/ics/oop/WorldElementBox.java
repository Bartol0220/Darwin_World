package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.GlobeMap;
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

    public WorldElementBox(WorldElement element, Optional<Animal> selectedAnimal, GlobeMap map, int cellWidth) {
        String name = element.getName();
        Optional<String> prefix = Optional.empty();

        if (selectedAnimal.filter(presentSelectedAnimal -> presentSelectedAnimal.getPosition().equals(element.getPosition())).isPresent()) {
            prefix = Optional.of("selected-");
        }

        if (map.areMultipleAnimalsOnField(element.getPosition())) {
            name = prefix.map(presentPrefix -> presentPrefix + "wolfs.png").orElse("wolfs.png");
        } else {
            name = prefix.map(presentPrefix -> presentPrefix + element.getName()).orElse(name);
        }

        Image image = loadImage(name);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(cellWidth * 0.9);
        imageView.setFitWidth(cellWidth * 0.9);
        imageView.setPreserveRatio(true);

        this.setPrefSize(cellWidth * 0.9, cellWidth * 0.9);
        this.getChildren().addAll(imageView);
        this.setStyle("-fx-background-color: transparent");
        this.setAlignment(Pos.CENTER);
    }
}
