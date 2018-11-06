package Presenter;

import Iteractor.IteractorEquipmentInventory;
import Iteractor.IteractorMovement;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.SelectedEquipmentInventoryShell;
import Model.Movement.MovementModel;
import Model.Worker.WorkerModel;
import Service.IUpdateData;
import Service.ListenersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class MovementPresenter extends BasePresenter implements IUpdateData {
    private static MovementPresenter sMovementPresenter;
    private static MovementModel sMovementModel;

    private MovementPresenter() {
        ListenersService.get().addListenerData(this);
    }

    public static MovementPresenter get() {
        if (sMovementPresenter == null) {
            sMovementPresenter = new MovementPresenter();
        }
        return sMovementPresenter;
    }

    public MovementModel getMovementModel() {
        return sMovementModel;
    }

    public void setMovementModel(MovementModel movementModel) {
        sMovementModel = movementModel;
        setSelectedObject(movementModel);
    }

    public ObservableList<DepartmentModel> getObservableDepartmentWithout(DepartmentModel department) {
        return Departments.get().getEntityListWithout(department);
    }

    public ObservableList<SelectedEquipmentInventoryShell> getEquipmentInventoryList(DepartmentModel department, ObservableList<EquipmentInventoryModel> listExcluding) {
        ObservableList<SelectedEquipmentInventoryShell> list = FXCollections.observableArrayList();
        if (listExcluding != null) {
            boolean flag = false;
            for (EquipmentInventoryModel equipment : Departments.get().getEntity(department.getId()).getEquipmentList()) {
                for (EquipmentInventoryModel equipmentExcluding : listExcluding) {
                    if (equipment.getId() == equipmentExcluding.getId()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    list.add(new SelectedEquipmentInventoryShell(equipment));
                }
                flag = false;
            }
            return list;
        } else {
            for (EquipmentInventoryModel equipment : Departments.get().getEntity(department.getId()).getEquipmentList()) {
                list.add(new SelectedEquipmentInventoryShell(equipment));
            }
            return list;
        }
    }

    public void moveEquipmentInventory(EquipmentInventoryModel equipment, DepartmentModel toDepartment, WorkerModel fromWorker, WorkerModel toWorker, String base) {
        DepartmentModel fromDepartment = equipment.getDepartmentModel();
        MovementModel movement = new MovementModel(0, LocalDate.now(), base);
        updateDepartment(fromDepartment, toDepartment);
        updateEquipment(equipment, toDepartment);
        movement.addEntity(movement.newMovementEquipment(equipment));
        move(movement, fromDepartment, toDepartment, fromWorker, toWorker, base);
        new IteractorEquipmentInventory().edit(equipment);
        ListenersService.get().updateControl(EquipmentInventoryModel.class);
    }

    public void moveEquipmentInventory(List<EquipmentInventoryModel> equipments, DepartmentModel toDepartment, WorkerModel fromWorker, WorkerModel toWorker, String base) {
        DepartmentModel fromDepartment = equipments.get(0).getDepartmentModel();
        updateDepartment(fromDepartment, toDepartment);
        MovementModel movement = new MovementModel(0, LocalDate.now(), base);
        for (EquipmentInventoryModel equipment : equipments) {
            updateEquipment(equipment, toDepartment);
            movement.addEntity(movement.newMovementEquipment(equipment));
        }
        move(movement, fromDepartment, toDepartment, fromWorker, toWorker, base);
        new IteractorEquipmentInventory().edit(equipments);
        ListenersService.get().updateControl(EquipmentInventoryModel.class);
        ListenersService.get().updateControl(MovementModel.class);
    }

    private void move(MovementModel movement, DepartmentModel fromDepartment, DepartmentModel toDepartment, WorkerModel fromWorker, WorkerModel toWorker, String base) {
        movement.addDepartment(movement.newMovementDepartment(fromDepartment));
        movement.addDepartment(movement.newMovementDepartment(toDepartment));
        movement.addWorker(movement.newMovementWorker(fromWorker));
        movement.addWorker(movement.newMovementWorker(toWorker));
        new IteractorMovement().addNew(movement);
    }

    private void updateEquipment(EquipmentInventoryModel equipment, DepartmentModel toDepartment) {
        // Equipments.get().getEntity(equipment.getEquipmentModel().getId()).setEquipmentInventoryList(null);
        equipment.setDepartmentModel(toDepartment);//назначение нового отдела дла оборудования
    }

    private void updateDepartment(DepartmentModel fromDepartment, DepartmentModel toDepartment) {
        // Departments.get().getEntity(toDepartment.getId()).setEquipmentList(null);
        // Departments.get().getEntity(fromDepartment.getId()).setEquipmentList(null);
    }


    @Override
    public void loadEntity(int id) {
        new IteractorMovement().loadData(id);
    }

    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {

    }
}
