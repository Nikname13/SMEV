package UI.MainTabs;

import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.BaseController;
import UI.Equipment.Controller.EquipmentsController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class EquipmentTabController extends BaseController implements IUpdateUI {

    public EquipmentTabController() {
        UpdateService.get().addListener(this);
    }

    @FXML
    private JFXTabPane mEquipmentTabContainer;

    @FXML
    public void initialize(){
        System.out.println("equipmentTab initialize");
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            TabControllerService.get().setListenerFirstTabPane((Tab nextTab) -> nextTab(nextTab, mEquipmentTabContainer));
            UpdateService.get().updateUI(EquipmentsController.class);
            mEquipmentTabContainer.getSelectionModel().select(0);
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }
}
