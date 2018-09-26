package UI.Worker.Controller;

import Model.Department.DepartmentModel;
import Model.Post.PostModel;
import Model.Worker.WorkerModel;
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

public class EditWorkerController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();
    private boolean mSelectedFlag;
    private PostModel mPostModel;
    private WorkerModel mWorkerModel;
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
    private AnchorPane mAnchorPaneEditWorker;

    public EditWorkerController() {
        mWorkerModel = WorkerPresenter.get().getWorkerModel();
    }

    @FXML
    public void initialize() {
        mBaseValidator.setJFXTextFields(mTextFieldName);
        mBaseValidator.setValidationFacades(new Pair(mFacadePost, mErrorPost, mComboBoxPost), new Pair(mFacadeDepartment, mErrorDepartment, mComboBoxDepartment));
        initTextField();
        initComboBoxPost(mComboBoxPost);
        initComboBoxDepartment(mComboBoxDepartment, true);
    }

    @Override
    protected Stage getStage() {
        return null;
    }

    @Override
    protected void initComboBoxDepartment(JFXComboBox<DepartmentModel> comboBox, boolean isSelectionItem) {
        super.initComboBoxDepartment(comboBox, isSelectionItem);
        comboBox.setItems(WorkerPresenter.get().getObservableDepartment());
        comboBox.getSelectionModel().select(mWorkerModel.getDepartmentModel());
    }

    @Override
    protected void initComboBoxPost(JFXComboBox<PostModel> comboBoxPost) {
        super.initComboBoxPost(comboBoxPost);
        comboBoxPost.setItems(WorkerPresenter.get().getObservablePost());
        mComboBoxPost.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedPost()));
        mComboBoxPost.getSelectionModel().select(mWorkerModel.getPost());
    }


    private void selectedPost() {
        if (mComboBoxPost.getSelectionModel().selectedIndexProperty().get() != -1) {
            setSelectedPost(true);
            mPostModel = mComboBoxPost.getValue();
        }
    }

    private void initTextField() {
        mTextFieldName.setText(mWorkerModel.getName());
    }

    @FXML
    private void onClickAdd() {
        if (mBaseValidator.validate()) {
            if (isSelectedPost()) {
                WorkerPresenter.get().editWorker(mTextFieldName.getText(), mPostModel, mComboBoxDepartment.getValue());
            } else {
                WorkerPresenter.get().editWorker(mTextFieldName.getText(), mComboBoxPost.getValue(), mComboBoxDepartment.getValue());
            }
            close(mAnchorPaneEditWorker);
        }
    }

    @FXML
    private void onClickCancel() {
        close(mAnchorPaneEditWorker);
    }
}
