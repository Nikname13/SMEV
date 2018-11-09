package UI.Inventory_number.Controller;

import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import Presenter.InventoryNumberPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AddInventoryNumberController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();

    @FXML
    private JFXTextField mTextField;
    @FXML
    private JFXComboBox<SupplyModel> mComboBoxSupply;
    @FXML
    private ValidationFacade mFacadeSupply;
    @FXML
    private Label mErrorSupply;
    @FXML
    private JFXTextArea mTextAreaDescription;
    @FXML
    private JFXCheckBox mCheckBoxGroup;
    @FXML
    private AnchorPane mAnchorPaneInventoryNumber;

    @FXML
    public void initialize(){
        mBaseValidator.setJFXTextFields(mTextField);
        mBaseValidator.setValidationFacades(new Pair(mFacadeSupply, mErrorSupply));
        initPromptText(mTextField, "Введите инвентарный номер", "Инвентарный номер");
        initComboBoxSupply(mComboBoxSupply, false, "Выберите поставку", "Номер поставки");
    }

    @Override
    protected void initComboBoxSupply(JFXComboBox<SupplyModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        super.initComboBoxSupply(comboBox, isSelectionItem, promptText, label);
        comboBox.setItems(Supplys.get().getObsEntityList());
    }

    @Override
    public void destroy() {

    }

    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            InventoryNumberPresenter.get().addInventoryNumber(mTextField.getText(), mComboBoxSupply.getValue(), mCheckBoxGroup.isSelected(), mTextAreaDescription.getText());
            close(mAnchorPaneInventoryNumber);
        }
    }

    @FXML
    private void onClickCancel() {
        close(mAnchorPaneInventoryNumber);
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}
