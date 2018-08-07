package UI.Validator;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Pair {
    private Label mErrorLabel;
    private ValidationFacade mValidationFacade;
    private JFXTextField mJFXTextField;

    public Pair(ValidationFacade facade, Label errorLabel) {
        mValidationFacade = facade;
        mErrorLabel = errorLabel;
        mErrorLabel.setLayoutX(facade.getLayoutX());
        mErrorLabel.setLayoutY(facade.getLayoutY() + 30.0);
        setDefaultPropertyLabel("");
    }

    public Pair(JFXTextField textField, Label errorLabel, String errorMessage) {
        mJFXTextField = textField;
        mErrorLabel = errorLabel;
        mErrorLabel.setLayoutX(textField.getLayoutX());
        mErrorLabel.setLayoutY(textField.getLayoutY() + 30.0);
        setDefaultPropertyLabel(errorMessage);
    }

    private void setDefaultPropertyLabel(String errorMessage) {
        mErrorLabel.setTextFill(Color.web("#c94444"));
        if (errorMessage.trim().isEmpty()) mErrorLabel.setText("Error message");
        else mErrorLabel.setText(errorMessage);
        mErrorLabel.setVisible(false);
        mErrorLabel.setWrapText(true);
    }

    public Label getErrorLabel() {
        return mErrorLabel;
    }

    public void setErrorLabel(Label errorLabel) {
        mErrorLabel = errorLabel;
    }

    public ValidationFacade getValidationFacade() {
        return mValidationFacade;
    }

    public void setValidationFacade(ValidationFacade validationFacade) {
        mValidationFacade = validationFacade;
    }

    public JFXTextField getJFXTextField() {
        return mJFXTextField;
    }

    public void setJFXTextField(JFXTextField JFXTextField) {
        mJFXTextField = JFXTextField;
    }
}
