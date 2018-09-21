package UI.MainTabs;

import Model.Movement.MovementModel;
import Service.IUpdateUI;
import Service.LisenersService;
import Service.TabControllerService;
import UI.BaseTabController;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class MovementTabController extends BaseTabController implements IUpdateUI {

    @FXML
    private JFXTabPane mMovementTabContainer;

    public MovementTabController() {
        LisenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {

    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            TabControllerService.get().setListenerFirstTabPane((Tab nextTab) -> nextTab(nextTab, mMovementTabContainer));
            //LisenersService.get().updateUI(EquipmentsController.class);
            LisenersService.get().updateControl(MovementModel.class);
            mMovementTabContainer.getSelectionModel().select(0);
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
