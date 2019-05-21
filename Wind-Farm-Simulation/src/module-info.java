module Wind.Farm {
    requires jdk.incubator.httpclient;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires com.jfoenix;

    exports home.Controllers.numberBtnsController;
    exports home;
    exports home.Controllers;
    exports home.Agents;
}