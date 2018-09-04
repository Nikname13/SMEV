package Iteractor;

import Model.Equipment.EquipmentParameterModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class IteractorEquipmentParameter extends GenericIteractor<EquipmentParameterModel> {

    private static String sURI = "/equipmentParameter_servlet";

    public IteractorEquipmentParameter() {
        super(sURI, EquipmentParameterModel.class, new TypeToken<ArrayList<EquipmentParameterModel>>() {
        }.getType());
    }

}
