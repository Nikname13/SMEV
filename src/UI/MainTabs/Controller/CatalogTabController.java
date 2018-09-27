package UI.MainTabs.Controller;

import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseTabController;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CatalogTabController extends BaseTabController implements IUpdateUI {

    @FXML
    private JFXListView<Label> mListView;

    @FXML
    private JFXTabPane mCatalogTabContainer;

    public CatalogTabController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
        initList();
    }

    private void initList() {
        mListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedItem(newValue)));
    }

    private void selectedItem(Label newValue) {
        if (newValue != null) {
            switch (newValue.getId()) {
                case "mInventoryNumber":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getInventoryNumberResource()));
                    break;
                case "mParameter":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getParameterResource()));
                    break;
                case "mProvider":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getProviderResource()));
                    break;
                case "mArea":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getAreaResource()));
                    break;
                case "mState":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getStateResource()));
                    break;
                case "mWorker":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getWorkerResource()));
                    break;
                case "mType":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getTypeResource()));
                    break;
            }
        }
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            System.out.println("catalog tab Controller");
            TabControllerService.get().setListenerFirstTabPane((Tab nextTab) -> nextTab(nextTab, mCatalogTabContainer));
            //ListenersService.get().updateUI(EquipmentsController.class);
            //ListenersService.get().updateControl(MovementModel.class);
            mCatalogTabContainer.getSelectionModel().select(0);
        }
    }

    @Override
    public void nextTab(Tab nextTab, TabPane tabPane) {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(nextTab);
        tabPane.getSelectionModel().select(nextTab);
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
