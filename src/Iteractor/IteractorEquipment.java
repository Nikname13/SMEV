package Iteractor;

import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.Equipments;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

public class IteractorEquipment extends GenericIteractor<EquipmentModel>{

    private static String sURL ="/equipment_servlet";
    private static String sLoadFileURL = "/load_equipment_servlet";

    public IteractorEquipment() {
        super(sURL, EquipmentModel.class, new TypeToken<ArrayList<EquipmentModel>>() {}.getType());
    }

    @Override
    public void setList(ObservableList<EquipmentModel> list) {
        Equipments.get().setEntityList(list, Comparator.comparing(EquipmentModel::getNameToLowerCase));
    }

    @Override
    public void setEntity(EquipmentModel entity) {
        entity.setLoad(true);
        for(EquipmentInventoryModel eq_inv : entity.getEquipmentInventoryList()){
            eq_inv.setEquipmentModel(entity);
        }
        if(Equipments.get().getEntity(entity.getId())!=null){
            Equipments.get().replace(entity);
        }else{
            Equipments.get().addEntity(entity, Comparator.comparing(EquipmentModel::getNameToLowerCase));
        }
    }

    @Override
    public String getLoadFileURL() {
        return sLoadFileURL;
    }

    @Override
    public void deleteEntity(int id) {
        Equipments.get().deleteEntity(id);
    }
}
