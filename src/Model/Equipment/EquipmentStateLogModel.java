package Model.Equipment;

import Model.AbstractModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EquipmentStateLogModel extends AbstractModel {

    private String mDescription;
    private LocalDate mDate;

    public EquipmentStateLogModel(int id, String state, String description, LocalDate date) {
        super(id, state);
        mDescription = description;
        mDate = date;
    }

    public EquipmentStateLogModel(int id, String state) {
        super(id, state);
    }

    public String getDescription() {
        return mDescription;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate date) {
        mDate = date;
    }

    public StringProperty dateToString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return new SimpleStringProperty((mDate).format(format));
    }
}
