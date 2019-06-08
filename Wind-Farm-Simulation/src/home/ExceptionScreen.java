package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ExceptionScreen {

   public ExceptionScreen(String msg) {
       Stage s = new Stage();

       Label b = new Label(msg);
       b.setMaxWidth(Double.MAX_VALUE);
       b.setTextAlignment(TextAlignment.CENTER);
       b.setAlignment(Pos.CENTER);
       //b.setLayoutX(80);
       b.setLayoutY(80);

       Button ok = new Button("ok");
       ok.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent e) {
               Stage stage = (Stage) s.getScene().getWindow();
               stage.close();
           }
       });

       ok.prefWidth(150);
       ok.setMinWidth(150);
       ok.setLayoutX(80);
       ok.setLayoutY(110);

       VBox vBox = new VBox(10, b);
       vBox.setAlignment(Pos.CENTER);
       vBox.getChildren().add(ok);


       Scene sc = new Scene(vBox, 300, 200);
       s.setScene(sc);

       s.show();
   }
}
