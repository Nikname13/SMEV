package Presenter;

import Iteractor.IteractorEquipment;
import Iteractor.IteractorEquipmentInventory;
import Iteractor.IteractorEquipmentParameter;
import Iteractor.IteractorMovement;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.EquipmentParameterModel;
import Model.Equipment.Equipments;
import Model.FileDumpModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Movement.MovementModel;
import Model.Parameter.ParameterModel;
import Model.State.StateModel;
import Model.Type.TypeModel;
import Service.ListenersService;
import UI.MainTabs.Controller.EquipmentTabController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipmentPresenter extends BaseFilePresenter {

    private static EquipmentModel sEquipmentModel;
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
        setSelectedObject(sEquipmentModel);
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

    public void addMovement(LocalDate date, String base, Object fromDepartment, Object toDepartment, Object fromWorker, Object toWorker, Object equipment) {

    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
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


    @Override
    protected void editFile(FileDumpModel editableFile) {
        new IteractorEquipment().editFile(editableFile, sEquipmentModel.getId(), getTypeDocuments());
    }

    @Override
    protected List<FileDumpModel> uploadFiles(List<File> fileList) {
        return new IteractorEquipment().uploadFile(sEquipmentModel.getId(), fileList, getTypeDocuments());
    }

    @Override
    protected File getFile(File savePathFile) {
        return new IteractorEquipment().downloadFile(sEquipmentModel.getId(), getTypeDocuments(), FileDumpPresenter.get().getFileDumpModel().getPath(), savePathFile);
    }
}
