package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SetupPresenter {
    @FXML
    private TextField moves;

    public void onSimulationStartClicked() {
        List<MoveDirection> directions = OptionsParser.parse(moves.getText().split(" "));
        GrassField map = new GrassField(4, 0);
        List<Vector2d> positions = List.of(new Vector2d(1,1), new Vector2d(3,3));
        Simulation simulation = new Simulation(positions, map, directions);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));

        SimulationApp newSimulationApp = new SimulationApp();

        try {
            SimulationPresenter presenter = newSimulationApp.showSimulation(new Stage());
            presenter.newSimulation(map, simulationEngine);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
