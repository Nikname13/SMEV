package UI.TabPane.Controller;

import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseTabController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class TabPaneSecondLvlTabController extends BaseTabController {

    public TabPaneSecondLvlTabController() {
        System.out.println("second lvl controller");
        ListenersService.get().addListenerUI(this);
        TabControllerService.get().setListenerSecondTabPane(((Tab nextTab) -> nextTab(nextTab, mSecondLvlTabPane)));
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
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            System.out.println("UPDATESECONDLVLTABPANE");
            if (mSecondLvlTabPane.getTabs().size() > 1) mSecondLvlTabPane.getTabs().remove(1);
        }
    }

}
