package UI.Movement.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Worker.WorkerModel;
import Presenter.EquipmentPresenter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.validation.ValidationFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.StackPane;


public class AddMovementController {

    @FXML
    private JFXComboBox<DepartmentModel> mComboBoxDepartmentFrom, mComboBoxDepartmentTo;
    @FXML
    private JFXComboBox<WorkerModel> mComboBoxWorkerTo, mComboBoxWorkerFrom;
    @FXML
    private JFXTextArea mTextAreaBase;
    @FXML
    private ValidationFacade mFacadeWorkerFrom, mFacadeWorkerTo, mFacadeDepartmentTo, mFacadeDepartmentFrom;
    @FXML
    private Label mErrorWorkerFrom, mErrorWorkerTo, mErrorDepartmentTo, mErrorDepartmentFrom;
    @FXML
    private TreeTableView<EquipmentInventoryModel> mTreeTableEquipment;
    @FXML
    private TreeTableColumn<EquipmentInventoryModel, String> mNameColumn, mInventoryNumberColumn;
    @FXML
    private StackPane mStackPaneAddMovement;

    @FXML
    public void initialize(){
    }

    @FXML
    private void onClickAdd(){
    }

    @FXML
    private void onClickCancel() {
        EquipmentPresenter.get().cancel();
    }
}
