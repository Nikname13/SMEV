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
        //new WorkerPresenter().updateData();
    }

    @FXML
    private TableView<WorkerModel> tableViewWorkers;

    @FXML
    private TableColumn<WorkerModel,String> firstColumn, secondColumn, departmentColumn;

    @FXML
    private AnchorPane mAnchorPaneWorkers;

    @FXML
    public void initialize(){
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        secondColumn.setCellValueFactory(cellData -> cellData.getValue().getPost().nameProperty());
        departmentColumn.setCellValueFactory(cellData->cellData.getValue().getDepartmentModel().nameProperty());
        tableViewWorkers.setItems(WorkerPresenter.get().getObservableWorker());
        tableViewWorkers.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->delete(newValue) ));
    }

    private void delete(WorkerModel worker){
        mId=worker.getId();
        WorkerPresenter.get().setWorkerModel(worker);
        // WorkerPresenter.get().editWorker("new name", "new post", worker.getDepartmentModel());
    }

    @FXML
    private void onClickDelete(){
        if(mId!=-1){
            WorkerPresenter.get().deleteWorker(mId);
            mId=-1;
        }
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddWorkerWindow((Stage) mAnchorPaneWorkers.getScene().getWindow(), 100.0, 200.0);
    }
}
