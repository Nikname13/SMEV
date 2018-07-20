package UI.Validator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class TextValidator {

    private static RequiredFieldValidator getRequiredValidator(){
        return  new RequiredFieldValidator();
    }

    public static List<JFXTextField> setTextFieldValidator(JFXTextField... textField) {

        List<JFXTextField> response=new ArrayList<>();
        for (JFXTextField text : textField) {
            RequiredFieldValidator validator = getRequiredValidator();
            response.add(text);
            text.getValidators().add(validator);
            validator.setMessage("Поле не может быть пустым");
            text.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue) {
                        text.validate();
                    }
                }
            });
        }
        return response;
    }

    public static boolean validationFacade(ValidationFacade... facades){
        boolean flag=true;
        for(ValidationFacade facade:facades) {
            for (ValidatorBase validator : facade.getValidators()) {
                if (validator.getSrcControl() == null) {
                    validator.setSrcControl(facade.getControl());
                }
                validator.validate();
                if(validator.getHasErrors()){
                    flag=false;
                }
                validator.setSrcControl(null);
            }
        }
        return flag;
    }
}
