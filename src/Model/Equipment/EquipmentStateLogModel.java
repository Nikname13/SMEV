package Model.Equipment;

import Model.AbstractModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class EquipmentStateLogModel extends AbstractModel {

    private String mDescription;

    public EquipmentStateLogModel(int id, String state, String description, LocalDate date) {
        super(id, state, date);
        mDescription = description;
    }

    public EquipmentStateLogModel(int id, String state) {
        super(id, state);
    }

    public EquipmentStateLogModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new EquipmentStateLogModel(-1, name);
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
}
