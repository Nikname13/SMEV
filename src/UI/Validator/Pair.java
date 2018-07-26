package UI.Validator;

import com.jfoenix.validation.ValidationFacade;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Pair {
    private Label mErrorLabel;
    private ValidationFacade mValidationFacade;

    public Pair(ValidationFacade facade, Label errorLabel) {
        mValidationFacade = facade;
        mErrorLabel = errorLabel;
        mErrorLabel.setLayoutX(facade.getLayoutX());
        mErrorLabel.setLayoutY(facade.getLayoutY() + 30.0);
        setDefaultPropertyLabel();
    }

    private void setDefaultPropertyLabel() {
        mErrorLabel.setTextFill(Color.web("#c94444"));
        mErrorLabel.setText("Error message");
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
}
