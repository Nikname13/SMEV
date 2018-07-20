package UI;

import Service.UpdateService;
import UI.MainTabs.DepartmentTabController;
import UI.MainTabs.EquipmentTabController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class ExampleController {

    public ExampleController() {

    }

    @FXML
    private JFXTabPane mTabContainer;

    @FXML
    private Tab mDepartmentTab, mEquipmentTab, mSupplyTab, mMovementTab;


    private double tabWith = 100.0;
    public static int lastSelectedTabIndex = 0;

    private void configureTabPane() {
        mTabContainer.setTabMinWidth(tabWith);
        mTabContainer.setTabMaxWidth(tabWith);
        mTabContainer.setTabMinHeight(tabWith);
        mTabContainer.setTabMaxHeight(tabWith);
    }

    @FXML
    public void initialize() {
        System.out.println("example initialize");
        mTabContainer.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedTab(newValue.getId())));
    }

    private void selectedTab(String id){

        switch (id){
            case "mDepartmentTab":
                UpdateService.get().updateUI(DepartmentTabController.class); break;
            case "mEquipmentTab":
                UpdateService.get().updateUI(EquipmentTabController.class); break;
            case "mSupplyTab": break;
            case "mMovementTab": break;
        }
    }
}