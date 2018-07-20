package UI.Department.Controller;

import Model.Department.DepartmentModel;
import Presenter.DepartmentPresenter;
import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.Coordinator;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DepartmentsController{

    public DepartmentsController(){
        UpdateService.get().addListener((Class<?> updateClass)->{
            if(updateClass.getName().equals(this.getClass().getName()))
            mDepartmentListView.getSelectionModel().clearSelection();});
    }

    @FXML
    private TableView<DepartmentModel> mDepartmentTableView;

    @FXML
    private TableColumn<DepartmentModel,String> numberColumn, nameColumn, areaColumn;

    @FXML
    private JFXListView<DepartmentModel> mDepartmentListView;

    @FXML
    private AnchorPane anchorPaneDepartment;

    @FXML
    public void initialize(){
/*        numberColumn.setCellValueFactory(cellData->cellData.getValue().numberProperty());
        nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        areaColumn.setCellValueFactory(cellData->cellData.getValue().getAreaModel().nameProperty());
        mDepartmentTableView.setItems(DepartmentPresenter.get().getObservableDepartment());
        mDepartmentTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->selectedDepartment(newValue));*/
        mDepartmentListView.setItems(DepartmentPresenter.get().getObservableDepartment());
        mDepartmentListView.setCellFactory(p->new ListCell<>(){
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getNumber()+" - "+item.getName());
                }else {
                    setText(null);
                }
            }
        });
        mDepartmentListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment(newValue)));
    }

    private void selectedDepartment(DepartmentModel department) {
        if(department!=null) {
            System.out.println("select department");
            DepartmentPresenter.get().setDepartmentModel(department);
            TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditDepartmentResource()));
            System.out.println(" listener " + TabControllerService.get().getListenerFirstTabPane().getClass().getName());
            UpdateService.get().updateUI(DepartmentModel.class);
        }

    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddDepartmentWindow((Stage)anchorPaneDepartment.getScene().getWindow(),400.0,414.0);
    }
}
