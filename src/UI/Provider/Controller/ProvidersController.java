package UI.Provider.Controller;

import Model.Provider.ProviderModel;
import Presenter.ProviderPresenter;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProvidersController {

    @FXML
    private TableView<ProviderModel> tableViewProvider;

    @FXML
    private TableColumn<ProviderModel,String> firstColumn, secondColumn;

    @FXML
    private AnchorPane mAnchorPaneProviders;

    @FXML
    public void initialize()
    {
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        secondColumn.setCellValueFactory(cellData->cellData.getValue().descriptionProperty());
        tableViewProvider.setItems(new ProviderPresenter().getObservableProvide());
        tableViewProvider.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> editEntity(newValue));

    }

    private void editEntity(Object entity){
        /*new ProviderPresenter().setProviderModel(entity);
        new ProviderPresenter().editProvide("updateData name","updateData description");*/
        new ProviderPresenter().delete(entity.hashCode());
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddProviderWindow((Stage) mAnchorPaneProviders.getScene().getWindow(), 100.0, 200.0);
    }
}
