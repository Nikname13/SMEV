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
import Model.Parameter.ParameterModel;
import Model.Parameter.Parameters;
import Model.State.StateModel;
import Model.State.States;
import Model.Type.TypeModel;
import Model.Type.Types;
import Model.Worker.WorkerModel;
import Service.IUpdateData;
import Service.LisenersService;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class EquipmentPresenter extends BasePresenter implements IUpdateData {

    private static EquipmentModel sEquipmentModel;
    private static EquipmentInventoryModel sEquipmentInventoryModel;
    private static StateModel sStateModel;
    private static InventoryNumberModel sInventoryNumberModel;
    private static EquipmentStateLogModel sEquipmentStateLog;
    private static EquipmentInventoryLogModel sEquipmentInventoryLogModel;
    private static EquipmentPresenter sEquipmentPresenter;
    private static DepartmentModel sDepartmentModel;

    private EquipmentPresenter() {
        LisenersService.get().addListenerData(this);
    }

    public static EquipmentPresenter get() {
        if (sEquipmentPresenter == null) {
            sEquipmentPresenter = new EquipmentPresenter();
        }
        return sEquipmentPresenter;
    }

    public DepartmentModel getDepartmentModel() {
        return sDepartmentModel;
    }

    public void setDepartmentModel(DepartmentModel departmentModel) {
        sDepartmentModel = departmentModel;
    }

    public EquipmentModel getEquipmentModel() {
        return sEquipmentModel;
    }

    public void setEquipmentModel(Object equipmentModel) {
        sEquipmentModel = (EquipmentModel) equipmentModel;
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
        sStateModel = (StateModel) stateModel;
    }

    public InventoryNumberModel getInventoryNumberModel() {
        return sInventoryNumberModel;
    }

    public void setInventoryNumberModel(InventoryNumberModel inventoryNumberModel) {
        sInventoryNumberModel = inventoryNumberModel;
    }

    public ObservableList<EquipmentModel> getObservableEquipment() {
        return Equipments.get().getEntityList();
    }

    public ObservableList<TypeModel> getObservableType() {
        return Types.get().getEntityList();
    }

    public ObservableList<StateModel> getObservableState() {
        return States.get().getEntityList();
    }

    public ObservableList<InventoryNumberModel> getObservableInventory() {
        return InventoryNumbers.get().getEntityList();
    }

    public ObservableList<Model.Department.DepartmentModel> getObservableDepartment() {
        return Departments.get().getEntityList();
    }

    public ObservableList<ParameterModel> getObservableParameter() {
        return Parameters.get().getEntityList();
    }

    public void addEquipment(String name, String nameFact, String description, Object typeModel, List<EquipmentParameterModel> values) {
        new IteractorEquipment().addNew(new EquipmentModel(
                0,
                name,
                nameFact,
                description,
                (TypeModel) typeModel,
                values,
                null
        ));
    }

    public void editEquipment(String name, String nameFact, String description, List<EquipmentParameterModel> values) {
        new IteractorEquipment().edit(new EquipmentModel(
                sEquipmentModel.getId(),
                name,
                nameFact,
                description,
                sEquipmentModel.getTypeModel(),
                values,
                null
        ));
    }

    public void editEquipment(EquipmentModel equipment) {
        new IteractorEquipment().edit(equipment);
        //LisenersService.get().refreshControl(EquipmentModel.class);
    }


    public void addEquipmentInventory(InventoryNumberModel inventoryNumber, int guaranty, String description,
                                      EquipmentStateLogModel equipmentState, EquipmentModel equipmentModel, StateModel state) {

        EquipmentInventoryModel equipment = new IteractorEquipmentInventory().addNew(new EquipmentInventoryModel(0, inventoryNumber, guaranty, description,
                Departments.get().getEntity(1), equipmentState, equipmentModel, state));
/*       equipment.addInvenotryEditLog(new EquipmentInventoryLogModel(0, equipment.getInventoryNumber().getName(),
               equipment.getInventoryNumber().getId(), LocalDate.now(), "Первый номер"));*/

    }

    public void editEquipmentInventory(InventoryNumberModel inventoryNumber, int guaranty, String description,
                                       DepartmentModel departmentModel, EquipmentStateLogModel equipmentState, EquipmentModel equipmentModel, StateModel state) {
        LisenersService.get().updateData(new IteractorEquipmentInventory().edit(new EquipmentInventoryModel(sEquipmentInventoryModel.getId(), inventoryNumber, guaranty,
                description, departmentModel, equipmentState, equipmentModel, state)));
        LisenersService.get().refreshControl(DepartmentModel.class);
    }

    public void editEquipmentInventory(EquipmentInventoryModel equipment) {
        LisenersService.get().updateData(new IteractorEquipmentInventory().edit(equipment));
        LisenersService.get().refreshControl(EquipmentInventoryModel.class);
    }


    public void moveEquipmentInventory(EquipmentInventoryModel equipment, DepartmentModel toDepartment, WorkerModel fromWorker, WorkerModel toWorker, String base) {

        Departments.get().getEntity(toDepartment.getId()).setLoad(false);
        Departments.get().getEntity(equipment.getDepartmentModel().getId()).setLoad(false);
        Equipments.get().getEntity(equipment.getEquipmentModel().getId()).setLoad(false);
        MovementModel movement = new MovementModel(0, LocalDate.now(), base);
        movement.addDepartment(equipment.getDepartmentModel());
        movement.addDepartment(toDepartment);
        equipment.setDepartmentModel(toDepartment);
        movement.addWorker(fromWorker);
        movement.addWorker(toWorker);
        movement.addEntity(equipment);
        new IteractorMovement().addNew(movement);
        new IteractorEquipmentInventory().edit(equipment);
        LisenersService.get().updateControl(EquipmentInventoryModel.class);
    }

    public void deleteEquipment() {
    }

    public void deleteEquipmentInventory(EquipmentInventoryModel eq_inv) {
        System.out.println("delete " + eq_inv.getInventoryNumber().getName());
        sEquipmentInventoryModel = null;
        //new IteractorEquipmentInventory().delete(eq_inv);
    }

    public void addEquipmentStateLog(String state, String description, LocalDate date) {
        sEquipmentStateLog = new EquipmentStateLogModel(0, state, description, date);
        sEquipmentInventoryModel.setStateModel(sStateModel);
        sEquipmentInventoryModel.addEntity(sEquipmentStateLog);
        editEquipmentInventory(sEquipmentInventoryModel);
        LisenersService.get().refreshControl(EquipmentInventoryModel.class);
    }

    public EquipmentStateLogModel getEquipmentStateLog() {
        return sEquipmentStateLog;
    }

    public void setEquipmentStateLog(EquipmentStateLogModel equipmentState) {
        sEquipmentStateLog = equipmentState;
    }

    public void addEquipmentInventoryLogModel(String description, LocalDate date) {
        sEquipmentInventoryLogModel = new EquipmentInventoryLogModel(0, sInventoryNumberModel.getName(), sInventoryNumberModel.getId(), date, description);
        sEquipmentInventoryModel.setInventoryNumber(sInventoryNumberModel);
        sEquipmentInventoryModel.addInvenotryEditLog(sEquipmentInventoryLogModel);
        editEquipmentInventory(sEquipmentInventoryModel);
        LisenersService.get().refreshControl(EquipmentInventoryLogModel.class);
    }

    public EquipmentInventoryLogModel getEquipmentInventoryLogModel() {
        return sEquipmentInventoryLogModel;
    }

    public void setEquipmentInventoryLogModel(EquipmentInventoryLogModel equipmentInventoryLogModel) {
        sEquipmentInventoryLogModel = equipmentInventoryLogModel;
    }

    public void addMovement(LocalDate date, String base, Object fromDepartment, Object toDepartment, Object fromWorker, Object toWorker, Object equipment) {

    }

    @Override
    public void update(Object object) {
        if (object.getClass().equals(EquipmentInventoryModel.class)) {
            EquipmentInventoryModel equipment = (EquipmentInventoryModel) object;
            EquipmentModel equipmentModel = Equipments.get().getEntity(equipment.getEquipmentModel().getId());
            if (equipmentModel != null) {
                equipmentModel.setLoad(false);
            }
        }
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sEquipmentInventoryModel)) {
                System.out.println("delete " + sEquipmentInventoryModel.getInventoryNumber().getName());
            }
        }
    }

    public void cancel() {
        LisenersService.get().refreshControl(EquipmentInventoryModel.class);
    }
}
