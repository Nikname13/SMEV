package Presenter;

import Iteractor.IteractorEquipment;
import Iteractor.IteractorEquipmentInventory;
import Iteractor.IteractorEquipmentParameter;
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
import UI.MainTabs.EquipmentTabController;
import javafx.collections.FXCollections;
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

    public ObservableList<ParameterModel> getObservableEquipmentParameter(List<EquipmentParameterModel> equipmentParameter) {
        List<ParameterModel> list = getObservableParameter();
        ObservableList<ParameterModel> obsList = FXCollections.observableArrayList();
        System.out.println("getObsEquipParameter");
        boolean flag = false;
        for (ParameterModel parameter : list) {
            for (EquipmentParameterModel eqParameter : equipmentParameter) {
                if (parameter.getId() == eqParameter.getParameterModel().getId()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                obsList.add(parameter);
            }
            flag = false;
        }
        return obsList;
    }

    public void addEquipment(String name, String nameFact, String description, Object typeModel, List<EquipmentParameterModel> values) {
        LisenersService.get().updateControl(EquipmentModel.class, new IteractorEquipment().addNew(new EquipmentModel(
                0,
                name,
                nameFact,
                description,
                (TypeModel) typeModel,
                values,
                null
        )));
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
        LisenersService.get().updateControl(EquipmentModel.class);
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

/*        Departments.get().getEntity(toDepartment.getId()).setLoad(false);
        Departments.get().getEntity(equipment.getDepartmentModel().getId()).setLoad(false);
        Equipments.get().getEntity(equipment.getEquipmentModel().getId()).setLoad(false);*/
        MovementModel movement = new MovementModel(0, LocalDate.now(), base);
        movement.addDepartment(movement.newMovementDepartment(equipment.getDepartmentModel()));
        movement.addDepartment(movement.newMovementDepartment(toDepartment));
        equipment.setDepartmentModel(toDepartment);//назначение нового отдела дла оборудования
        movement.addWorker(movement.newMovementWorker(fromWorker));
        movement.addWorker(movement.newMovementWorker(toWorker));
        movement.addEntity(movement.newMovementEquipment(equipment));
        new IteractorMovement().addNew(movement);
        new IteractorEquipmentInventory().edit(equipment);
        LisenersService.get().updateControl(EquipmentInventoryModel.class);
    }

    public void deleteEquipment() {
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
            if (getSelectedObject().equals(sEquipmentModel)) {
                System.out.println("delete equipment");
                if (new IteractorEquipment().delete(sEquipmentModel.getId())) {
                    LisenersService.get().updateControl(EquipmentModel.class);
                    LisenersService.get().updateUI(EquipmentTabController.class);
                }
            }
        }
    }

    public void cancel() {
        LisenersService.get().refreshControl(EquipmentInventoryModel.class);
    }

    public void deleteEquipmentParameter(EquipmentParameterModel equipmentParameter) {
        System.out.println("equipmentParameterId " + equipmentParameter.getId());
        new IteractorEquipmentParameter().delete(equipmentParameter.getId());
        Equipments.get().getEntity(sEquipmentModel.getId()).getEntityList().remove(equipmentParameter);
    }

    @Override
    public void loadEntity(int id) {
        if (!sEquipmentModel.isLoad()) {
            new IteractorEquipment().loadData(id);
        }
    }
}
