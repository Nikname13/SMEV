package Presenter;

import Iteractor.IteractorInventoryNumber;
import Model.Equipment.EquipmentInventoryModel;
import Model.Inventory_number.InventoryNumberLog;
import Model.Inventory_number.InventoryNumberModel;
import Model.Supply.SupplyModel;
import Service.ListenersService;
import UI.Inventory_number.Controller.InventoryNumbersController;

import java.time.LocalDate;

public class InventoryNumberPresenter extends BasePresenter {

    private static InventoryNumberModel sInventoryNumberModel;
    private static InventoryNumberPresenter sInventoryNumberPresenter;

    private InventoryNumberPresenter() {
        ListenersService.get().addListenerData(this);
    }

    public static InventoryNumberPresenter get() {
        if (sInventoryNumberPresenter == null) {
            sInventoryNumberPresenter = new InventoryNumberPresenter();
        }
        return sInventoryNumberPresenter;
    }

    public InventoryNumberModel getInventoryNumberModel() {
        return sInventoryNumberModel;
    }

    public void setInventoryNumberModel(InventoryNumberModel inventoryNumberModel) {
        sInventoryNumberModel = inventoryNumberModel;
        setSelectedObject(inventoryNumberModel);
    }

    public void addInventoryNumber(String number, SupplyModel supply, boolean group, String description) {
        ListenersService.get().updateData(new IteractorInventoryNumber().addNew(new InventoryNumberModel(
                0,
                number,
                supply,
                group,
                description,
                new InventoryNumberLog(0, number, LocalDate.now(), supply.getName(), "First number")
        )));
        ListenersService.get().updateControl(InventoryNumberModel.class);
    }

    public void addInventoryNumberLog(String number, String base){
        sInventoryNumberModel.addEntity(new InventoryNumberLog(0, number, LocalDate.now(), sInventoryNumberModel.getSupply().getName(), base));
        sInventoryNumberModel.setName(number);
        editInventoryNumber(sInventoryNumberModel);
    }

    public void editInventoryNumber( SupplyModel supply, String description){
        if (new IteractorInventoryNumber().edit(new InventoryNumberModel(sInventoryNumberModel.getId(), sInventoryNumberModel.getName(), supply, sInventoryNumberModel.isGroup(), description)) != null) {
            update();
        }
    }

    public void editInventoryNumber(InventoryNumberModel inventoryNumberModel){
        if (new IteractorInventoryNumber().edit(inventoryNumberModel) != null) {
            update();
        }
    }

    private void update() {
        for (EquipmentInventoryModel equipmentInventoryModel : sInventoryNumberModel.getEquipmentInventoryList()) {
            ListenersService.get().updateData(equipmentInventoryModel);
        }
        ListenersService.get().refreshControl(InventoryNumberModel.class);
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sInventoryNumberModel)) {
                if (new IteractorInventoryNumber().delete(sInventoryNumberModel.getId())) {
                    ListenersService.get().updateUI(InventoryNumbersController.class);
                    ListenersService.get().updateUI(SupplyModel.class);
                }
            }
        }
    }
}
