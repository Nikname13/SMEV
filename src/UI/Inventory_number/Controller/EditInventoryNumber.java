package UI.Inventory_number.Controller;

import Model.Equipment.EquipmentInventoryModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import Presenter.InventoryNumberPresenter;
import Service.IUpdateUI;
import UI.BaseController;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditInventoryNumber extends BaseController implements IUpdateUI {

    @FXML
    private JFXTextField mTextFieldNumber;
    @FXML
    private JFXCheckBox mCheckBoxGroup;
    @FXML
    private JFXComboBox<SupplyModel> mComboBoxSupply;
    @FXML
    private JFXTextArea mTextAreaDescription;
    @FXML
    private TreeTableView<EquipmentInventoryModel> mTreeTableEquipmentInventory;
    @FXML
    private TreeTableColumn<EquipmentInventoryModel,String> mNameEquipmentColumn,mDepartmentEquipmentColumn,mStateEquipmentColumn,mDescriptionEquipmentColumn;

    @FXML
    private AnchorPane mEditInventoryNumberPane;

    public void initialize(){
    }

    @FXML
    private void onClickAdd(){

    }

    @Override
    public void updateUI(Class<?> updateClass) {

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

    @Override
    protected Stage getStage() {
        return (Stage)mEditInventoryNumberPane.getScene().getWindow();
    }
}
