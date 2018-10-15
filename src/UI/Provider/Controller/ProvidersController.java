package UI.Provider.Controller;

import Model.Provider.ProviderModel;
import Presenter.ProviderPresenter;
import Service.IUpdateUI;
import UI.BaseController;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProvidersController extends BaseController implements IUpdateUI {

    @FXML
    private TableView<ProviderModel> tableViewProvider;

    @FXML
    private TableColumn<ProviderModel,String> firstColumn, secondColumn;

    @FXML
    private AnchorPane mAnchorPaneProviders;

    @FXML
    public void initialize()
    {
        initTable();
    }

    private void initTable() {
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        secondColumn.setCellValueFactory(cellData->cellData.getValue().descriptionProperty());
        tableViewProvider.setItems(ProviderPresenter.get().getObservableProvide());
        tableViewProvider.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> editEntity(newValue));
    }

    private void editEntity(Object entity){

    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddProviderWindow(getStage());
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneProviders.getScene().getWindow();
    }

    @Override
    public void updateUI(Class<?> updateClass) {

    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }
}
