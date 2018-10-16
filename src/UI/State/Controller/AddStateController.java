package UI.State.Controller;

import Presenter.StatePresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddStateController extends BaseController {

    private BaseValidator mBaseValidator=new BaseValidator();

    @FXML
    private JFXTextField mTextFieldName;

    @FXML
    private AnchorPane mAnchorPaneAddState;

    @FXML
    public void initialize(){
        mBaseValidator.setJFXTextFields(mTextFieldName);
        initTextField(mTextFieldName, "Введите состояние", "Состояние");
    }

    @FXML
    private void onClickAdd(){
        if(mBaseValidator.validate()) {
            new StatePresenter().addState(mTextFieldName.getText());
            close(mAnchorPaneAddState);
        }
    }

    @FXML
    private  void onClickCancel(){
        close(mAnchorPaneAddState);
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}
