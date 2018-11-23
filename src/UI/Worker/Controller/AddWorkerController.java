package UI.Worker.Controller;

import Model.Department.DepartmentModel;
import Model.Post.PostModel;
import Presenter.WorkerPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddWorkerController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();
    private boolean mPostIsSelected;
    private PostModel mPostModel;

    @FXML
    private JFXTextField mTextFieldName;

    @FXML
    private JFXComboBox<PostModel> mComboBoxPost;

    @FXML
    private JFXComboBox<DepartmentModel> mComboBoxDepartment;

    @FXML
    private ValidationFacade mFacadeDepartment, mFacadePost;

    @FXML
    private Label mErrorDepartment, mErrorPost;

    @FXML
    private AnchorPane mAnchorPaneWorker;

    @FXML
    public void initialize(){
        mBaseValidator.setValidationFacades(new Pair(mFacadeDepartment, mErrorDepartment, mComboBoxDepartment), new Pair(mFacadePost, mErrorPost, mComboBoxPost));
        mBaseValidator.setJFXTextFields(mTextFieldName);
        initComboBox();
        initPromptText(mTextFieldName, "Введите ФИО сотрудника", "ФИО");

    }

    private void initComboBox() {
        initJFXComboBox(new DepartmentModel(), mComboBoxDepartment, false, "Выберите отдел", "Отдел");
        initJFXComboBox(new PostModel(), mComboBoxPost, false, "Выберите должность", "Должность");
        mComboBoxDepartment.setItems(WorkerPresenter.get().getObservableDepartment());
        mComboBoxPost.setItems(WorkerPresenter.get().getObservablePost());
        mComboBoxPost.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedPost()));
        mComboBoxPost.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mPostIsSelected = false;
            }
        });
    }

    @Override
    protected Stage getStage() {
        return null;
    }

    private void selectedPost() {
        if (mComboBoxPost.getSelectionModel().selectedIndexProperty().get() != -1) {
            mPostIsSelected = true;
            mPostModel = mComboBoxPost.getValue();
        }
    }


    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            if (mPostIsSelected) {
                WorkerPresenter.get().addWorker(mTextFieldName.getText(), mPostModel, mComboBoxDepartment.getValue());
            } else {
                WorkerPresenter.get().addWorker(mTextFieldName.getText(), mComboBoxPost.getValue(), mComboBoxDepartment.getValue());
            }
            close(mAnchorPaneWorker);
        }
    }

    @FXML
    private void onClickCancel() {
        close(mAnchorPaneWorker);
    }
}
