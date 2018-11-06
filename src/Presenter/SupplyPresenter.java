package Presenter;

import Iteractor.IteractorSupply;
import Model.Inventory_number.InventoryNumberLog;
import Model.Inventory_number.InventoryNumberModel;
import Model.Provider.ProviderModel;
import Model.Supply.SupplyModel;
import Service.IUpdateData;
import Service.ListenersService;
import UI.Supply.Controller.SupplysController;

import java.time.LocalDate;
import java.util.List;

public class SupplyPresenter extends BasePresenter implements IUpdateData {

    private static SupplyModel sSupplyModel;
    private static SupplyPresenter sSupplyPresenter;

    private SupplyPresenter() {
        ListenersService.get().addListenerData(this);
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
        setSelectedObject(supplyModel);
    }


    public void addSupply(String number, String typeSupply, LocalDate dateSupply, List<InventoryNumberModel> inventoryList, String description, ProviderModel provider) {
        for(InventoryNumberModel inventoryNumber:inventoryList){
            inventoryNumber.addEntity(new InventoryNumberLog(0, inventoryNumber.getName(), LocalDate.now(), number, "Начало начал"));
        }
        ListenersService.get().updateData(new IteractorSupply().addNew(new SupplyModel(0, number, typeSupply, dateSupply, inventoryList, description, provider)));
        ListenersService.get().updateUI(SupplysController.class);

    }

    public void editSupply(String number, String typeSupply, LocalDate dateSupply, String description, ProviderModel provider) {
        new IteractorSupply().edit(new SupplyModel(sSupplyModel.getId(), number, typeSupply, dateSupply, null, description, provider));
        ListenersService.get().refreshControl(SupplyModel.class);
        if (sSupplyModel.getProviderModel().getId() != provider.getId()) {
            ListenersService.get().updateControl(SupplyModel.class);
        }
    }

    @Override
    public void loadEntity(int id) {

    }

    @Override
    public void update(Object entity) {
/*        if (entity.getClass().equals(ProviderModel.class)) {
            ProviderModel provider = (ProviderModel) entity;
            for (SupplyModel supply : Supplys.get().getObsEntityList()) {
                if (supply.getProviderModel().getId() == provider.getId()) {
                    supply.setProviderModel(provider);
                    break;
                }
            }
            ListenersService.get().updateUI(SupplysController.class);
        }*/
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sSupplyModel)) {
                if (new IteractorSupply().delete(sSupplyModel.getId())) {
                    ListenersService.get().updateControl(SupplyModel.class);
                }
            }
        }
    }
}
