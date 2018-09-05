package UI.State.Controller;

import Model.State.StateModel;
import Presenter.StatePresenter;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatesController {

    public StatesController(){
        //new StatePresenter().updateData();
    }

    @FXML
    private TableView<StateModel> tableViewState;

    @FXML
    private TableColumn<StateModel,String> firstColumn;

    @FXML
    private AnchorPane anchorPaneState;

    @FXML
    public void initialize(){
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewState.setItems(new StatePresenter().getObservableState());
        tableViewState.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->editState(newValue));
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddStateWindow((Stage) anchorPaneState.getScene().getWindow(), 100.0, 200.0);
    }

    private void editState(Object state){
        /*new StatePresenter().setState(state);
        new StatePresenter().editState("New name of state");*/
        new StatePresenter().deleteState(state.hashCode());
    }
}
