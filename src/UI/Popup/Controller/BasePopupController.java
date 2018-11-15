package UI.Popup.Controller;

import Service.ListenersService;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public abstract class BasePopupController {

    protected void init(JFXListView<Node> list) {
        list.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedPopupItem(newValue)));
        list.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!oldValue) list.getSelectionModel().clearSelection();
            }
        });
        list.setOnMouseClicked(event -> {
            ListenersService.get().hidePopup();
        });
    }

    protected void selectedPopupItem(Node node) {
        if (node != null) {
            if (node.getId().equals("delete")) {
                ListenersService.get().delete();
            } else {
                ListenersService.get().onMouseClick(node.getId());
            }
        }
    }
}
