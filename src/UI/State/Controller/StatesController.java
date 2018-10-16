package UI.State.Controller;

import Model.State.StateModel;
import Presenter.StatePresenter;
import UI.BaseController;
import UI.Coordinator;
import UI.Validator.BaseValidator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatesController extends BaseController {

    public StatesController(){
        //new StatePresenter().updateData();
    }

    @FXML
    private TableView<StateModel> tableViewState;

    @FXML
    private TableColumn<StateModel,String> firstColumn;

    @FXML
    private AnchorPane mAnchorPaneState;

    @FXML
    public void initialize(){
        initTable();

    }

    private void initTable() {
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewState.setItems(new StatePresenter().getObservableState());
        tableViewState.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->editState(newValue));
    }

    private void editState(StateModel newValue) {

    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddStateWindow(getStage());
    }


    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneState.getScene().getWindow();
    }
}
