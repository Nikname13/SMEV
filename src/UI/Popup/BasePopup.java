package UI.Popup;

import com.jfoenix.controls.JFXPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

import java.io.IOException;

public class BasePopup {

    private static String sEquipmentInventoryPopup = "/UI/Popup/equipmentInventoryPopup.fxml";
    private static String sDepartmentsListPopup = "/UI/Popup/departmentsListPopup.fxml";
    private JFXPopup mPopup;

    public BasePopup(Node node, String resourceURL) {

        try {
            mPopup = new JFXPopup(FXMLLoader.load(getClass().getResource(resourceURL)));
            node.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY)
                    mPopup.show(node, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());

            });
        } catch (IOException ex) {
            System.out.println("Ошибка загрузки " + resourceURL);
            ex.printStackTrace();
        }
    }

    public static String getEquipmentInventoryPopup() {
        return sEquipmentInventoryPopup;
    }

    public static String getDepartmentsListPopup() {
        return sDepartmentsListPopup;
    }

    public JFXPopup get() {
        return mPopup;
    }
}
