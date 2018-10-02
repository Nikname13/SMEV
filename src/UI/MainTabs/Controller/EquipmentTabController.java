package UI.MainTabs.Controller;

import Model.Equipment.EquipmentModel;
import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseTabController;
import UI.Equipment.Controller.EquipmentsController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class EquipmentTabController extends BaseTabController implements IUpdateUI {

    public EquipmentTabController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private
    TabPane mEquipmentTabContainer;

    @FXML
    public void initialize(){
        System.out.println("equipmentTab initialize");
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            TabControllerService.get().setListenerFirstTabPane((Tab nextTab) -> nextTab(nextTab, mEquipmentTabContainer));
            ListenersService.get().updateUI(EquipmentsController.class);
            ListenersService.get().updateControl(EquipmentModel.class);
            mEquipmentTabContainer.getSelectionModel().select(0);
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
