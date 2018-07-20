package UI.Parameter.Controller;

import Presenter.ParametersPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddParameterController {

    @FXML
    private TextArea valuesParameter;

    @FXML
    private TextField nameParameter;

    @FXML
    private RadioButton isValue;

    @FXML
    public void initialize() {

        System.out.println("init");

    }

    @FXML
    private void onClickOk() {
        if (isValue.isSelected()) {
            List<Object> values = new ArrayList<>();
            String s[] = valuesParameter.getText().split("\n");
            for (int i = 0; i < s.length; i++) {
                values.add(s[i]);
            }
            if (valuesParameter.getText().length() == 0) isValue.setSelected(false);
            new ParametersPresenter().addParameter(nameParameter.getText(), isValue.isSelected(), values);
        } else new ParametersPresenter().addParameter(nameParameter.getText(), isValue.isSelected(), null);
    }

    @FXML
    private void onClickCancel() {
    }

}
