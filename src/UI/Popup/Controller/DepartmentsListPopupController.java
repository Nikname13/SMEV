package UI.Popup.Controller;

import Service.LisenersService;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class DepartmentsListPopupController extends BasePopupController {

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
                case "purchase":
                    LisenersService.get().onMouseClick(node.getId());
                    break;
                case "delete":
                    LisenersService.get().delete();
                    break;
            }
        }
    }
}
