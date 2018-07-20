package Presenter;

import Iteractor.IteractorEquipment;
import Iteractor.IteractorEquipmentInventory;
import Iteractor.IteractorMovement;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.*;
import Model.Inventory_number.InventoryNumberModel;
import Model.Inventory_number.InventoryNumbers;
import Model.Movement.MovementModel;
import Model.State.StateModel;
import Model.State.States;
import Model.Type.TypeModel;
import Model.Type.Types;
import Model.Worker.WorkerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class EquipmentPresenter {

    private static EquipmentModel sEquipmentModel;
    private static EquipmentInventoryModel sEquipmentInventoryModel;
    private static StateModel sStateModel;
    private static EquipmentStateModel mEquipmentState;
    private static EquipmentPresenter sEquipmentPresenter;

    public static EquipmentPresenter get(){
        if(sEquipmentPresenter==null){
            sEquipmentPresenter=new EquipmentPresenter();
        }
        return sEquipmentPresenter;
    }

    private EquipmentPresenter(){}

    public EquipmentModel getEquipmentModel(){
        return sEquipmentModel;
    }

    public void setEquipmentModel(Object equipmentModel){
        sEquipmentModel =(EquipmentModel) equipmentModel;
    }

    public EquipmentInventoryModel getEquipmentInventoryModel() {
        return sEquipmentInventoryModel;
    }

    public void setEquipmentInventoryModel(Object equipmentInventoryModel) {
        sEquipmentInventoryModel = (EquipmentInventoryModel) equipmentInventoryModel;
    }

    public StateModel getStateModel() {
        return sStateModel;
    }

    public void setStateModel(Object stateModel) {
        sStateModel =(StateModel)stateModel;
    }

    public ObservableList<EquipmentModel> getObservableEquipment(){
      return   Equipments.get().getEntityList();
    }

    public ObservableList<TypeModel> getObservableType(){
        return Types.get().getEntityList();
    }

    public ObservableList<StateModel> getObservableState(){
        return States.get().getEntityList();
    }

    public ObservableList<InventoryNumberModel> getObservableInventory(){
        return InventoryNumbers.get().getEntityList();
    }

    public ObservableList<Model.Department.DepartmentModel> getObservableDepartment(){
        return Departments.get().getEntityList();
    }

    public void addEquipment(String name, String nameFact, String description, String config, Object typeModel,List<EquipmentParameterModel> values){
        new IteractorEquipment().addNew(new EquipmentModel(
                0,
                name,
                nameFact,
                description,
                config,
                (TypeModel)typeModel,
                values,
                null
                ));
    }

    public void editEquipment(String name, String nameFact, String description, String config, Object typeModel,List<EquipmentParameterModel> values){
        new IteractorEquipment().edit(new EquipmentModel(
                sEquipmentModel.getId(),
                name,
                nameFact,
                description,
                config,
                (TypeModel)typeModel,
                values,
                null
        ));
    }

    public void addEquipmentInventory(InventoryNumberModel inventoryNumber, int guaranty, String description, String photos,
                                      DepartmentModel departmentModel, EquipmentStateModel equipmentState, EquipmentModel equipmentModel){
        new IteractorEquipmentInventory().addNew(new EquipmentInventoryModel(0, inventoryNumber, guaranty, description, photos, Departments.get().getEntity(5), equipmentState, equipmentModel));
    }

    public void editEquipmentInventory(InventoryNumberModel inventoryNumber, int guaranty, String description, String photos,
                                       DepartmentModel departmentModel, EquipmentStateModel equipmentState, EquipmentModel equipmentModel){
        new IteractorEquipmentInventory().edit(new EquipmentInventoryModel(sEquipmentInventoryModel.getId(), inventoryNumber, guaranty,
                description, photos, departmentModel, equipmentState, equipmentModel));
    }

    public void moveEquipmentInventory(EquipmentInventoryModel equipment, DepartmentModel toDepartment,WorkerModel fromWorker, WorkerModel toWorker, String base){

        MovementModel movement=new MovementModel(0, LocalDate.now(), base);
        movement.addDepartment(equipment.getDepartmentModel());
        movement.addDepartment(toDepartment);
        equipment.setDepartmentModel(toDepartment);
        movement.addWorker(fromWorker);
        movement.addWorker(toWorker);
        movement.addEntity(equipment);
        new IteractorMovement().addNew(movement);
        new IteractorEquipmentInventory().edit(equipment);
    }

    public void deleteEquipment(){
    }

    public void deleteEquipmentInventory(EquipmentInventoryModel eq_inv){
        new IteractorEquipmentInventory().delete(eq_inv);
    }

    public void addEquipmentState(String description, LocalDate date){
        mEquipmentState=new EquipmentStateModel(0,description,sStateModel,date);
    }

    public EquipmentStateModel getEquipmentState() {
        return mEquipmentState;
    }

    public void addMovement(LocalDate date, String base, Object fromDepartment, Object toDepartment, Object fromWorker, Object toWorker, Object equipment) {

    }

}
