package home.Controllers.numberBtnsController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class fourController implements Initializable {

    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button prev;

    @FXML
    public Button next;


    @FXML
    public void handleButtonClicks(ActionEvent mouseEvent) throws IOException {
       if (mouseEvent.getSource() == prev) {
           loadStage(mouseEvent,"/home/fxml/Turbine/three.fxml");
       } else if (mouseEvent.getSource() == next) {
           loadStage(mouseEvent,"/home/fxml/Turbine/five.fxml");
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
