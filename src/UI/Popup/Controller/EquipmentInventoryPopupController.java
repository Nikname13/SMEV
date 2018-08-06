package UI.Popup.Controller;

import Presenter.EquipmentPresenter;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class EquipmentInventoryPopupController extends BasePopupController {

    @FXML
    private JFXListView<Node> mPopupList;

    @FXML
    public void initialize() {
        init(mPopupList);
    }

    @Override
    protected void selectedPopupItem(Node node) {
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
}
