package UI.Popup.Controller;

import Presenter.EquipmentPresenter;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class EquipmentInventoryPopupController {

    @FXML
    private JFXListView<Node> mPopupList;

    public void initialize() {
        mPopupList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedPopupItem(newValue));
        mPopupList.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) clearSelection();
            }
        });
    }

    private void selectedPopupItem(Node node) {
        if (node != null) {
            switch (node.getId()) {
                case "moveLog":
                    System.out.println(node.getId());
                    break;
                case "statusLog":
                    System.out.println(node.getId());
                    break;
                case "delete":
                    if (EquipmentPresenter.get().getEquipmentInventoryModel() != null)
                        EquipmentPresenter.get().deleteEquipmentInventory(EquipmentPresenter.get().getEquipmentInventoryModel());
                    break;
            }
        }

    }

    private void clearSelection() {
        mPopupList.getSelectionModel().clearSelection();
    }
}
