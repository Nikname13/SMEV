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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddWorkerController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();
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
        initPromptText(mTextFieldName, "Введите ФИО сотрудника", "ФИО");
        initComboBoxDepartment(mComboBoxDepartment, false, "Выберите отдел", "Отдел");
        initComboBoxPost(mComboBoxPost, "Выберите должность", "Должность");
    }

    @Override
    protected Stage getStage() {
        return null;
    }

    @Override
    protected void initComboBoxDepartment(JFXComboBox<DepartmentModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        super.initComboBoxDepartment(comboBox, isSelectionItem, promptText, label);
        comboBox.setItems(WorkerPresenter.get().getObservableDepartment());
    }

    @Override
    protected void initComboBoxPost(JFXComboBox<PostModel> comboBoxPost, String promptText, String label) {
        super.initComboBoxPost(comboBoxPost, promptText, label);
        comboBoxPost.setItems(WorkerPresenter.get().getObservablePost());
        mComboBoxPost.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedPost()));
    }

    @Override
    public void destroy() {

    }

    private void selectedPost() {
        if (mComboBoxPost.getSelectionModel().selectedIndexProperty().get() != -1) {
            setSelectedPost(true);
            mPostModel = mComboBoxPost.getValue();
        }
    }


    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            if (isSelectedPost()) {
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
