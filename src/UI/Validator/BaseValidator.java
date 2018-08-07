package UI.Validator;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class BaseValidator {

    private List<JFXTextArea> mJFXTextAreas;
    private List<JFXTextField> mJFXTextFields;
    private List<Pair> mValidationFacades, mCustomURLValidation;
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

    public void setValidationFacades(Pair... validationFacades) {
        mValidationFacades = new ArrayList<>();
        for (Pair validationFacade : validationFacades) {
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

    public void setCustomURLValidation(Pair... objects) {
        mCustomURLValidation = new ArrayList<>();
        for (Pair text : objects) {
            mCustomURLValidation.add(text);
            setJFXTextFields(text.getJFXTextField());
            text.getJFXTextField().focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (oldValue) {
                        if (!text.getJFXTextField().getText().trim().isEmpty()) {
                            if (ControllerValidator.customURLValidation(text.getJFXTextField())) {
                                text.getErrorLabel().setVisible(false);
                                text.getJFXTextField().setUnFocusColor(Color.BLACK);
                            } else {
                                text.getErrorLabel().setVisible(true);
                                text.getJFXTextField().setUnFocusColor(Color.web("#c94444"));
                            }
                        }
                    }
                }
            });
            text.getJFXTextField().textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (text.getJFXTextField().getText().trim().isEmpty()) {
                        text.getErrorLabel().setVisible(false);
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
            for (Pair validationFacade : mValidationFacades) {
                if (!ControllerValidator.validationFacade(validationFacade.getValidationFacade())) {
                    flag = false;
                    validationFacade.getErrorLabel().setVisible(true);
                }
            }
        }
        if (mCustomURLValidation != null) {
            for (Pair object : mCustomURLValidation) {
                if (!ControllerValidator.customURLValidation(object.getJFXTextField())) {
                    flag = false;
                }
            }

        }
        return flag;
    }

    public boolean validateFacade(ValidationFacade validationFacade) {
        for (Pair facade : mValidationFacades) {
            if (facade.getValidationFacade().getId().equals(validationFacade.getId())) {
                if (!ControllerValidator.validationFacade(facade.getValidationFacade())) {
                    facade.getErrorLabel().setVisible(true);
                    return false;
                } else {
                    facade.getErrorLabel().setVisible(false);
                }
            }
        }
        return true;
    }

    public void setFacadeErrorMessage(String facadeErrorMessage) {
        mFacadeErrorMessage = facadeErrorMessage;
    }
}
