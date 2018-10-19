package Presenter;

import Iteractor.IteractorInventoryNumber;
import Model.Equipment.EquipmentInventoryModel;
import Model.Inventory_number.InventoryNumberLog;
import Model.Inventory_number.InventoryNumberModel;
import Model.Inventory_number.InventoryNumbers;
import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import Service.IUpdateData;
import Service.ListenersService;

import java.time.LocalDate;
import java.util.Set;

public class InventoryNumberPresenter extends BasePresenter implements IUpdateData {

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
    }

    public void addInventoryNumber(String number, SupplyModel supply, boolean group, String description) {
        ListenersService.get().updateData(new IteractorInventoryNumber().addNew(new InventoryNumberModel(
                0,
                number,
                supply,
                group,
                description,
                new InventoryNumberLog(0, number, LocalDate.now(), supply.getName(), "Начало начал")
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

    public void delete(int id){
        new IteractorInventoryNumber().delete(id);
    }

    public void delete(Set<Integer> id){

    }

    @Override
    void loadEntity(int id) {

    }

    @Override
    public void update(Object entity) {
        if (entity.getClass().equals(SupplyModel.class)) {
            InventoryNumbers.get().update();
        }
        if (entity.getClass().equals(InventoryNumberModel.class)) {
            InventoryNumberModel inventoryNumberModel = (InventoryNumberModel) entity;
            SupplyModel supplyModel = Supplys.get().getEntity(inventoryNumberModel.getSupply().getId());
            if (supplyModel != null) {
                supplyModel.setEntityList(null);
            }
        }
    }

    @Override
    public void delete() {

    }
}
