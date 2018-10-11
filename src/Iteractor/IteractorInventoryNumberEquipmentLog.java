package Iteractor;

import Model.Equipment.EquipmentInventoryLogModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class IteractorInventoryNumberEquipmentLog extends GenericIteractor<EquipmentInventoryLogModel> {

    private static String sURI = "/inventory_equipment_log_servlet";

    public IteractorInventoryNumberEquipmentLog() {
        super(sURI, EquipmentInventoryLogModel.class, new TypeToken<ArrayList<EquipmentInventoryLogModel>>() {
        }.getType());
    }

}
