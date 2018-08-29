package UI.Equipment.Equipment_inventory.Equipment_number.Controller;

import Model.Equipment.EquipmentInventoryLogModel;
import Presenter.EquipmentPresenter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class InventoryLogController {

    @FXML
    private TreeTableView<EquipmentInventoryLogModel> mInventoryLogTreeView;

    @FXML
    private TreeTableColumn<EquipmentInventoryLogModel, String> mDateColumn, mInventoryNumberColumn, mDescriptionColumn;

    @FXML
    public void initialize() {
        initTreeTable();
    }

    private void initTreeTable() {
        mDateColumn.setCellValueFactory(cellDate -> cellDate.getValue().getValue().dateToString());
        mInventoryNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().descriptionProperty());
        updateTable(EquipmentPresenter.get().getEquipmentInventoryModel().getObsInventoryLogList());
    }

    private void updateTable(ObservableList<EquipmentInventoryLogModel> equipmentList) {
        TreeItem<EquipmentInventoryLogModel> rootItem = new TreeItem<>();
        for (EquipmentInventoryLogModel log : equipmentList) {
            rootItem.getChildren().add(new TreeItem<>(log));
        }
        mInventoryLogTreeView.setRoot(rootItem);
        mInventoryLogTreeView.setShowRoot(false);
    }

}
