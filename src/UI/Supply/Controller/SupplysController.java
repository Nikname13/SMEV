package UI.Supply.Controller;

import Model.Supply.SupplyModel;
import Presenter.SupplyPresenter;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SupplysController {

    @FXML
    private TableView<SupplyModel> tableViewSupply;

    @FXML
    private TableColumn<SupplyModel,String> numberColumn, providerColumn, typeColumn;

    @FXML
    private TableColumn<SupplyModel,String> dateColumn;

    @FXML
    private AnchorPane mAnchorPaneSupply;

    @FXML
    public void initialize(){
        numberColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        providerColumn.setCellValueFactory(cellData->cellData.getValue().getProviderModel().nameProperty());
        typeColumn.setCellValueFactory(cellData->cellData.getValue().typeSupplyProperty());
        //dateColumn.setCellValueFactory(cellData-> cellData.getValue().dateSupply());
        tableViewSupply.setItems(new SupplyPresenter().getObservableSupply());
        tableViewSupply.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->editSupply(newValue) );
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddSupplyWindow((Stage) mAnchorPaneSupply.getScene().getWindow(), 100.0, 200.0);
    }

    private void editSupply(Object supply){
        new SupplyPresenter().setSupplyModel(supply);
        new Coordinator().goToEditSupplyWindow((Stage) mAnchorPaneSupply.getScene().getWindow(), 100.0, 200.0);
    }
}
