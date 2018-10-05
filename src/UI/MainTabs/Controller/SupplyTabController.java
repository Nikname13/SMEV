package UI.MainTabs.Controller;

import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseTabController;
import UI.Supply.Controller.SupplysController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class SupplyTabController extends BaseTabController implements IUpdateUI {

    @FXML
    private JFXTabPane mSupplyTabContainer;

    public SupplyTabController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
        System.out.println("supply tab initialize");
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            TabControllerService.get().setListenerFirstTabPane((Tab nextTab) -> nextTab(nextTab, mSupplyTabContainer));
            ListenersService.get().updateUI(SupplysController.class);
            mSupplyTabContainer.getSelectionModel().select(0);
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }
}
