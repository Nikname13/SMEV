package UI.Parameter.Controller;

import Presenter.ParameterPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddParameterController extends BaseController {

    private BaseValidator mBaseValidator=new BaseValidator();

    @FXML
    private JFXTextField mNameParameter;

    @FXML
    private AnchorPane mAnchorPaneAddParameter;

    @FXML
    public void initialize() {
        mBaseValidator.setJFXTextFields(mNameParameter);
        initPromptText(mNameParameter, "Введите наименование параметра", "Наименование параметра");
        System.out.println("init");
    }

    @FXML
    private void onClickAdd() {
        if(mBaseValidator.validate()){
            ParameterPresenter.get().addParameter(mNameParameter.getText());
            close(mAnchorPaneAddParameter);
        }
    }

    @FXML
    private void onClickCancel() {
        close(mAnchorPaneAddParameter);
    }

    @Override
    protected Stage getStage() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
