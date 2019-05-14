module Wind.Farm {
    requires jdk.incubator.httpclient;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports home;
    exports home.Agents;
    exports home.Controllers;
}