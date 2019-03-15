package Presenter;

import Model.Area.AreaModel;
import Model.Area.Areas;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.EquipmentModel;
import Model.Equipment.Equipments;
import Model.Inventory_number.InventoryNumberModel;
import Model.Inventory_number.InventoryNumbers;
import Model.Location.LocationModel;
import Model.Location.Locations;
import Model.Movement.MovementModel;
import Model.Movement.Movements;
import Model.Parameter.ParameterModel;
import Model.Parameter.Parameters;
import Model.Post.PostModel;
import Model.Post.Posts;
import Model.Provider.ProviderModel;
import Model.Provider.Providers;
import Model.State.StateModel;
import Model.State.States;
import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import Model.Type.TypeModel;
import Model.Type.Types;
import Model.Worker.WorkerModel;
import Model.Worker.Workers;
import Service.ErrorService;
import Service.IUpdateData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class BasePresenter implements IUpdateData {

    private static Object sSelectedObject;
    private static String sTypeDocuments;
    public static Object getSelectedObject() {
        return sSelectedObject;
    }

    protected void setSelectedObject(Object selectedObject) {
        sSelectedObject = selectedObject;
    }


    public String getTypeDocuments() {
        return sTypeDocuments;
    }

    public void setTypeDocuments(String type) {
        sTypeDocuments = type;
    }

    void loadEntity(int id) {
        ErrorService.get().overrideError("loadEntity", this.getClass());
    }

    public ObservableList<AreaModel> getObservableArea() {
        return Areas.get().getObsEntityList();
    }

    public ObservableList<DepartmentModel> getObservableDepartment() {
        return Departments.get().getObsEntityList();
    }

    public ObservableList<WorkerModel> getObservableWorker() {
        return Workers.get().getObsEntityList();
    }

    public ObservableList<LocationModel> getObservableLocation() {
        return Locations.get().getObsEntityList();
    }

    public ObservableList<EquipmentModel> getObservableEquipment() {
        return Equipments.get().getObsEntityList();
    }

    public ObservableList<TypeModel> getObservableType() {
        return Types.get().getObsEntityList();
    }

    public ObservableList<StateModel> getObservableState() {
        return States.get().getObsEntityList();
    }

    public ObservableList<InventoryNumberModel> getObservableInventory() {
        return InventoryNumbers.get().getObsEntityList();
    }

    public ObservableList<InventoryNumberModel> getObservableAvailableInventory() {
        ObservableList<InventoryNumberModel> list=InventoryNumbers.get().getObsEntityList();
        ObservableList<InventoryNumberModel> availableList=FXCollections.observableArrayList();
        for(InventoryNumberModel inventoryNumber:list){
            if(!inventoryNumber.isGroup()){
                if(inventoryNumber.getEquipmentInventoryList().size()<1) {
                    availableList.add(inventoryNumber);
                }
            }else{
                availableList.add(inventoryNumber);
            }
        }
        return availableList;
    }

    public ObservableList<ParameterModel> getObservableParameter() {
        return Parameters.get().getObsEntityList();
    }

    public ObservableList<MovementModel> getObservableMovement() {
        return Movements.get().getObsEntityList();
    }

    public ObservableList<ProviderModel> getObservableProvider() {
        return Providers.get().getObsEntityList();
    }

    public ObservableList<SupplyModel> getObservableSupply() {
        return Supplys.get().getObsEntityList();
    }

    public ObservableList<PostModel> getObservablePost() {
        return Posts.get().getObsEntityList();
    }

    public boolean isNumber(String currentString) {
        return currentString.matches("^[1-9]+([0-9]+$)?");
    }


    @Override
    public void update(Object equipment) {
        ErrorService.get().overrideError("update", this.getClass());
    }

    @Override
    public void delete() {
        ErrorService.get().overrideError("delete", this.getClass());
    }
}
