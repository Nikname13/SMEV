package UI.Validator;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerValidator {

    private static RequiredFieldValidator getRequiredValidator() {
        return new RequiredFieldValidator();
    }

    public static List<JFXTextField> setTextFieldValidator(JFXTextField... textField) {

        List<JFXTextField> response = new ArrayList<>();
        for (JFXTextField text : textField) {
            RequiredFieldValidator validator = getRequiredValidator();
            response.add(text);
            text.getValidators().add(validator);
            //System.out.println("width " + text.getPrefWidth() + "id " + text.getId());
            if (text.getPrefWidth() >= 160) {
                validator.setMessage("Поле не может быть пустым");
            } else {
                //validator.setIcon(new Image);
            }
            text.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (oldValue) {
                        text.setText(text.getText().trim());
                        text.validate();
                    }
                }
            });
            text.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    text.validate();
                }
            });
        }
        return response;
    }

    public static boolean validationFacade(ValidationFacade facade) {
        boolean flag = true;
        for (ValidatorBase validator : facade.getValidators()) {
            if (validator.getSrcControl() == null) {
                validator.setSrcControl(facade.getControl());
            }
            validator.validate();
            if (validator.getHasErrors()) {
                flag = false;
            }
            validator.setSrcControl(null);
        }
        return flag;
    }

    public static List<JFXTextArea> setTextAreaValidator(JFXTextArea... textArea) {

        List<JFXTextArea> response = new ArrayList<>();
        for (JFXTextArea text : textArea) {
            RequiredFieldValidator validator = getRequiredValidator();
            response.add(text);
            text.getValidators().add(validator);
            validator.setMessage("Поле не может быть пустым");
            text.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (oldValue) {
                        text.setText(text.getText().trim());
                        text.validate();
                    }
                }
            });
        }
        return response;
    }

    public static boolean customURLValidation(JFXTextField textField) {
        Pattern p = Pattern.compile("^(https?:\\/\\/)?([\\w\\.]+)\\.([a-z]{2,6}\\.?)(\\/[\\w\\.]*)*\\/?$");
        Matcher m = p.matcher(textField.getText());
        if (!m.matches()) {
            System.out.println("неверный URL");
            return false;
        }
        return true;
    }
}
