package Service;

import Model.Equipment.EquipmentInventoryModel;

public interface IUpdateData {
    void updateEquipment(EquipmentInventoryModel equipment);

    void delete();
}
