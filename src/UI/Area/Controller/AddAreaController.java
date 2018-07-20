package UI.Area.Controller;

import Presenter.AreaPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddAreaController {

    @FXML
    private TextField textFieldName;

    @FXML
    public void initialize(){

    }

    @FXML
    private void onClickAdd(){
        new AreaPresenter().addArea(textFieldName.getText());
    }
}
