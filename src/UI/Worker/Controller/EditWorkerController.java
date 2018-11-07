package UI.Worker.Controller;

import Model.Department.DepartmentModel;
import Model.Post.PostModel;
import Model.Worker.WorkerModel;
import Presenter.WorkerPresenter;
import Service.IUpdateUI;
import Service.ListenersService;
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

public class EditWorkerController extends BaseController implements IUpdateUI {

    private BaseValidator mBaseValidator = new BaseValidator();
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
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
        mBaseValidator.setJFXTextFields(mTextFieldName);
        mBaseValidator.setValidationFacades(new Pair(mFacadePost, mErrorPost, mComboBoxPost), new Pair(mFacadeDepartment, mErrorDepartment, mComboBoxDepartment));
        initTextField();
        initComboBoxPost(mComboBoxPost, "Выберите или введите должность", "Должность");
        initComboBoxDepartment(mComboBoxDepartment, true, "Выберите отдел", "Отдел");
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


    private void selectedPost() {
        if (mComboBoxPost.getSelectionModel().selectedIndexProperty().get() != -1) {
            setSelectedPost(true);
            mPostModel = mComboBoxPost.getValue();
        }
    }

    private void initTextField() {

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

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(WorkerModel.class.getName())) {
            System.out.println("ololo update");
            mWorkerModel = WorkerPresenter.get().getWorkerModel();
            mComboBoxPost.getSelectionModel().select(mWorkerModel.getPost());
            mComboBoxDepartment.getSelectionModel().select(mWorkerModel.getDepartmentModel());
            mTextFieldName.setText(mWorkerModel.getName());
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }
}
