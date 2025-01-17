package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.WorldElement;
import agh.ics.oop.model.stats.Stats;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
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
        if (map.areMultipleAnimalsOnField(element.getPosition())) {
            if (selectedAnimal.isPresent() && (selectedAnimal.get() == element)) {
                name = "wolfs-selected.png";
            } else {
                name = "wolfs.png";
            }
        } else if (selectedAnimal.isPresent() && (selectedAnimal.get() == element)) {
            name = "selected-" + name;
        }

//        if (element instanceof Animal) {
//            Animal animal = (Animal) element;
//            ProgressBar progressBar = new ProgressBar();
//
//            progressBar.setStyle("-fx-background-color: #73ff00; -fx-background-insets: 1 1 1 1; -fx-padding: 0.3em;");
////            progressBar.getStylesheets().add("simulation.css");
//
//            progressBar.setProgress(animal.getAnimalStats().getEnergy()/ stats.getDayMaximumEnergy());
//
//            this.getChildren().add(progressBar);
//        }

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
