package UI.Department.Controller;

import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;


public class AddPurchaseController {

    @FXML
    private JFXTextField mTextFieldURL;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private JFXDatePicker mDatePicker;

    @FXML
    private Label mErrorDate;

    @FXML
    private ValidationFacade mFacadeDate;

    @FXML
    private AnchorPane mAnchorPanePurchase;

    private BaseValidator mBaseValidator = new BaseValidator();

    @FXML
    public void initialize(){
        mDatePicker.setValue(LocalDate.now());
        mBaseValidator.setJFXTextFields(mTextFieldURL);
        mBaseValidator.setJFXTextAreas(mTextAreaDescription);
        Pair pair = new Pair(mFacadeDate, mErrorDate);
        pair.getErrorLabel().setLayoutY(pair.getErrorLabel().getLayoutY() + 5.0);
        mBaseValidator.setFacadeErrorMessage("Необходимо указать дату");
        mBaseValidator.setValidationFacades(pair);
    }

    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            System.out.println("true");
            System.out.println(mTextFieldURL.getText());
            //DepartmentPresenter.get().addPurchase(mTextFieldURL.getText().trim(), mTextAreaDescription.getText().trim(), mDatePicker.getValue());
            close();
        }
    }

    @FXML
    private void onClickCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) mAnchorPanePurchase.getScene().getWindow();
        stage.close();
    }

}
