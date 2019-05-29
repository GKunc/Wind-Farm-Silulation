package home.Controllers.sideBarMenuController;

import home.Agents.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Summary implements Initializable {

    @FXML
    public AnchorPane rootPane;


    @FXML
    public Button btnDashBoard;
    public Button optionsBtn;
    public Button summaryBtn;
    public Button failuresBtn;
    public Button weatherBtn;
    public Button realTimeBtn;

    public TextArea windowConsole;

    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == btnDashBoard) {
            loadStage(mouseEvent, "/home/fxml/DashBoard.fxml");
        }
        else if (mouseEvent.getSource() == optionsBtn) {
            loadStage(mouseEvent, "/home/fxml/Wind_Farm_Simulation.fxml");
        }
        else if (mouseEvent.getSource() == summaryBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/Summary.fxml");
        }
        else if (mouseEvent.getSource() == failuresBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/FailuresList.fxml");
        }
        else if (mouseEvent.getSource() == realTimeBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/RealTimeSimulation.fxml");
        }
        else if (mouseEvent.getSource() == weatherBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/Weather.fxml");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // todo
        // zainicjalizowac danymi z ekranu opcji symulacji
        windowConsole.clear();
        windowConsole.setVisible(true);
        windowConsole.setDisable(false);
        String firstData = "1884-11-01";
        String lastData = "1884-11-23";

        try {
            windowConsole.appendText(Main.showSimulationResults(new String[]{"fromApi", "12", "KRAKÓW", firstData, lastData}));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
