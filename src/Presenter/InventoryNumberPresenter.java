package Presenter;

import Iteractor.IteractorInventoryNumber;
import Model.Inventory_number.InventoryNumberModel;
import Model.Inventory_number.InventoryNumbers;
import Model.Supply.SupplyModel;
import javafx.collections.ObservableList;

import java.util.Set;

public class InventoryNumberPresenter {

    private static InventoryNumberModel sInventoryNumberModel;

    public InventoryNumberModel getInventoryNumberModel() {
        return sInventoryNumberModel;
    }

    public void setInventoryNumberModel(InventoryNumberModel inventoryNumberModel) {
        sInventoryNumberModel = inventoryNumberModel;
    }

    public ObservableList<InventoryNumberModel> getObservableInventoryN(){
        return InventoryNumbers.get().getEntityList();
    }

    public void addInventoryNumber(String number, Object supply, boolean group, String description){
        new IteractorInventoryNumber().addNew(new InventoryNumberModel(
                0,
                number,
                (SupplyModel)supply,
                group,
                description
        ));
    }

    public void editInventoryNumber(String number, SupplyModel supply, boolean group, String description){
        new IteractorInventoryNumber().edit(new InventoryNumberModel(sInventoryNumberModel.getId(), number, supply, group, description));
    }

    public void delete(int id){
        new IteractorInventoryNumber().delete(id);
    }

    public void delete(Set<Integer> id){

    }
}
