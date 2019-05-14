module Wind.Farm {
    requires jdk.incubator.httpclient;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports home;
    exports home.Agents;
    exports home.Controllers;
}