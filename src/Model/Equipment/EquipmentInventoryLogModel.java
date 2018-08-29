package Model.Equipment;

import Model.AbstractModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EquipmentInventoryLogModel extends AbstractModel {

    private int mInventoryId;
    private LocalDate mDate;
    private String mDescription;

    public EquipmentInventoryLogModel(int id, String inventoryNumber, int inventoryId, LocalDate date, String description) {
        super(id, inventoryNumber);
        mInventoryId = inventoryId;
        mDate = date;
        mDescription = description;
    }

    public EquipmentInventoryLogModel(int id, String name) {
        super(id, name);
    }

    public int getInventoryId() {
        return mInventoryId;
    }

    public void setInventoryId(int inventoryId) {
        mInventoryId = inventoryId;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return new SimpleObjectProperty<>(mDate);
    }

    public StringProperty dateToString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return new SimpleStringProperty((mDate).format(format));
    }
}
