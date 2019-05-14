package home.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button btnSimulation;

    @FXML
    public Button btnHowItWorks;

    @FXML
    public Button btnDocumentation;


    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == btnSimulation) {
            loadStage(mouseEvent,"/home/fxml/Wind_Farm_Simulation.fxml");
        } else if (mouseEvent.getSource() == btnHowItWorks) {
            loadStage(mouseEvent,"/home/fxml/How_It_Works.fxml");
        } else if (mouseEvent.getSource() == btnDocumentation) {
            loadStage(mouseEvent,"/home/fxml/Documentation.fxml");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
