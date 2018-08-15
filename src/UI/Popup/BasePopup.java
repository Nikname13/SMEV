package UI.Popup;

import Service.IOnMouseClick;
import Service.IUpdatePopup;
import Service.LisenersService;
import com.jfoenix.controls.JFXPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

import java.io.IOException;

public class BasePopup implements IUpdatePopup {

    private static String sEquipmentInventoryPopup = "/UI/Popup/equipmentInventoryPopup.fxml";
    private static String sBaseListPopup = "/UI/Popup/BaseListPopup.fxml";
    private static String sDepartmentListPopup = "/UI/Popup/departmentsListPopup.fxml";
    private JFXPopup mPopup;

    public BasePopup(Node node, String resourceURL, IOnMouseClick primaryClick) {
        LisenersService.get().addListenerPopup(this::hide);
        try {
            mPopup = new JFXPopup(FXMLLoader.load(getClass().getResource(resourceURL)));
            node.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    LisenersService.get().setListenerOnMouseClick(primaryClick);
                    mPopup.show(node, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
                }
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (primaryClick != null)
                        primaryClick.primaryClick(node.getId());
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

    public static String getDepartmentListPopup() {
        return sDepartmentListPopup;
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
