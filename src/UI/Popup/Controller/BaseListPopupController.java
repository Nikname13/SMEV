package UI.Popup.Controller;

import Service.UpdateService;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class BaseListPopupController extends BasePopupController {

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
                case "delete":
                    UpdateService.get().delete();
                    break;
            }
        }
    }

}