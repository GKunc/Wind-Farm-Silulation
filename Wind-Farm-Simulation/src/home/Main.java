package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

        @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/DashBoard.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Model farmy wiatrowej");
        stage.setScene(scene);
        stage.show();
    }
//    @Override
//    public void start(Stage primaryStage) {
//        Parent root = null;
//        try {
//            //root = FXMLLoader.load(getClass().getResource("guiWindow.fxml"));
//            root = FXMLLoader.load(getClass().getResource("finalGUIWindow.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Scene scene = new Scene(root, 900, 600);
//        scene.getStylesheets().add(getClass().getResource("GUIStyle.css").toExternalForm());
//        primaryStage.setTitle("Model farmy wiatrowej");
////        primaryStage.setScene(new Scene(root));
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}

