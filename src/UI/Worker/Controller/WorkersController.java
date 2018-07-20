package UI.Worker.Controller;

import Model.Worker.WorkerModel;
import Presenter.WorkerPresenter;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WorkersController {

    private int mId;

    public WorkersController(){
        //new WorkerPresenter().update();
    }

    @FXML
    private TableView<WorkerModel> tableViewWorkers;

    @FXML
    private TableColumn<WorkerModel,String> firstColumn, secondColumn, departmentColumn;

    @FXML
    private AnchorPane anchorPaneWorkers;

    @FXML
    public void initialize(){
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        secondColumn.setCellValueFactory(cellData->cellData.getValue().postProperty());
        departmentColumn.setCellValueFactory(cellData->cellData.getValue().getDepartmentModel().nameProperty());
        tableViewWorkers.setItems(new WorkerPresenter().getObservableWorkers());
        tableViewWorkers.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->delete(newValue) ));
    }

    private void delete(WorkerModel worker){
        mId=worker.getId();
        new WorkerPresenter().setWorker(worker);
        new WorkerPresenter().editWorker("new name","new post", worker.getDepartmentModel());
    }

    @FXML
    private void onClickDelete(){
        if(mId!=-1){
            new WorkerPresenter().deleteWorker(mId);
            mId=-1;
        }
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddWorkerWindow((Stage)anchorPaneWorkers.getScene().getWindow(),100.0,200.0);
    }
}