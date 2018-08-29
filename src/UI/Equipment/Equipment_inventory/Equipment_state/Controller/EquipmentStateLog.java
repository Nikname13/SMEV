package UI.Equipment.Equipment_inventory.Equipment_state.Controller;

import Model.Equipment.EquipmentStateLogModel;
import Presenter.EquipmentPresenter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class EquipmentStateLog {

    @FXML
    private TreeTableView<EquipmentStateLogModel> mEquipmentStateLogTreeTable;

    @FXML
    private TreeTableColumn<EquipmentStateLogModel, String> mDateColumn, mStateColumn, mDescriptionColumn;

    @FXML
    public void initialize() {
        mDateColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().dateToString());
        mStateColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().descriptionProperty());
        updateTable(EquipmentPresenter.get().getEquipmentInventoryModel().getObservableEntityList());
    }

    private void updateTable(ObservableList<EquipmentStateLogModel> equipmentList) {
    }
}
