package UI.Inventory_number.Controller;

import Model.Inventory_number.InventoryNumberModel;
import Presenter.InventoryNumberPresenter;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class InventoryNumbersController {

    @FXML
    private TableView<InventoryNumberModel> tableViewNumber;

    @FXML
    private TableColumn<InventoryNumberModel,String> numberInventoryColumn, numberSupplyColumn;

    @FXML
    private TableColumn<InventoryNumberModel,Boolean> groupColumn;

    @FXML
    private AnchorPane anchorPanelInventoryNumber;

    public void initialize(){
        System.out.println("inventoryNumber");
        numberInventoryColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        numberSupplyColumn.setCellValueFactory(cellData->cellData.getValue().getSupply().nameProperty());
        groupColumn.setCellValueFactory(cellData->cellData.getValue().groupProperty());
        // tableViewNumber.setItems(new InventoryNumberPresenter().getObservableInventoryN());
        tableViewNumber.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->edit(newValue)));

    }

    private void edit(InventoryNumberModel entity){
        new InventoryNumberPresenter().setInventoryNumberModel(entity);
        new Coordinator().goToEditInventoryNumber((Stage)anchorPanelInventoryNumber.getScene().getWindow(),100.0,200.0);

    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddInventoryNumberWindow((Stage)anchorPanelInventoryNumber.getScene().getWindow(),100.0,200.0);
    }
}
