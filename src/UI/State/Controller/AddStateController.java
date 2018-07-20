package UI.State.Controller;

import Presenter.StatePresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddStateController {

    @FXML
    private TextField textFieldName;

    @FXML
    public void initialize(){

    }

    @FXML
    private void onClickAdd(){
        new StatePresenter().addState(textFieldName.getText());
    }
}
