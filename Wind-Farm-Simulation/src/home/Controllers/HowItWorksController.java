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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HowItWorksController implements Initializable {

    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button btnDashBoard;

    @FXML
    public Button one;
    public Button two;
    public Button three;
    public Button four;
    public Button five;
    public Button six;
    public Button seven;
    public Button eight;
    public Button nine;
    public Button ten;
    public Button eleven;
    public Button twelve;
    public Button thirteen;



    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == btnDashBoard) {
            loadStage(mouseEvent,"/home/fxml/DashBoard.fxml");
        } else if (mouseEvent.getSource() == one) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/one.fxml");
        } else if (mouseEvent.getSource() == two) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/two.fxml");
        } else if (mouseEvent.getSource() == three) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/three.fxml");
        } else if (mouseEvent.getSource() == four) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/four.fxml");
        } else if (mouseEvent.getSource() == five) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        } else if (mouseEvent.getSource() == six) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        } else if (mouseEvent.getSource() == seven) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        } else if (mouseEvent.getSource() == eight) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        } else if (mouseEvent.getSource() == nine) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        } else if (mouseEvent.getSource() == ten) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        } else if (mouseEvent.getSource() == eleven) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        } else if (mouseEvent.getSource() == twelve) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        } else if (mouseEvent.getSource() == thirteen) {
            openNewWindow(mouseEvent, "/home/fxml/Turbine/five.fxml");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void openNewWindow(ActionEvent event, String fxml) throws IOException {
        Parent screenToLoad = FXMLLoader.load(getClass().getResource(fxml));

        Stage stage = new Stage();
        stage.setScene(new Scene(screenToLoad));
        stage.show();
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
