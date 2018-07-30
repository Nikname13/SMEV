package UI.Equipment.Controller;

import Model.AbstractModel;
import Model.Equipment.EquipmentModel;
import Presenter.EquipmentPresenter;
import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.Coordinator;
import com.jfoenix.controls.JFXTreeView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EquipmentsController implements IUpdateUI {

    public EquipmentsController() {
        UpdateService.get().addListener(this);
    }

    @FXML
    private TableView<EquipmentModel> tableViewEquipment;

    @FXML
    private TableColumn<EquipmentModel, String> nameColumn, nameFactColumn;

    @FXML
    private TableColumn<EquipmentModel, String> typeColumn;

    @FXML
    private AnchorPane anchorPaneEquipments;

    @FXML
    private JFXTreeView<AbstractModel<?>> mEquipmentTreeView;

    @FXML
    public void initialize() {
/*        tableViewEquipment.setItems(EquipmentPresenter.get().getObservableEquipment());
        nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        nameFactColumn.setCellValueFactory(cellData->cellData.getValue().nameFactProperty());
        typeColumn.setCellValueFactory(cellData->cellData.getValue().getTypeModel().nameProperty());
        tableViewEquipment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedEquipment(newValue));*/

        TreeItem<AbstractModel<?>> rootItem = new TreeItem<>();
        boolean flag = false;
        for (EquipmentModel equipment : EquipmentPresenter.get().getObservableEquipment()) {
            for (TreeItem<AbstractModel<?>> treeEquipment : rootItem.getChildren()) {
                if (treeEquipment.getValue().getId() == equipment.getTypeModel().getId()) {
                    treeEquipment.getChildren().add(new TreeItem<>(equipment));
                    flag = true;
                }
            }
            if (!flag) {
                TreeItem<AbstractModel<?>> treeItemFirst = new TreeItem<>(equipment.getTypeModel());
                rootItem.getChildren().add(treeItemFirst);
                treeItemFirst.getChildren().add(new TreeItem<>(equipment));
            } else {
                flag = false;
            }
        }
        mEquipmentTreeView.setRoot(rootItem);
        mEquipmentTreeView.setShowRoot(false);
        mEquipmentTreeView.setCellFactory(p -> new TreeCell<>() {
            @Override
            protected void updateItem(AbstractModel<?> item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        mEquipmentTreeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
    }

    @FXML
    private void onClickAdd() {
        new Coordinator().goToAddEquipmentWindow((Stage) anchorPaneEquipments.getScene().getWindow(),100.0,200.0);
    }

    private void selectedEquipment(TreeItem<AbstractModel<?>> equipment) {
        System.out.println("select equipment");
        if (equipment != null) {
            if (equipment.getValue().getClass().getName().equals(EquipmentModel.class.getName())) {
                System.out.println(" listener " + TabControllerService.get().getListenerFirstTabPane().getClass().getName());
                System.out.println(equipment.getValue().getClass().getName());
                EquipmentPresenter.get().setEquipmentModel(equipment.getValue());
                TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditEquipmentResource()));
                UpdateService.get().updateUI(EquipmentModel.class);
                // new Coordinator().goToEditEquipmentWindow((Stage)anchorPaneEquipments.getScene().getWindow());
            }
        }
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            mEquipmentTreeView.getSelectionModel().clearSelection();
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }
}
