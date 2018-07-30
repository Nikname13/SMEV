package UI.Movement.Controller;

import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.EquipmentInventoryModel;
import Model.Worker.WorkerModel;
import Presenter.EquipmentPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;


public class AddMovementController {

    private static EquipmentInventoryModel sEquipmentInventoryModel;
    private Object mToDepartment,mFormWorker, mToWorker;

    public AddMovementController() {
        sEquipmentInventoryModel = EquipmentPresenter.get().getEquipmentInventoryModel();
    }

    @FXML
    private Label dateLabel, fromLabel;

    @FXML
    private ComboBox<DepartmentModel> comboBoxDepartment;

    @FXML
    private ComboBox<WorkerModel> comboBoxDepartmentWorker, comboBoxSmevWorker;

    @FXML
    private TextArea textAreaBase;

    @FXML
    private TableView<WorkerModel> tableViewWorker;

    @FXML
    private TableColumn<WorkerModel,String> nameColumn, postColumn;

    @FXML
    public void initialize(){
        dateLabel.setText(LocalDate.now().toString());
        fromLabel.setText(sEquipmentInventoryModel.getDepartmentModel().getName());
        comboBoxDepartment.setCellFactory(p->new ListCell<DepartmentModel>(){
            @Override
            protected void updateItem(DepartmentModel department,boolean empty){
                super.updateItem(department,empty);
                if(department!=null && !empty){
                    setText(department.getName());
                }else setText(null);
            }
        });
        comboBoxDepartment.setItems(Departments.get().getEntityList());
        comboBoxDepartment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedDepartment(newValue));
/*        comboBoxDepartmentWorker.setCellFactory(p->new ListCell<>(){
            @Override
            protected void updateItem(WorkerModel worker,boolean empty){
                super.updateItem(worker,empty);
                if(worker!=null && !empty){
                    setText(worker.getName());
                }else setText(null);
            }
        });*/
        comboBoxDepartmentWorker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedDepartmentWorker(newValue));
/*        comboBoxSmevWorker.setCellFactory(p->new ListCell<>(){
            @Override
            protected void updateItem(WorkerModel worker,boolean empty){
                super.updateItem(worker,empty);
                if(worker!=null && !empty){
                    setText(worker.getName());
                }else setText(null);
            }
        });*/
       // comboBoxSmevWorker.setItems(Departments.get().getDepartment(0).getWorkerList());
        comboBoxSmevWorker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->selectedSmevWorker(newValue));
    }

    private void selectedDepartment(Object department){
        comboBoxDepartmentWorker.setItems(((DepartmentModel)department).getObsWorkerList());
        mToDepartment=department;
    }

    private void selectedDepartmentWorker(Object worker){
        mToWorker=worker;
    }

    private void selectedSmevWorker(Object worker){
        mFormWorker=worker;
    }

    @FXML
    private void onClickAdd(){
        EquipmentPresenter.get().addMovement(LocalDate.now(),textAreaBase.getText(),sEquipmentInventoryModel.getDepartmentModel(),mToDepartment,mFormWorker,mToWorker,sEquipmentInventoryModel);
    }

    @FXML
    private void onClickCancel() {
        EquipmentPresenter.get().cancel();
    }
}
