package UI.Provider.Controller;

import Presenter.ProviderPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddProviderController extends BaseController {

    private BaseValidator mBaseValidator=new BaseValidator();

    @FXML
    private JFXTextField mTextFieldName;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private AnchorPane mAnchorPaneAddProvider;

    @FXML
    public void initialize(){
        mBaseValidator.setJFXTextFields(mTextFieldName);
    }

    @FXML
    private void onClickAdd(){
        if(mBaseValidator.validate()) {
            ProviderPresenter.get().addProvider(mTextFieldName.getText(), mTextAreaDescription.getText());
            close(mAnchorPaneAddProvider);
        }
    }

    @FXML
    private void onClickCancel(){
        close(mAnchorPaneAddProvider);
    }

    @Override
    protected Stage getStage() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
