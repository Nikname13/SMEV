package UI.Equipment.Equipment_inventory.Equipment_state.Controller;

import Presenter.EquipmentInventoryPresenter;
import Presenter.EquipmentPresenter;
import UI.BaseController;
import UI.Validator.ControllerValidator;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddEquipmentStateLog extends BaseController {

    @FXML
    private Label labelData;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private JFXTextField mTextFieldState;

    @FXML
    private AnchorPane mAnchorPaneEquipmentState;

    @FXML
    public void initialize(){
        labelData.setText(String.valueOf(LocalDate.now()));
        mTextFieldState.setText(EquipmentInventoryPresenter.get().getStateModel().getName());
        ControllerValidator.setTextAreaValidator(mTextAreaDescription);
    }

    @FXML
    private void onClickAdd(){
        if (mTextAreaDescription.validate()) {
            EquipmentInventoryPresenter.get().addEquipmentStateLog(mTextFieldState.getText(), mTextAreaDescription.getText(), LocalDate.now());
            close(mAnchorPaneEquipmentState);
        }
    }

    @FXML
    private void onClickCancel() {
        EquipmentPresenter.get().cancel();
        close(mAnchorPaneEquipmentState);
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}
