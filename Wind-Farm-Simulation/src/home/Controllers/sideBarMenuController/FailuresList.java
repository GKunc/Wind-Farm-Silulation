package home.Controllers.sideBarMenuController;

import home.Agents.FailuresInfo;
import home.Agents.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FailuresList  implements Initializable {

    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button btnDashBoard;
    public Button optionsBtn;
    public Button summaryBtn;
    public Button failuresBtn;
    public Button weatherBtn;
    public Button realTimeBtn;

    public TableView<FailuresInfo> failuresTable;

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
        try {
            TableColumn turbineNo = new TableColumn<FailuresInfo,Integer>("Nr turbiny");
            turbineNo.setPrefWidth(150);
            TableColumn failureDescription = new TableColumn<FailuresInfo,String>("Opis awarii");
            failureDescription.setPrefWidth(200);
            TableColumn failureTime = new TableColumn<FailuresInfo,String>("Czas trwania");
            failureTime.setPrefWidth(150);

            failureTime.setCellValueFactory(
                    new PropertyValueFactory<FailuresInfo, Integer>("time"));

            turbineNo.setCellValueFactory(
                    new PropertyValueFactory<FailuresInfo, String>("turbineNo"));

            failureDescription.setCellValueFactory(
                    new PropertyValueFactory<FailuresInfo, String>("description"));

            //failuresTable.setItems(data);
            failuresTable.getColumns().addAll(turbineNo, failureDescription, failureTime);

            final ObservableList<FailuresInfo> data =
                    FXCollections.observableArrayList(
                            Main.getListOfFailures()
                    );
            failuresTable.setItems(data);

        } catch (Exception e) {
            //e.printStackTrace();
            // todo
            // dodac otwerajace sie okno aby wybrac opcje wymulacji
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
