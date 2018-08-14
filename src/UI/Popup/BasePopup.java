package UI.Popup;

import Service.IUpdatePopup;
import Service.UpdateService;
import com.jfoenix.controls.JFXPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

import java.io.IOException;

public class BasePopup implements IUpdatePopup {

    private static String sEquipmentInventoryPopup = "/UI/Popup/equipmentInventoryPopup.fxml";
    private static String sBaseListPopup = "/UI/Popup/BaseListPopup.fxml";
    private static String sDepartmentListPopup = "";
    private JFXPopup mPopup;

    public BasePopup(Node node, String resourceURL, Runnable primaryClick) {
        UpdateService.get().addListenerPopup(this::hide);
        try {
            mPopup = new JFXPopup(FXMLLoader.load(getClass().getResource(resourceURL)));
            node.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY)
                    mPopup.show(node, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (primaryClick != null)
                        primaryClick.run();
                }
            });
        } catch (IOException ex) {
            System.out.println("Ошибка загрузки " + resourceURL);
            ex.printStackTrace();
        }
    }


    public static String getEquipmentInventoryPopup() {
        return sEquipmentInventoryPopup;
    }

    public static String getBaseListPopup() {
        return sBaseListPopup;
    }

    public JFXPopup get() {
        return mPopup;
    }

    @Override
    public void hide() {
        if (mPopup.showingProperty().get())
            mPopup.hide();
    }
}
