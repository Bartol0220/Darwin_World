package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class WorldElementBox extends VBox {
    private static final Map<String, Image> imageMap = new HashMap<>();

    private static Image loadImage(String name) {
        return imageMap.computeIfAbsent(name, _ -> new Image("images/"+name));
    }

    public WorldElementBox(WorldElement element) {
        Image image = loadImage(element.getName());
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);

        Label label = new Label();
        if (element instanceof Animal) {
            label.setText("Z "+element.getPosition().toString());
        } else {
            label.setText("Trawa");
        }
        this.getChildren().addAll(imageView, label);
        this.setAlignment(Pos.CENTER);
    }
}
