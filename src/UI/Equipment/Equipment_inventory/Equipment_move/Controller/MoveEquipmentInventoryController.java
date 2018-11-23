package UI.Equipment.Equipment_inventory.Equipment_move.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Worker.WorkerModel;
import Presenter.EquipmentInventoryPresenter;
import Presenter.EquipmentPresenter;
import Presenter.MovementPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MoveEquipmentInventoryController extends BaseController {

    private EquipmentInventoryModel mEquipment;
    private DepartmentModel mDepartment;
    private BaseValidator mBaseValidator = new BaseValidator();

    @FXML
    private JFXTextField mTextFieldDepartment;
    @FXML
    private JFXComboBox<DepartmentModel> mComboBoxDepartment;
    @FXML
    private JFXComboBox<WorkerModel> mComboBoxWorkerTo, mComboBoxWorkerFrom;
    @FXML
    private JFXTextArea mTextAreaBase;
    @FXML
    private ValidationFacade mFacadeWorkerFrom, mFacadeWorkerTo, mFacadeDepartmentTo;
    @FXML
    private Label mErrorWorkerFrom, mErrorWorkerTo, mErrorDepartmentTo;
    @FXML
    private AnchorPane mAnchorPaneMoveEquipment;

    public MoveEquipmentInventoryController() {
        mEquipment = EquipmentInventoryPresenter.get().getEquipmentInventoryModel();
        mDepartment = EquipmentPresenter.get().getDepartmentModel();
    }

    @FXML
    public void initialize() {
        mBaseValidator.setValidationFacades(
                new Pair(mFacadeWorkerFrom, mErrorWorkerFrom, mComboBoxWorkerFrom),
                new Pair(mFacadeWorkerTo, mErrorWorkerTo, mComboBoxWorkerTo),
                new Pair(mFacadeDepartmentTo, mErrorDepartmentTo, mComboBoxDepartment));
        mBaseValidator.setJFXTextAreas(mTextAreaBase);


        initComboBox();

        setWorker();

        mTextFieldDepartment.setText(mEquipment.getDepartmentModel().getName());
    }

    private void initComboBox() {
        initJFXComboBox(new WorkerModel(), mComboBoxWorkerFrom, true, "Выберите сотрудника", "Сотрудник");
        initJFXComboBox(new WorkerModel(), mComboBoxWorkerTo, true, "Выберите сотрудника", "Сотрудник");
        initJFXComboBox(new DepartmentModel(), mComboBoxDepartment, true, "Выберите отдел", "Отдел");
        mComboBoxDepartment.setItems(EquipmentPresenter.get().getObservableDepartment());
        mComboBoxDepartment.getSelectionModel().select(EquipmentPresenter.get().getDepartmentModel());
        mComboBoxDepartment.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment(newValue)));
    }

    private void setWorker() {
        mComboBoxWorkerTo.setItems(mDepartment.getObsWorkerList());
        mComboBoxWorkerTo.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            mBaseValidator.validateFacade(mFacadeWorkerTo);
        }));
        setWorkerTo();
        mComboBoxWorkerFrom.setItems(mEquipment.getDepartmentModel().getObsWorkerList());
        mComboBoxWorkerFrom.getSelectionModel().selectFirst();
    }

    private void setWorkerTo() {
        mComboBoxWorkerTo.getSelectionModel().selectFirst();
    }

    private void selectedDepartment(DepartmentModel department) {
        System.out.println("select department");
        mComboBoxWorkerTo.setItems(department.getObsWorkerList());
        mComboBoxWorkerTo.getEditor().requestFocus();
        setWorkerTo();
    }

    @FXML
    private void onClickMove() {
        if (mBaseValidator.validate()) {
            MovementPresenter.get().moveEquipmentInventory(
                    mEquipment,
                    mDepartment,
                    mComboBoxWorkerFrom.getValue(),
                    mComboBoxWorkerTo.getValue(),
                    mTextAreaBase.getText());
            close(mAnchorPaneMoveEquipment);
        }
    }

    @FXML
    private void onClickCancel() {
        EquipmentPresenter.get().cancel();
        close(mAnchorPaneMoveEquipment);
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}
