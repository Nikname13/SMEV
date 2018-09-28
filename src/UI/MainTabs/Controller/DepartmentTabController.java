package UI.MainTabs.Controller;

import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseTabController;
import UI.Department.Controller.DepartmentsController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class DepartmentTabController extends BaseTabController implements IUpdateUI {

    public DepartmentTabController() {
        ListenersService.get().addListenerUI(this);
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
            ListenersService.get().updateUI(DepartmentsController.class);
            mDepartmentTabContainer.getSelectionModel().select(0);
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
