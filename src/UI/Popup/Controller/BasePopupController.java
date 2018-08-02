package UI.Popup.Controller;

import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class BasePopupController {

    protected void init(JFXListView<Node> list) {
        list.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedPopupItem(newValue)));
        list.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) list.getSelectionModel().clearSelection();
            }
        });
    }

    protected void selectedPopupItem(Node node) {
    }
}
