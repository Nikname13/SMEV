package UI.MainTabs.Controller;

import Model.Movement.MovementModel;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseTabController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class MovementTabController extends BaseTabController {

    @FXML
    private JFXTabPane mMovementTabContainer;

    public MovementTabController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {

    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            TabControllerService.get().setListenerFirstTabPane((Tab nextTab) -> nextTab(nextTab, mMovementTabContainer));
            //ListenersService.get().updateUI(EquipmentsController.class);
            ListenersService.get().updateControl(MovementModel.class);
            mMovementTabContainer.getSelectionModel().select(0);
        }
    }

}
