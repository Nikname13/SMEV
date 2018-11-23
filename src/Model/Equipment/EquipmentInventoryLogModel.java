package Model.Equipment;

import Model.AbstractModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class EquipmentInventoryLogModel extends AbstractModel {

    private int mInventoryId;
    private String mDescription;

    public EquipmentInventoryLogModel(int id, String inventoryNumber, int inventoryId, LocalDate date, String description) {
        super(id, inventoryNumber, date);
        mInventoryId = inventoryId;
        mDescription = description;
    }

    public EquipmentInventoryLogModel(int id, String name) {
        super(id, name);
    }

    public EquipmentInventoryLogModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new EquipmentInventoryLogModel(-1, name);
    }

    public int getInventoryId() {
        return mInventoryId;
    }

    public void setInventoryId(int inventoryId) {
        mInventoryId = inventoryId;
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

}
