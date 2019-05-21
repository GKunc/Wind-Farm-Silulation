package home.Agents;

import javafx.beans.property.SimpleStringProperty;

public class FailuresInfo {
    private SimpleStringProperty turbineNo;
    private SimpleStringProperty  description;
    private SimpleStringProperty  time;


    public FailuresInfo(Integer turbineNo, String description, String time) {
        this.turbineNo = new SimpleStringProperty(turbineNo.toString());
        this.description = new SimpleStringProperty(description);
        this.time = new SimpleStringProperty(time);
    }

    public String getTurbineNo() {
        return turbineNo.get();
    }

    public SimpleStringProperty turbineNoProperty() {
        return turbineNo;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }
}
