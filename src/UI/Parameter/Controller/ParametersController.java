package UI.Parameter.Controller;

import Model.Parameter.ParameterModel;
import Presenter.ParameterPresenter;
import UI.BaseController;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ParametersController extends BaseController {

    public ParametersController() {
        //new ParameterPresenter().updateParameters();
    }

    @FXML
    private TableView<ParameterModel> tableViewParameters;

    @FXML
    private TableColumn<ParameterModel, String> firstColumn;

    @FXML
    private AnchorPane mAnchorPaneParameters;

    @FXML
    public void initialize() {
        System.out.println("init");
        initTable();

    }

    private void initTable() {
        firstColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableViewParameters.setItems(ParameterPresenter.get().getObservableParameter());
        tableViewParameters.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> editParameter(newValue));
    }

    @FXML
    private void onClickAdd() {
        new Coordinator().goToAddParameterWindow(getStage());

    }

    private void editParameter(Object o) {
        ParameterPresenter.get().setParameter(o);
        new Coordinator().goToEditParameterWindow(getStage(), 100.0, 200.0);
        System.out.println("editParameter");
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneParameters.getScene().getWindow();
    }
}
