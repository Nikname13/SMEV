package Model.Movement;

import Model.Equipment.EquipmentModel;

public class MovementEquipment extends EquipmentModel {
    private String mInventoryNumber;
    private int mEquipmentId;

    public MovementEquipment(int id, String name, String inventoryNumber, int equipmentId) {
        super(id, name);
        mInventoryNumber = inventoryNumber;
        mEquipmentId = equipmentId;
    }

    public String getInventoryNumber() {
        return mInventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        mInventoryNumber = inventoryNumber;
    }

    public int getEquipmentId() {
        return mEquipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        mEquipmentId = equipmentId;
    }
}
