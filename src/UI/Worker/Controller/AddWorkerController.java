package UI.Worker.Controller;

import Model.Department.DepartmentModel;
import Presenter.WorkerPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

public class AddWorkerController {

    private DepartmentModel mDepartmentModel;

    @FXML
    private TextField textFieldName, textFieldPost;

    @FXML
    private ComboBox<DepartmentModel> comboBoxDepartment;

    @FXML
    public void initialize(){
        comboBoxDepartment.setCellFactory(p-> new ListCell<DepartmentModel>(){
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
        comboBoxDepartment.setItems(new WorkerPresenter().getObservableDepartment());
        comboBoxDepartment.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment(newValue)));
    }

    private void selectedDepartment(DepartmentModel departmet){
        mDepartmentModel=departmet;
    }

    @FXML
    private void onClickAdd(){
        new WorkerPresenter().addWorker(textFieldName.getText(),textFieldPost.getText(), mDepartmentModel);
    }
}
