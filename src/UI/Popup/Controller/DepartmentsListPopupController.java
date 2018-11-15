package UI.Popup.Controller;

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
}
