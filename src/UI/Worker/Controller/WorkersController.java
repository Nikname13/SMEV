package UI.Worker.Controller;

import Model.Worker.WorkerModel;
import Presenter.WorkerPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WorkersController extends BaseController implements IOnMouseClick {

    @FXML
    private TableView<WorkerModel> mTableViewWorkers;
    @FXML
    private TableColumn<WorkerModel, String> mNameColumn, mPostColumn, mDepartmentColumn;
    @FXML
    private AnchorPane mAnchorPaneWorkers;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<WorkerModel> mModelList;
    private FilteredList<WorkerModel> mFilteredList;
    public WorkersController() {
        ListenersService.get().addListenerUI(this);
    }


    @FXML
    public void initialize(){
        mModelList = FXCollections.observableArrayList();
        initTextView();
        initTable();
        initPopup();
    }

    private void initTextView() {
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        mTextFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            mFilteredList.setPredicate(worker -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (worker.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (worker.getPost().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return worker.getDepartmentModel().getName().toLowerCase().contains(lowerCaseFilter);
            });
        }));
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


    private void updateTable(ObservableList<WorkerModel> observableWorker) {
        mModelList = observableWorker;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        SortedList<WorkerModel> sortedList = new SortedList<>(mFilteredList);
        mTableViewWorkers.setItems(sortedList);
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
    public void primaryClick(String id) {
        if (id.equals("mTableViewWorkers")) {
                new Coordinator().goToEditWorkerWindow(getStage());
        }
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            mTextFieldSearch.setText("");
            updateTable(WorkerPresenter.get().getObservableWorker());
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
            mTextFieldSearch.setText("");
            updateTable(WorkerPresenter.get().getObservableWorker());
        }
    }

}
