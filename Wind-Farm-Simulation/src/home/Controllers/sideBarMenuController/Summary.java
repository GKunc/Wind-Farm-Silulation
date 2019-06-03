package home.Controllers.sideBarMenuController;

import home.Agents.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

    public Label turbiny_wydatki_txt;
    public Label naprawy_wydatki_txt;
    public Label inne_wydatki_txt;
    public Label zarobki_txt;
    public Label suma_txt;
    public Label turbiny_txt;
    public Label wiatr_txt;

    public Button zarobki_wykres;

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
        else if (mouseEvent.getSource() == zarobki_wykres) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // todo
        // zainicjalizowac danymi z ekranu opcji symulacji


        turbiny_wydatki_txt.setText("" + Main.turbineExpenses);
        naprawy_wydatki_txt.setText("" + Main.failuresExpenses);
        inne_wydatki_txt.setText("" + Main.otherExpenses);
        zarobki_txt.setText("" + Main.earnings);
        suma_txt.setText("" + Main.total);

        turbiny_txt.setText("" + Main.numberOfTurbines);
        wiatr_txt.setText("" + (double)Math.round(Main.averageWind * 100000d) / 100000d);

        try {
           // windowConsole.appendText(Main.showSimulationResults(new String[]{"fromApi", Main.numberOfTurbines, Main.cityName, Main.startDate, Main.endDate}));
        } catch (Exception e) {
            // e.printStackTrace();
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
