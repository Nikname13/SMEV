package UI.Equipment.Equipment_inventory.Equipment_move.Controller;

import Model.Department.DepartmentModel;
import Model.Movement.MovementModel;
import Presenter.EquipmentPresenter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class MovementsEquipmentLogController {

    @FXML
    private TreeTableView<MovementModel> mMovementsLogTreeView;

    @FXML
    private TreeTableColumn<MovementModel, String> mDateColumn, mBaseColumn;

    @FXML
    public void initialize() {
        initTreeVie();
    }

    private void initTreeVie() {
        mDateColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().dateToString());
        mBaseColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mMovementsLogTreeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedMovement(newValue)));
        updateTable(EquipmentPresenter.get().getEquipmentMovementsLog(EquipmentPresenter.get().getEquipmentInventoryModel().getId()));
    }

    private void selectedMovement(TreeItem<MovementModel> newValue) {
        for (DepartmentModel department : newValue.getValue().getObsDepartmentList()) {
            System.out.println(department.getName());
        }
    }

    private void updateTable(ObservableList<MovementModel> equipmentMovementsLog) {
        TreeItem<MovementModel> rootItem = new TreeItem<>();
        for (MovementModel log : equipmentMovementsLog) {
            rootItem.getChildren().add(new TreeItem<>(log));
        }
        mMovementsLogTreeView.setRoot(rootItem);
        mMovementsLogTreeView.setShowRoot(false);
    }
}
