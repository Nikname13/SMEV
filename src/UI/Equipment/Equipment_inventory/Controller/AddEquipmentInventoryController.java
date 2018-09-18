package UI.Equipment.Equipment_inventory.Controller;

import Model.Equipment.EquipmentModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.State.StateModel;
import Presenter.EquipmentPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddEquipmentInventoryController extends BaseController {

    private EquipmentModel mEquipment;
    private BaseValidator mBaseValidator = new BaseValidator();

    public AddEquipmentInventoryController(){
        mEquipment=EquipmentPresenter.get().getEquipmentModel();
    }

    @FXML
    private JFXTextField mTextFieldGuaranty, mTextFieldCount;

    @FXML
    private JFXComboBox<StateModel> mComboBoxState;

    @FXML
    private JFXComboBox<InventoryNumberModel> mComboBoxInventory;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private ValidationFacade mFacadeNumber, mFacadeState;

    @FXML
    private Label mErrorNumber, mErrorState;

    @FXML
    private AnchorPane mAnchorPaneEquipmentInventory;

    public void initialize(){
        initComboBoxNumber(mComboBoxInventory, false);
        initComboBoxState(mComboBoxState, false);
        initTextFieldCount();
        mBaseValidator.setJFXTextFields(mTextFieldCount, mTextFieldGuaranty);
        mBaseValidator.setValidationFacades(new Pair(mFacadeNumber, mErrorNumber), new Pair(mFacadeState, mErrorState));
    }

    private void initTextFieldCount() {
        mTextFieldCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                if (!newValue.isEmpty()) {
                    if (!newValue.matches("^[1-9]+([0-9]+$)?")) {
                        mTextFieldCount.setText(oldValue);
                        return;
                    }
                    int number = Integer.parseInt(newValue);
                    if (number <= 100) {
                        return;
                    }
                    mTextFieldCount.setText(oldValue);
                }
            }
        });
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneEquipmentInventory.getScene().getWindow();
    }

    @Override
    protected void initComboBoxNumber(JFXComboBox<InventoryNumberModel> comboBox, boolean isSelectionItem) {
        super.initComboBoxNumber(comboBox, isSelectionItem);
        comboBox.setItems(EquipmentPresenter.get().getObservableInventory());
        comboBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedInventory(newValue)));
    }

    private void selectedInventory(InventoryNumberModel inventory){
        if (inventory != null) {
            mTextFieldCount.setText("");
            mTextFieldCount.setVisible(inventory.isGroup());
                resizeWidthStage();
        }
    }

    @Override
    protected void initComboBoxState(JFXComboBox<StateModel> comboBox, boolean isSelectionItem) {
        super.initComboBoxState(comboBox, isSelectionItem);
        comboBox.setItems(EquipmentPresenter.get().getObservableState());
    }

    private void resizeWidthStage() {
        if (mTextFieldCount.isVisible()) {
            getStage().setWidth(getStage().getMinWidth() + (mTextFieldCount.getWidth() + 15));
        } else {
            getStage().setWidth(getStage().getMinWidth());
        }
    }

    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            System.out.println("true");
            EquipmentPresenter.get().addEquipmentInventory(
                    mComboBoxInventory.getValue(),
                    Integer.parseInt(mTextFieldGuaranty.getText()),
                    mTextAreaDescription.getText(),
                    null,
                    mEquipment,
                    mComboBoxState.getValue(),
                    mComboBoxInventory.getValue().isGroup() ? Integer.parseInt(mTextFieldCount.getText()) : 1);
        }
    }

    @FXML
    private void onClickCancel() {
    }
}
