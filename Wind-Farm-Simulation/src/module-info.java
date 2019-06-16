module Wind.Farm {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires com.jfoenix;
    requires okhttp3;
    requires okio;

    exports home;
    exports home.Controllers;
    exports home.Agents;
}