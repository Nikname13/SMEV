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
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
        if(mTextFieldURL.validate() &&
        mTextAreaDescription.validate() &&
        dateValidate()){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
                //DepartmentPresenter.get().addPurchase(mTextFieldURL.getText(),mTextAreaDescription.getText(), LocalDate.now());
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
