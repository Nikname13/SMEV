package UI.Equipment.Equipment_inventory.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Worker.WorkerModel;
import Presenter.EquipmentPresenter;
import UI.Validator.ControllerValidator;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;

public class MoveEquipmentInventoryController {

    private EquipmentInventoryModel mEquipment;
    private DepartmentModel mDepartment;
    private WorkerModel mWorkerTo, mWorkerFrom;

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

    public MoveEquipmentInventoryController() {
        mEquipment = EquipmentPresenter.get().getEquipmentInventoryModel();
        mDepartment = EquipmentPresenter.get().getDepartmentModel();
    }

    @FXML
    public void initialize(){
        ControllerValidator.validationFacade(mFacadeDepartmentTo, mFacadeWorkerTo, mFacadeWorkerFrom);
        mTextFieldDepartment.setText(mEquipment.getDepartmentModel().getName());
        mComboBoxDepartment.setCellFactory(p -> new ListCell<DepartmentModel>() {
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        mComboBoxDepartment.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getName());
                }else {
                    setText(null);
                }
            }
        });
        mComboBoxDepartment.setItems(EquipmentPresenter.get().getObservableDepartment());
        mComboBoxDepartment.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment(newValue)));
        mComboBoxWorkerFrom.setCellFactory((p -> new ListCell<WorkerModel>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getName());
                }else {
                    setText(null);
                }
            }
        }));
        mComboBoxWorkerFrom.setItems(mEquipment.getDepartmentModel().getObsWorkerList());
        mComboBoxWorkerFrom.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedWorkerFrom(newValue)));
    }

    private void selectedDepartment(DepartmentModel department){
        mDepartment=department;
        mComboBoxWorkerTo.setCellFactory(p -> new ListCell<WorkerModel>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getName());
                }else {
                    setText(null);
                }
            }
        });
        mComboBoxWorkerTo.setItems(department.getObsWorkerList());
        mComboBoxWorkerTo.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedWorkerTo(newValue)));
    }

    private void selectedWorkerTo(WorkerModel worker){
        mWorkerTo =worker;
    }

    private void selectedWorkerFrom(WorkerModel worker){
        mWorkerFrom=worker;
    }

    @FXML
    private void onClickMove(){
        EquipmentPresenter.get().moveEquipmentInventory(mEquipment, mDepartment, mWorkerFrom, mWorkerTo, mTextAreaBase.getText());
    }
}
