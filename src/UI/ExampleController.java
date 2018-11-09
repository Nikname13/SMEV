package UI;

import Service.IErrorMessage;
import Service.ListenersService;
import UI.MainTabs.Controller.*;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ExampleController extends BaseController implements IErrorMessage {

    @FXML
    private StackPane mStackPainMain;

    @FXML
    private JFXTabPane mTabContainer;

    public ExampleController() {
        ListenersService.get().setErrorListener(this::showError);
    }

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
        setParentStackPane(mStackPainMain);
    }

    private void selectedTab(String id){

        switch (id){
            case "mDepartmentTab":
                ListenersService.get().updateUI(DepartmentTabController.class);
                break;
            case "mEquipmentTab":
                ListenersService.get().updateUI(EquipmentTabController.class);
                break;
            case "mSupplyTab":
                ListenersService.get().updateUI(SupplyTabController.class);
                break;
            case "mMovementTab":
                ListenersService.get().updateUI(MovementTabController.class);
                break;
            case "mCatalogTab":
                ListenersService.get().updateUI(CatalogTabController.class);
                break;
        }
    }

    @Override
    public void showError(String errorMessage) {
        JFXDialog dialog = new JFXDialog(mStackPainMain, new Label(errorMessage), JFXDialog.DialogTransition.BOTTOM);
        dialog.show();
    }

    @Override
    protected Stage getStage() {
        return null;
    }

    @Override
    public void destroy() {

    }
}