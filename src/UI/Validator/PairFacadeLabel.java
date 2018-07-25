package UI.Validator;

import com.jfoenix.validation.ValidationFacade;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class PairFacadeLabel {
    private Label mErrorLabel;
    private ValidationFacade mValidationFacade;

    public PairFacadeLabel(ValidationFacade validationFacade, Label label) {
        mValidationFacade = validationFacade;
        mErrorLabel = label;
        mErrorLabel.setTextFill(Color.web("#c94444"));
        mErrorLabel.setText("Error message");
        mErrorLabel.setLayoutX(mValidationFacade.getLayoutX());
        mErrorLabel.setLayoutY(mValidationFacade.getLayoutY() + 30.0);
        mErrorLabel.setVisible(false);
        System.out.println("y= " + mValidationFacade.getLayoutY() + " x= " + mValidationFacade.getLayoutX());

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
