package Iteractor;

import Model.Inventory_number.InventoryNumberModel;
import Model.Inventory_number.InventoryNumbers;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IteractorInventoryNumber extends GenericIteractor<InventoryNumberModel> {

    private static String sURL="/inventoryNumber_servlet";

    public IteractorInventoryNumber() {
        super(sURL,InventoryNumberModel.class, new TypeToken<ArrayList<InventoryNumberModel>>(){}.getType());
    }

    @Override
    public void setList(ObservableList<InventoryNumberModel> list) {
        Collections.sort(list, Comparator.comparing(InventoryNumberModel::getNameToLowerCase));
        InventoryNumbers.get().setEntityList(list);
    }

    @Override
    public void setEntity(InventoryNumberModel entity) {
        if(InventoryNumbers.get().getEntity(entity.getId())!=null){
            InventoryNumbers.get().replace(entity);
        }else{
            InventoryNumbers.get().addEntity(entity);
        }
    }

    @Override
    public void deleteEntity(int id) {
       InventoryNumbers.get().deleteEntity(id);
    }
}
