package Presenter;

import Iteractor.IteractorMovement;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.SelectedEquipmentInventoryShell;
import Model.Movement.MovementModel;
import Service.IUpdateData;
import Service.ListenersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    }

    public ObservableList<DepartmentModel> getObservableDepartmentWithout(DepartmentModel department) {
        return Departments.get().getEntityListWithout(department);
    }

    public ObservableList<SelectedEquipmentInventoryShell> getEquipmentInventoryList(DepartmentModel department, ObservableList<EquipmentInventoryModel> listExcluding) {
        System.out.println("ololo blat");
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
                } else {
                    flag = true;
                }
            }
            return list;
        } else {
            for (EquipmentInventoryModel equipment : Departments.get().getEntity(department.getId()).getEquipmentList()) {
                list.add(new SelectedEquipmentInventoryShell(equipment));
            }
            return list;
        }
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
