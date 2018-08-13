package Model.Equipment;

import Model.AbstractModel;

import java.time.LocalDate;

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
}
