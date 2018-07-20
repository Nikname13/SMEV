package UI.MainTabs;

import Service.TabControllerService;
import Service.UpdateService;
import UI.BaseController;
import UI.Equipment.Controller.EquipmentsController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class EquipmentTabController extends BaseController {

    public EquipmentTabController() {
       UpdateService.get().addListener((Class<?> updateClass)->{
            System.out.println("update class= "+updateClass.getName()+" tabClass "+this.getClass().getName());
            if (updateClass.getName().equals(this.getClass().getName())) {
                TabControllerService.get().setListenerFirstTabPane((Tab nextTab)-> nextTab(nextTab,mEquipmentTabContainer));
                UpdateService.get().updateUI(EquipmentsController.class);
                mEquipmentTabContainer.getSelectionModel().select(0);
            }
        });
    }

    @FXML
    private JFXTabPane mEquipmentTabContainer;

    @FXML
    public void initialize(){
        System.out.println("equipmentTab initialize");
    }
}
