package UI.Department.Controller;

import Presenter.DepartmentPresenter;
import UI.Validator.ControllerValidator;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;


public class AddPurchaseController {

    @FXML
    private JFXTextField mTextFieldURL;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private JFXDatePicker mDatePicker;

    @FXML
    private Label mDateErrorMessage;

    @FXML
    private ValidationFacade mDateValidationFacade;

    @FXML
    private AnchorPane mAnchorPanePurchase;

    @FXML
    public void initialize(){
        mDatePicker.setValue(LocalDate.now());
        RequiredFieldValidator validator=new RequiredFieldValidator();
        validator.setMessage("Необходимо указать дату");
        mDateValidationFacade.getValidators().add(validator);
        ControllerValidator.setTextFieldValidator(mTextFieldURL);
        ControllerValidator.setTextAreaValidator(mTextAreaDescription);
        mDatePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(oldValue);
                System.out.println(newValue);
               if(oldValue)dateValidate();
            }
        });
    }

    @FXML
    private void onClickAdd(){
        mTextFieldURL.validate();
        mTextAreaDescription.validate();
        dateValidate();
        if(mTextFieldURL.validate() &&
        mTextAreaDescription.validate() &&
        dateValidate()){
            System.out.println("true");
            DepartmentPresenter.get().addPurchase(mTextFieldURL.getText().trim(), mTextAreaDescription.getText().trim(), mDatePicker.getValue());
        }else{
            System.out.println("false");

        }
    }

    private boolean dateValidate(){
        if(ControllerValidator.validationFacade(mDateValidationFacade)){
            mDateErrorMessage.setVisible(false);
            return true;
        }
        else{
            mDateErrorMessage.setVisible(true);
            return false;
        }
    }
}
