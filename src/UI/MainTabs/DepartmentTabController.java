package UI.MainTabs;

import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.BaseController;
import UI.Department.Controller.DepartmentsController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class DepartmentTabController extends BaseController implements IUpdateUI {

    public DepartmentTabController(){
        UpdateService.get().addListener(this);
        TabControllerService.get().setListenerFirstTabPane((Tab nextTab)-> nextTab(nextTab,mDepartmentTabContainer));
    }

    @FXML
    private JFXTabPane mDepartmentTabContainer;

    @FXML
    public void initialize(){
        System.out.println("department tab initialize");

    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            TabControllerService.get().setListenerFirstTabPane((Tab nextTab) -> nextTab(nextTab, mDepartmentTabContainer));
            UpdateService.get().updateUI(DepartmentsController.class);
            mDepartmentTabContainer.getSelectionModel().select(0);
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }
}
