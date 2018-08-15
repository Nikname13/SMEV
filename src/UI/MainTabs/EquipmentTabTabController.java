package UI.MainTabs;

import Service.IUpdateUI;
import Service.LisenersService;
import Service.TabControllerService;
import UI.BaseTabController;
import UI.Equipment.Controller.EquipmentsController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class EquipmentTabTabController extends BaseTabController implements IUpdateUI {

    public EquipmentTabTabController() {
        LisenersService.get().addListenerUI(this);
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
            LisenersService.get().updateUI(EquipmentsController.class);
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
