package UI.MainTabs.Controller;

import Service.ListenersService;
import Service.TabControllerService;
import UI.Area.Controller.AreasController;
import UI.BaseTabController;
import UI.Inventory_number.Controller.InventoryNumbersController;
import UI.Parameter.Controller.ParametersController;
import UI.Provider.Controller.ProvidersController;
import UI.State.Controller.StatesController;
import UI.Type.Controller.TypesController;
import UI.Worker.Controller.WorkersController;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class CatalogTabController extends BaseTabController {

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
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getInventoryNumberListResource()));
                    ListenersService.get().updateUI(InventoryNumbersController.class);
                    break;
                case "mParameter":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getParameterListResource()));
                    ListenersService.get().updateUI(ParametersController.class);
                    break;
                case "mProvider":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getProviderResource()));
                    ListenersService.get().updateUI(ProvidersController.class);
                    break;
                case "mArea":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getAreaResource()));
                    ListenersService.get().updateUI(AreasController.class);
                    break;
                case "mState":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getStateResource()));
                    ListenersService.get().updateUI(StatesController.class);
                    break;
                case "mWorker":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getWorkerResource()));
                    ListenersService.get().updateUI(WorkersController.class);
                    break;
                case "mType":
                    TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getTypeResource()));
                    ListenersService.get().updateUI(TypesController.class);
                    break;
            }
        }
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            System.out.println("catalog tab Controller");
            TabControllerService.get().setListenerFirstTabPane((Tab nextTab) -> nextTab(nextTab, mCatalogTabContainer));
            mCatalogTabContainer.getSelectionModel().select(0);
            mListView.getSelectionModel().clearSelection();
        }
    }
}
