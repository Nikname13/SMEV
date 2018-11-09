package UI.Inventory_number.Controller;

import Model.Inventory_number.InventoryNumberLog;
import Presenter.InventoryNumberPresenter;
import UI.BaseController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

public class InventoryNumberLogController extends BaseController {

    @FXML
    private TreeTableView<InventoryNumberLog> mInventoryLogTreeView;

    @FXML
    private TreeTableColumn<InventoryNumberLog, String> mDateColumn, mInventoryNumberColumn, mDescriptionColumn;

    @FXML
    public void initialize() {
        initTreeTable();
    }

    private void initTreeTable() {
        mDateColumn.setCellValueFactory(cellDate -> cellDate.getValue().getValue().dateToString());
        mInventoryNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().descriptionProperty());
        updateTable(InventoryNumberPresenter.get().getInventoryNumberModel().getObservableEntityList());
    }

    private void updateTable(ObservableList<InventoryNumberLog> logList) {
        TreeItem<InventoryNumberLog> rootItem = new TreeItem<>();
        for (InventoryNumberLog log : logList) {
            rootItem.getChildren().add(new TreeItem<>(log));
        }
        mInventoryLogTreeView.setRoot(rootItem);
        mInventoryLogTreeView.setShowRoot(false);
    }
    @Override
    protected Stage getStage() {
        return null;
    }

}
