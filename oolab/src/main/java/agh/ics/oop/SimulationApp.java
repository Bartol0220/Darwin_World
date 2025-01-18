package agh.ics.oop;

import agh.ics.oop.presenter.SetupPresenter;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("setup.fxml"));
        ScrollPane viewRoot = loader.load();
        SetupPresenter presenter = loader.getController();

        configureStage(primaryStage, viewRoot);
        primaryStage.show();

        primaryStage.setOnCloseRequest(windowEvent -> {
            try {
                if (presenter.stopAllSimulations()) {
                    System.exit(0);
                } else {
                    windowEvent.consume();
                }
            } catch (InterruptedException e) {
                System.exit(1);
            }
        });
    }

    private void configureStage(Stage primaryStage, ScrollPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        primaryStage.setMaximized(true);
    }

    public SimulationPresenter showSimulation(Stage newStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        ScrollPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        configureStage(newStage, viewRoot);

        newStage.show();
        return presenter;
    }
}
