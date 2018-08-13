package UI.Equipment.Equipment_inventory.Equipment_number;

import Presenter.EquipmentPresenter;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddInventoryNumberLog {

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
        mTextFieldNumber.setText(EquipmentPresenter.get().getInventoryNumberModel().getName());
    }

    @FXML
    private void onClickAdd() {
        if (mBaseValidator.validate()) {
            EquipmentPresenter.get().addEquipmentInventoryLogModel(mTextAreaDescription.getText(), LocalDate.now());
            close();
        }

    }

    @FXML
    private void onClickCancel() {
        EquipmentPresenter.get().cancel();
        close();
    }

    private void close() {
        Stage stage = (Stage) mAnchorPaneInventoryLog.getScene().getWindow();
        stage.close();
    }
}
