package UI.Worker.Controller;

import Model.Department.DepartmentModel;
import Presenter.WorkerPresenter;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddWorkerController {

    private BaseValidator mBaseValidator = new BaseValidator();

    @FXML
    private JFXTextField mTextFieldName, mTextFieldPost;

    @FXML
    private JFXComboBox<DepartmentModel> mComboBoxDepartment;

    @FXML
    private ValidationFacade mFacadeDepartment;

    @FXML
    private Label mErrorDepartment;

    @FXML
    private AnchorPane mAnchorPaneWorker;

    @FXML
    public void initialize(){
        mBaseValidator.setValidationFacades(new Pair(mFacadeDepartment, mErrorDepartment));
        mBaseValidator.setJFXTextFields(mTextFieldName, mTextFieldPost);
        initComboBoxDepartment();

    }

    private void initComboBoxDepartment() {
        mComboBoxDepartment.setCellFactory(p -> new ListCell<DepartmentModel>() {
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getName());
                }else{
                    setText(null);
                }
            }
        });
        mComboBoxDepartment.setItems(WorkerPresenter.get().getObservableDepartment());
    }

    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            WorkerPresenter.get().addWorker(mTextFieldName.getText(), mTextFieldPost.getText(), mComboBoxDepartment.getValue());
            close();
        }
    }

    @FXML
    private void onClickCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) mAnchorPaneWorker.getScene().getWindow();
        stage.close();
    }
}
