package Iteractor;

import Model.Equipment.EquipmentInventoryLogModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class IteractorInventoryLog extends GenericIteractor<EquipmentInventoryLogModel> {

    private static String sURI = "/inventory_log_servlet";

    public IteractorInventoryLog() {
        super(sURI, EquipmentInventoryLogModel.class, new TypeToken<ArrayList<EquipmentInventoryLogModel>>() {
        }.getType());
    }

}
