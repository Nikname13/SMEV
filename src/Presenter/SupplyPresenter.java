package Presenter;

import Iteractor.IteractorSupply;
import Model.Inventory_number.InventoryNumberModel;
import Model.Provider.ProviderModel;
import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import Service.IUpdateData;
import Service.ListenersService;

import java.time.LocalDate;
import java.util.List;

public class SupplyPresenter extends BasePresenter implements IUpdateData {

    private static SupplyModel sSupplyModel;
    private static SupplyPresenter sSupplyPresenter;

    private SupplyPresenter() {
    }

    public static SupplyPresenter get() {
        if (sSupplyPresenter == null) sSupplyPresenter = new SupplyPresenter();
        return sSupplyPresenter;
    }

    public SupplyModel getSupplyModel() {
        return sSupplyModel;
    }

    public void setSupplyModel(SupplyModel supplyModel) {
        sSupplyModel = supplyModel;
    }


    public void addSupply(String number, String typeSupply, LocalDate dateSupply, List<InventoryNumberModel> inventoryList, String description, ProviderModel provider) {
        ListenersService.get().updateData(new IteractorSupply().addNew(new SupplyModel(0, number, typeSupply, dateSupply, inventoryList, description, provider)));
        ListenersService.get().updateUI(SupplyModel.class);

    }

    public void editSupply(String number, String typeSupply, LocalDate dateSupply, String description, ProviderModel provider) {
        new IteractorSupply().edit(new SupplyModel(sSupplyModel.getId(), number, typeSupply, dateSupply, null, description, provider));
    }

    public void deleteSupply(int id){
        new IteractorSupply().delete(id);
    }

    public void update(){
        Supplys.get().update();
    }

    @Override
    public void loadEntity(int id) {

    }

    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {

    }
}
