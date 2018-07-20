package UI.Provider.Controller;

import Presenter.ProviderPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddProviderController {

    @FXML
    private TextField textFieldName;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    public void initialize(){

    }

    @FXML
    private void onClickAdd(){
        new ProviderPresenter().addProvide(textFieldName.getText(),textAreaDescription.getText());
    }
}
