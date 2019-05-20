package home.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {

    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button btnDashBoard;

    public RadioButton takeFromApi;
    public RadioButton takeFromFile;
    public TextField cityName;
    public ChoiceBox<String> chooseCity;
    public Button startButton;
    public TextField startDate;
    public TextField endDate;
    public TextField turbineNumber;

    ObservableList<String> dataFromFile = FXCollections.observableArrayList("Kielce", "Linowo", "Gdańsk");

    final ToggleGroup group = new ToggleGroup();

    public void takeDataFromApi() {
        cityName.setDisable(false);
        chooseCity.setDisable(true);
        startButton.setDisable(false);
        startDate.setDisable(false);
        endDate.setDisable(false);
        turbineNumber.setDisable(false);
    }

    public void takeDataFromFile() {
        cityName.setDisable(true);
        chooseCity.setDisable(false);
        startButton.setDisable(false);
        startDate.setDisable(true);
        endDate.setDisable(true);
        turbineNumber.setDisable(false);
    }

    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
       if (mouseEvent.getSource() == btnDashBoard) {
           loadStage(mouseEvent,"/home/fxml/DashBoard.fxml");
       }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        takeFromApi.setToggleGroup(group);
        takeFromFile.setToggleGroup(group);

        chooseCity.setItems(dataFromFile);
    }

    @FXML
    public void loadStage(ActionEvent event, String fxml) throws IOException {
        Parent screenToLoad = FXMLLoader.load(getClass().getResource(fxml));
        Scene loadedScene = new Scene(screenToLoad);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(loadedScene);
        window.show();
    }
}
