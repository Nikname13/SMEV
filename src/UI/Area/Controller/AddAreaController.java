package UI.Area.Controller;

import Presenter.AreaPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddAreaController extends BaseController {

    private BaseValidator mBaseValidator=new BaseValidator();

    @FXML
    private JFXTextField mTextFieldName;

    @FXML
    private AnchorPane mAnchorPaneAddArea;

    @FXML
    public void initialize(){
        mBaseValidator.setJFXTextFields(mTextFieldName);
        initPromptText(mTextFieldName, "Введите наименование района", "Наименование района");
    }

    @FXML
    private void onClickAdd(){
        if(mBaseValidator.validate()){
            AreaPresenter.get().addArea(mTextFieldName.getText());
            close(mAnchorPaneAddArea);
        }
    }

    @FXML
    private void onClickCancel(){
        close(mAnchorPaneAddArea);
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}
