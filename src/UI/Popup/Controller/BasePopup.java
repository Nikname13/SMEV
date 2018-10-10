package UI.Popup.Controller;

import Presenter.BasePresenter;
import Service.IOnMouseClick;
import Service.IUpdatePopup;
import Service.ListenersService;
import com.jfoenix.controls.JFXPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

import java.io.IOException;

public class BasePopup implements IUpdatePopup {

    private static String sEquipmentInventoryPopup = "/UI/Popup/equipmentInventoryPopup.fxml";
    private static String sBaseListPopup = "/UI/Popup/baseListPopup.fxml";
    private static String sDepartmentListPopup = "/UI/Popup/departmentsListPopup.fxml";
    private static String sSupplyListPopup = "/UI/Popup/supplyListPopup.fxml";
    private JFXPopup mPopup;

    public BasePopup(Node node, String resourceURL, IOnMouseClick primaryClick) {
        ListenersService.get().addListenerPopup(this::hide);
        try {
            mPopup = new JFXPopup(FXMLLoader.load(getClass().getResource(resourceURL)));
            node.setOnMouseClicked(event -> {
                if (BasePresenter.getSelectedObject() != null) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        ListenersService.get().setListenerOnMouseClick(primaryClick);
                        mPopup.show(node, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
                    }
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (primaryClick != null)
                            primaryClick.primaryClick(node.getId());
                    }
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

    public static String getSupplyListPopup() {
        return sSupplyListPopup;
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
