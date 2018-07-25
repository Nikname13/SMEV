package UI.Validator;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class BaseValidator {

    private List<JFXTextArea> mJFXTextAreas;
    private List<JFXTextField> mJFXTextFields;
    private List<PairFacadeLabel> mValidationFacades;
    private String mFacadeErrorMessage = "Необходимо выбрать значение";

    public void setJFXTextAreas(JFXTextArea... textAreas) {
        mJFXTextAreas = new ArrayList<>();
        for (JFXTextArea textArea : textAreas) {
            mJFXTextAreas.add(textArea);
            ControllerValidator.setTextAreaValidator(textArea);
        }
    }

    public void setJFXTextFields(JFXTextField... textFields) {
        mJFXTextFields = new ArrayList<>();
        for (JFXTextField textField : textFields) {
            mJFXTextFields.add(textField);
            ControllerValidator.setTextFieldValidator(textField);
        }

    }

    public void setValidationFacades(PairFacadeLabel... validationFacades) {
        mValidationFacades = new ArrayList<>();
        for (PairFacadeLabel validationFacade : validationFacades) {
            RequiredFieldValidator validator = new RequiredFieldValidator();
            validator.setMessage(mFacadeErrorMessage);
            ValidationFacade facade = validationFacade.getValidationFacade();
            facade.getValidators().add(validator);
            validationFacade.getErrorLabel().setText(mFacadeErrorMessage);
            mValidationFacades.add(validationFacade);
            facade.getControl().focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (oldValue) {
                        if (ControllerValidator.validationFacade(facade)) {
                            validationFacade.getErrorLabel().setVisible(false);
                        } else {
                            validationFacade.getErrorLabel().setVisible(true);
                        }
                    }
                }
            });
        }
    }

    public boolean validate() {
        boolean flag = true;
        if (mJFXTextFields != null) {
            for (JFXTextField textField : mJFXTextFields) {
                if (!textField.validate()) flag = false;
            }
        }
        if (mJFXTextAreas != null) {
            for (JFXTextArea textArea : mJFXTextAreas) {
                if (!textArea.validate()) flag = false;
            }
        }
        if (mValidationFacades != null) {
            for (PairFacadeLabel validationFacade : mValidationFacades) {
                if (!ControllerValidator.validationFacade(validationFacade.getValidationFacade())) {
                    flag = false;
                    validationFacade.getErrorLabel().setVisible(true);
                }
            }
        }
        return flag;
    }

    public void setFacadeErrorMessage(String facadeErrorMessage) {
        mFacadeErrorMessage = facadeErrorMessage;
    }
}
