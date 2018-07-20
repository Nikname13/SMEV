package Iteractor;

import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.Equipments;
import Net.Connector;
import Net.URLBuilder;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class IteractorEquipmentInventory extends GenericIteractor<EquipmentInventoryModel>{

    private static String sURL="/equipmentInventory_servlet";

    public IteractorEquipmentInventory() {
        super(sURL, EquipmentInventoryModel.class, new TypeToken<ArrayList<EquipmentInventoryModel>>(){}.getType());
    }

    @Override
    public String getGson(EquipmentInventoryModel entity) {
        return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(Expose.class)!=null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create().toJson(entity);
    }

    @Override
    public void setList(ObservableList<EquipmentInventoryModel> list) {
        super.setList(list);
    }

    @Override
    public void setEntity(EquipmentInventoryModel entity) {
        super.setEntity(entity);
    }

    @Override
    public void deleteEntity(EquipmentInventoryModel entity) {
        super.deleteEntity(entity);
    }
}
