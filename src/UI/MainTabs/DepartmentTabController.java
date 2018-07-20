package UI.MainTabs;

import Service.TabControllerService;
import Service.UpdateService;
import UI.BaseController;
import UI.Department.Controller.DepartmentsController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class DepartmentTabController extends BaseController {

    public DepartmentTabController(){
        UpdateService.get().addListener((Class<?> updateClass)->{
            System.out.println("update class= "+updateClass.getName()+" tabClass "+this.getClass().getName());
            if (updateClass.getName().equals(this.getClass().getName())) {
                TabControllerService.get().setListenerFirstTabPane((Tab nextTab)-> nextTab(nextTab,mDepartmentTabContainer));
                UpdateService.get().updateUI(DepartmentsController.class);
                mDepartmentTabContainer.getSelectionModel().select(0);
            }
        });
        TabControllerService.get().setListenerFirstTabPane((Tab nextTab)-> nextTab(nextTab,mDepartmentTabContainer));
    }

    @FXML
    private JFXTabPane mDepartmentTabContainer;

    @FXML
    public void initialize(){
        System.out.println("department tab initialize");

    }
}
