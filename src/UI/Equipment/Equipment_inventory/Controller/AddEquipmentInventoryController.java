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
    private JFXComboBox<InventoryNumberModel> mComboBoxInventoryNumber;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private ValidationFacade mFacadeNumber, mFacadeState;

    @FXML
    private Label mErrorNumber, mErrorState;

    @FXML
    private AnchorPane mAnchorPaneEquipmentInventory;

    public void initialize(){
        initComboBox();
        initTextField();
        mBaseValidator.setValidationFacades(new Pair(mFacadeNumber, mErrorNumber, mComboBoxInventoryNumber), new Pair(mFacadeState, mErrorState, mComboBoxState));
    }

    private void initTextField() {
        onlyNumber(mTextFieldCount, 100);
        onlyNumber(mTextFieldGuaranty, 100);
        mBaseValidator.setJFXTextFields(mTextFieldCount, mTextFieldGuaranty);
    }

    private void initComboBox() {
        initJFXComboBox(new InventoryNumberModel(), mComboBoxInventoryNumber, false, "Выберите номер", "Номер");
        initJFXComboBox(new StateModel(), mComboBoxState, false, "Выберите состояние", "Состояние");
        mComboBoxInventoryNumber.setItems(EquipmentPresenter.get().getObservableAvailableInventory());
        mComboBoxInventoryNumber.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedInventory(newValue)));
        mComboBoxState.setItems(EquipmentPresenter.get().getObservableState());
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneEquipmentInventory.getScene().getWindow();
    }

    private void selectedInventory(InventoryNumberModel inventory){
        if (inventory != null) {
            mTextFieldCount.setText("");
            mTextFieldCount.setVisible(inventory.isGroup());
                resizeWidthStage();
        }
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
                    mComboBoxInventoryNumber.getValue(),
                    Integer.parseInt(mTextFieldGuaranty.getText()),
                    mTextAreaDescription.getText(),
                    mEquipment,
                    mComboBoxState.getValue(),
                    mComboBoxInventoryNumber.getValue().isGroup() ? Integer.parseInt(mTextFieldCount.getText()) : 1);
            close(mAnchorPaneEquipmentInventory);
        }
    }

    @FXML
    private void onClickCancel() {
        close(mAnchorPaneEquipmentInventory);
    }
}
