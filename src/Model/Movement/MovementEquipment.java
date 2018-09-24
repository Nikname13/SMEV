package Model.Movement;

import Model.Equipment.EquipmentModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MovementEquipment extends EquipmentModel {
    private String mInventoryNumber, mType;
    private int mEquipmentId;

    public MovementEquipment(int id, String name, String inventoryNumber, String type, int equipmentId) {
        super(id, name);
        mInventoryNumber = inventoryNumber;
        mType = type;
        mEquipmentId = equipmentId;
    }

    public String getInventoryNumber() {
        return mInventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        mInventoryNumber = inventoryNumber;
    }

    public StringProperty inventoryNumberProperty() {
        return new SimpleStringProperty(mInventoryNumber);
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public StringProperty typeProperty() {
        return new SimpleStringProperty(mType);
    }

    public int getEquipmentId() {
        return mEquipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        mEquipmentId = equipmentId;
    }
}
