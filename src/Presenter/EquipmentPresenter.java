package Presenter;

import Iteractor.IteractorEquipment;
import Iteractor.IteractorEquipmentInventory;
import Iteractor.IteractorEquipmentParameter;
import Iteractor.IteractorMovement;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.*;
import Model.Inventory_number.InventoryNumberModel;
import Model.Movement.MovementModel;
import Model.Parameter.ParameterModel;
import Model.State.StateModel;
import Model.Type.TypeModel;
import Service.IUpdateData;
import Service.ListenersService;
import UI.MainTabs.Controller.EquipmentTabController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
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
        ListenersService.get().addListenerData(this);
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

    public ObservableList<ParameterModel> getObservableEquipmentParameter(List<EquipmentParameterModel> equipmentParameter) {
        ObservableList<ParameterModel> obsList = FXCollections.observableArrayList();
        System.out.println("getObsEquipParameter");
        boolean flag = false;
        for (ParameterModel parameter : getObservableParameter()) {
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
        ListenersService.get().updateControl(EquipmentModel.class, new IteractorEquipment().addNew(new EquipmentModel(
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
        ListenersService.get().updateControl(EquipmentModel.class);
    }

    public void editEquipment(EquipmentModel equipment) {
        new IteractorEquipment().edit(equipment);
        //ListenersService.get().refreshControl(EquipmentModel.class);
    }


    public void addEquipmentInventory(InventoryNumberModel inventoryNumber, int guaranty, String description, EquipmentModel equipmentModel, StateModel state, int count) {
        List<EquipmentInventoryModel> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            EquipmentInventoryModel equip = new EquipmentInventoryModel(0, inventoryNumber, guaranty, description,
                    Departments.get().getEntity(1), equipmentModel, state);
            equip.addFirstInventoryNumber();
            list.add(equip);
        }
        EquipmentInventoryModel equipment = new IteractorEquipmentInventory().addNew(list).iterator().next();
        ListenersService.get().updateData(equipment);
        ListenersService.get().updateControl(EquipmentInventoryModel.class);
    }

    public void editEquipmentInventory(InventoryNumberModel inventoryNumber, int guaranty, String description,
                                       DepartmentModel departmentModel, EquipmentModel equipmentModel, StateModel state) {
        /*      */
        EquipmentInventoryModel newEquipment = new IteractorEquipmentInventory().edit(new EquipmentInventoryModel(sEquipmentInventoryModel.getId(), inventoryNumber, guaranty,
                description, departmentModel, equipmentModel, state));
        if (newEquipment != null) {
            //ListenersService.get().updateData(newEquipment);
            sEquipmentInventoryModel.setInventoryNumber(inventoryNumber);
            sEquipmentInventoryModel.setGuaranty(guaranty);
            sEquipmentInventoryModel.setDescription(description);
            sEquipmentInventoryModel.setDepartmentModel(departmentModel);
            sEquipmentInventoryModel.setEquipmentModel(equipmentModel);
            sEquipmentInventoryModel.setStateModel(state);
            ListenersService.get().updateData(newEquipment);
            ListenersService.get().refreshControl(EquipmentInventoryModel.class);
        }
    }

    public void editEquipmentInventory(EquipmentInventoryModel equipment) {
        ListenersService.get().updateData(new IteractorEquipmentInventory().edit(equipment));
        ListenersService.get().refreshControl(EquipmentInventoryModel.class);
    }

    public void deleteEquipment() {
    }

    public void addEquipmentStateLog(String state, String description, LocalDate date) {
        sEquipmentStateLog = new EquipmentStateLogModel(0, state, description, date);
        sEquipmentInventoryModel.setStateModel(sStateModel);
        sEquipmentInventoryModel.addEntity(sEquipmentStateLog);
        editEquipmentInventory(sEquipmentInventoryModel);
        ListenersService.get().refreshControl(EquipmentInventoryModel.class);
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
        sEquipmentInventoryModel.addInventoryEditLog(sEquipmentInventoryLogModel);
        editEquipmentInventory(sEquipmentInventoryModel);
        ListenersService.get().refreshControl(EquipmentInventoryLogModel.class);
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
                equipmentModel.setEquipmentInventoryList(null);
            }
        }
        if (object.getClass().equals(ParameterModel.class)) {
            ParameterModel parameterModel = (ParameterModel) object;
            for (EquipmentModel equipment : Equipments.get().getObsEntityList()) {
                if (equipment.getEntityList() != null) {
                    for (EquipmentParameterModel equipmentParameter : equipment.getEntityList()) {
                        if (equipmentParameter.getParameterModel().getId() == parameterModel.getId()) {
                            equipment.setEntityList(null);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sEquipmentInventoryModel)) {
                System.out.println("delete " + sEquipmentInventoryModel.getInventoryNumber().getName());
                if (new IteractorEquipmentInventory().delete(sEquipmentInventoryModel.getId())) {
                    ListenersService.get().updateData(sEquipmentInventoryModel);
                    ListenersService.get().updateControl(EquipmentInventoryModel.class);
                }
            }
            if (getSelectedObject().equals(sEquipmentModel)) {
                System.out.println("delete equipment");
                if (new IteractorEquipment().delete(sEquipmentModel.getId())) {
                    ListenersService.get().updateControl(EquipmentModel.class);
                    ListenersService.get().updateUI(EquipmentTabController.class);
                }
            }
        }
    }

    public void cancel() {
        ListenersService.get().refreshControl(EquipmentInventoryModel.class);
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

    public ObservableList<MovementModel> getEquipmentMovementsLog(int id) {
        ObservableList<MovementModel> list = FXCollections.observableArrayList();
        for (MovementModel movement : new IteractorMovement().getList(id, "equipment")) {
            list.add(movement);
        }
        return list;
    }
}
