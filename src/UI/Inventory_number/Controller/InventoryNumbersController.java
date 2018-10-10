package UI.Inventory_number.Controller;

import Model.Inventory_number.InventoryNumberModel;
import Presenter.InventoryNumberPresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class InventoryNumbersController extends BaseController implements IUpdateUI {

    @FXML
    private TableView<InventoryNumberModel> mTableViewNumber;

    @FXML
    private TableColumn<InventoryNumberModel, String> mNumberInventoryColumn, mNumberSupplyColumn;

    @FXML
    private TableColumn<InventoryNumberModel, Boolean> mGroupColumn;

    @FXML
    private AnchorPane mAnchorPaneInventoryNumber;

    public InventoryNumbersController() {
        ListenersService.get().addListenerUI(this);
    }

    public void initialize(){
        System.out.println("inventoryNumber");
        initTable();
    }

    private void initTable() {
        mNumberInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mNumberSupplyColumn.setCellValueFactory(cellData -> cellData.getValue().getSupply().nameProperty());
        mGroupColumn.setCellValueFactory(cellData -> cellData.getValue().groupProperty());
        // mTableViewNumber.setItems(new InventoryNumberPresenter().getObservableInventoryN());
        mTableViewNumber.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> edit(newValue)));
        mTableViewNumber.setFixedCellSize(50.0);

    }

    private void edit(InventoryNumberModel entity){
        //new InventoryNumberPresenter().setInventoryNumberModel(entity);
        new Coordinator().goToEditInventoryNumber((Stage) mAnchorPaneInventoryNumber.getScene().getWindow(), 100.0, 200.0);

    }

    private void updateTable(ObservableList<InventoryNumberModel> observableInventory) {
        mTableViewNumber.setItems(observableInventory);
        //mTableViewNumber.prefHeightProperty().bind(Bindings.size(observableInventory).multiply(mTableViewNumber.getFixedCellSize()).add(55));
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddInventoryNumberWindow(getStage());
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneInventoryNumber.getScene().getWindow();
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            updateTable(InventoryNumberPresenter.get().getObservableInventory());
        }
    }


    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(InventoryNumberModel.class.getName())) {
            updateTable(InventoryNumberPresenter.get().getObservableInventory());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }
}
