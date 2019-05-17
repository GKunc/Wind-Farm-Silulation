module Wind.Farm {
    requires jdk.incubator.httpclient;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;

    exports home;
    exports home.Agents;
    exports home.Controllers;
    exports home.Controllers.numberBtnsController;

}