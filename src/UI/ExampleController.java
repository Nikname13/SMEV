package UI;

import Service.IErrorMessage;
import Service.LisenersService;
import UI.MainTabs.DepartmentTabTabController;
import UI.MainTabs.EquipmentTabTabController;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

public class ExampleController implements IErrorMessage {

    @FXML
    private StackPane mStackPainMain;

    @FXML
    private JFXTabPane mTabContainer;

    public ExampleController() {
        LisenersService.get().setErrorListener(this::showError);
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
    }

    private void selectedTab(String id){

        switch (id){
            case "mDepartmentTab":
                LisenersService.get().updateUI(DepartmentTabTabController.class);
                break;
            case "mEquipmentTab":
                LisenersService.get().updateUI(EquipmentTabTabController.class);
                break;
            case "mSupplyTab": break;
            case "mMovementTab": break;
        }
    }

    @Override
    public void showError(String errorMessage) {
        JFXDialog dialog = new JFXDialog(mStackPainMain, new Label(errorMessage), JFXDialog.DialogTransition.BOTTOM);
        dialog.show();
    }
}