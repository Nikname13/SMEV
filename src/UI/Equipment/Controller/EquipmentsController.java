package UI.Equipment.Controller;

import Model.AbstractModel;
import Model.Equipment.EquipmentModel;
import Presenter.EquipmentPresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.Coordinator;
import UI.Popup.BasePopup;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EquipmentsController implements IUpdateUI {

    public EquipmentsController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private StackPane mStackPaneEquipments;

    @FXML
    private TreeView<AbstractModel<?>> mEquipmentTreeView;

    @FXML
    public void initialize() {
        initEquipmentTreeView();
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mEquipmentTreeView, BasePopup.getBaseListPopup(), null);
    }

    private void initEquipmentTreeView() {
        mEquipmentTreeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
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
        //updateEquipmentTreeView();
    }

    private void updateEquipmentTreeView() {
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

    }


    @FXML
    private void onClickAdd() {
        new Coordinator().goToAddEquipmentWindow((Stage) mStackPaneEquipments.getScene().getWindow());
    }

    private void selectedEquipment(TreeItem<AbstractModel<?>> equipment) {
        System.out.println("select equipment");
        if (equipment != null) {
            if (equipment.getValue().getClass().getName().equals(EquipmentModel.class.getName())) {
                EquipmentPresenter.get().setSelectedObject(equipment.getValue());
                EquipmentPresenter.get().setEquipmentModel(equipment.getValue());
                TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditEquipmentResource()));
                ListenersService.get().updateUI(EquipmentModel.class);
            } else {
                EquipmentPresenter.get().setSelectedObject(null);
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
        if (updateClass.getName().equals(EquipmentModel.class.getName())) {
            updateEquipmentTreeView();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {
        if (updateClass.getName().equals(EquipmentModel.class.getName())) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateEquipmentTreeView();
                    mEquipmentTreeView.getSelectionModel().select(new TreeItem<>((EquipmentModel) currentItem));
                }
            });

        }
    }
}
