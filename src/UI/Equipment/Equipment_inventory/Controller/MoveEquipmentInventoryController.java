package UI.Equipment.Equipment_inventory.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Worker.WorkerModel;
import Presenter.EquipmentPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;

public class MoveEquipmentInventoryController {

    private EquipmentInventoryModel mEquipment;
    private DepartmentModel mDepartment;
    private WorkerModel mWorkerTo, mWorkerFrom;

    public MoveEquipmentInventoryController(){
        mEquipment=EquipmentPresenter.get().getEquipmentInventoryModel();
    }

    @FXML
    private Label labelDepartment;

    @FXML
    private ComboBox<DepartmentModel> comboBoxDepartment;

    @FXML
    private ComboBox<WorkerModel> comboBoxWorkerTo, comboBoxWorkerFrom;

    @FXML
    private TextArea textAreaBase;

    @FXML
    public void initialize(){
        labelDepartment.setText(mEquipment.getDepartmentModel().getName());
        comboBoxDepartment.setCellFactory(p-> new ListCell<DepartmentModel>(){
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
        comboBoxDepartment.setItems(EquipmentPresenter.get().getObservableDepartment());
        comboBoxDepartment.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment(newValue)));
        comboBoxWorkerFrom.setCellFactory((p->new ListCell<WorkerModel>(){
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
        comboBoxWorkerFrom.setItems(mEquipment.getDepartmentModel().getObsWorkerList());
        comboBoxWorkerFrom.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedWorkerFrom(newValue)));
    }

    private void selectedDepartment(DepartmentModel department){
        mDepartment=department;
        comboBoxWorkerTo.setCellFactory(p-> new ListCell<WorkerModel>(){
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
        comboBoxWorkerTo.setItems(department.getObsWorkerList());
        comboBoxWorkerTo.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedWorkerTo(newValue)));
    }

    private void selectedWorkerTo(WorkerModel worker){
        mWorkerTo =worker;
    }

    private void selectedWorkerFrom(WorkerModel worker){
        mWorkerFrom=worker;
    }

    @FXML
    private void onClickMove(){
        EquipmentPresenter.get().moveEquipmentInventory(mEquipment,mDepartment,mWorkerFrom, mWorkerTo,textAreaBase.getText());
    }
}
