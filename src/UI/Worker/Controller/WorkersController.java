package UI.Worker.Controller;

import Model.Worker.WorkerModel;
import Presenter.WorkerPresenter;
import Service.IOnMouseClick;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WorkersController extends BaseController implements IUpdateUI, IOnMouseClick {

    @FXML
    private TableView<WorkerModel> mTableViewWorkers;
    @FXML
    private TableColumn<WorkerModel, String> mNameColumn, mPostColumn, mDepartmentColumn;

    public WorkersController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private AnchorPane mAnchorPaneWorkers;

    @FXML
    public void initialize(){
        initTable();
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mTableViewWorkers, BasePopup.getBaseListPopup(), this);
    }

    private void initTable() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mPostColumn.setCellValueFactory(cellData -> cellData.getValue().getPost().nameProperty());
        mDepartmentColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartmentModel().nameProperty());
        mTableViewWorkers.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedWorker(newValue)));
    }

    private void selectedWorker(WorkerModel newValue) {
        if (newValue != null) {
            WorkerPresenter.get().setWorkerModel(newValue);
            ListenersService.get().updateUI(WorkerModel.class);
        } else {
            WorkerPresenter.get().setWorkerModel(null);
        }
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddWorkerWindow(getStage());
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneWorkers.getScene().getWindow();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void primaryClick(String id) {
        switch (id) {
            case "mTableViewWorkers":
                new Coordinator().goToEditWorkerWindow(getStage());
                break;
        }
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            mTableViewWorkers.setItems(WorkerPresenter.get().getObservableWorker());
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(WorkerModel.class.getName())) {
            mTableViewWorkers.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(WorkerModel.class.getName())) {
            mTableViewWorkers.setItems(WorkerPresenter.get().getObservableWorker());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }
}
