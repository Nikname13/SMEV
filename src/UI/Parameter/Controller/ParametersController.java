package UI.Parameter.Controller;

import Model.Parameter.ParameterModel;
import Presenter.ParametersPresenter;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ParametersController {

    public ParametersController() {
        //new ParametersPresenter().updateParameters();
    }

    @FXML
    private TableView<ParameterModel> tableViewParameters;

    @FXML
    private TableColumn<ParameterModel, String> firstColumn;

    @FXML
    private AnchorPane anchorPaneParameters;

    @FXML
    public void initialize() {
        System.out.println("init");
        firstColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        // tableViewParameters.setItems(new ParametersPresenter().getObservableParameters());
        tableViewParameters.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> editParameter(newValue));
    }

    @FXML
    private void onClickAdd() {
        new Coordinator().goToAddParameterWindow((Stage) anchorPaneParameters.getScene().getWindow(),100.0,200.0);

    }

    private void editParameter(Object o) {
        new ParametersPresenter().setParameter(o);
        new Coordinator().goToEditParameterWindow((Stage) anchorPaneParameters.getScene().getWindow(),100.0,200.0);
        System.out.println("editParameter");
    }

}
