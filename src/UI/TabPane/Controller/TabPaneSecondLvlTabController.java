package UI.TabPane.Controller;

import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.BaseTabController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class TabPaneSecondLvlTabController extends BaseTabController implements IUpdateUI {

    public TabPaneSecondLvlTabController() {
        System.out.println("second lvl controller");
        UpdateService.get().addListenerUI(this);
        TabControllerService.get().setListenerThirdTabPane(((Tab nextTab)->nextTab(nextTab,mSecondLvlTabPane)));
    }

    @FXML
    private JFXTabPane mSecondLvlTabPane;

    @FXML
    public void initialize(){

    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            mSecondLvlTabPane.getSelectionModel().select(0);
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }
}
