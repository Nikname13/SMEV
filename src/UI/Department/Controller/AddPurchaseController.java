package UI.Department.Controller;

import Presenter.DepartmentPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;


public class AddPurchaseController {

    @FXML
    private TextField textFieldURL;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private void onClickAdd(){
        DepartmentPresenter.get().addPurchase(textFieldURL.getText(),textAreaDescription.getText(), LocalDate.now());
    }
}
