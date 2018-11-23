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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditWorkerController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();
    private PostModel mPostModel;
    private WorkerModel mWorkerModel;
    private boolean mPostIsSelected;
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
        mPostIsSelected = false;
        System.out.println("constructor");
        mWorkerModel = WorkerPresenter.get().getWorkerModel();
    }

    @FXML
    public void initialize() {
        mBaseValidator.setJFXTextFields(mTextFieldName);
        mBaseValidator.setValidationFacades(new Pair(mFacadePost, mErrorPost, mComboBoxPost), new Pair(mFacadeDepartment, mErrorDepartment, mComboBoxDepartment));
        initTextField();
        initComboBox();

    }

    private void initComboBox() {
        initJFXComboBox(new PostModel(), mComboBoxPost, false, "Выберите или введите должность", "Должность");
        initJFXComboBox(new DepartmentModel(), mComboBoxDepartment, true, "Выберите отдел", "Отдел");
        mComboBoxDepartment.setItems(WorkerPresenter.get().getObservableDepartment());
        mComboBoxDepartment.getSelectionModel().select(mWorkerModel.getDepartmentModel());
        mComboBoxPost.setItems(WorkerPresenter.get().getObservablePost());
        mComboBoxPost.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedPost()));
        mComboBoxPost.getSelectionModel().select(mWorkerModel.getPost());
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

    private void initTextField() {
        mTextFieldName.setText(mWorkerModel.getName());
    }

    @FXML
    private void onClickAdd() {
        if (mBaseValidator.validate()) {
            if (mPostIsSelected) {
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
