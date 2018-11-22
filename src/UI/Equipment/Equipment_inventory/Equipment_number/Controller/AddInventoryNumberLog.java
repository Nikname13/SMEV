package UI.Equipment.Equipment_inventory.Equipment_number.Controller;

import Presenter.EquipmentInventoryPresenter;
import Presenter.EquipmentPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddInventoryNumberLog extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();

    @FXML
    private Label mLabelData;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private JFXTextField mTextFieldNumber;

    @FXML
    private AnchorPane mAnchorPaneInventoryLog;

    public void initialize() {
        mLabelData.setText(String.valueOf(LocalDate.now()));
        mBaseValidator.setJFXTextAreas(mTextAreaDescription);
        mTextFieldNumber.setText(EquipmentInventoryPresenter.get().getInventoryNumberModel().getName());
    }

    @FXML
    private void onClickAdd() {
        if (mBaseValidator.validate()) {
            EquipmentInventoryPresenter.get().addEquipmentInventoryLogModel(mTextAreaDescription.getText(), LocalDate.now());
            close(mAnchorPaneInventoryLog);
        }

    }

    @FXML
    private void onClickCancel() {
        EquipmentPresenter.get().cancel();
        close(mAnchorPaneInventoryLog);
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneInventoryLog.getScene().getWindow();
    }
}
