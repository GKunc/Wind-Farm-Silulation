package home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ExceptionScreen {

   public ExceptionScreen(String msg) {
       Stage s = new Stage();

       Label b = new Label(msg);
       b.setTextAlignment(TextAlignment.CENTER);
       b.setLayoutX(80);
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

       Pane r = new Pane();
       r.getChildren().add(b);
       r.getChildren().add(ok);

       Scene sc = new Scene(r, 300, 200);
       s.setScene(sc);

       s.show();
   }
}
