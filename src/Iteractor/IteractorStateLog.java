package Iteractor;

import Model.Equipment.EquipmentStateLogModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class IteractorStateLog extends GenericIteractor<EquipmentStateLogModel> {

    private static String sURI = "/state_log_servlet";

    public IteractorStateLog() {
        super(sURI, EquipmentStateLogModel.class, new TypeToken<ArrayList<EquipmentStateLogModel>>() {
        }.getType());
    }

}
