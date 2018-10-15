package UI.Inventory_number.Controller;

import Model.Inventory_number.InventoryNumberModel;
import Presenter.InventoryNumberPresenter;
import Service.IOnMouseClick;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class InventoryNumbersController extends BaseController implements IUpdateUI, IOnMouseClick {

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
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mTableViewNumber, BasePopup.getInventoryNumberPopup(), this);
    }

    private void initTable() {
        mNumberInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mNumberSupplyColumn.setCellValueFactory(cellData -> cellData.getValue().getSupply().nameProperty());
        mGroupColumn.setCellValueFactory(cellData -> cellData.getValue().groupProperty());
        mTableViewNumber.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedNumber(newValue)));
        mTableViewNumber.setFixedCellSize(50.0);

    }

    private void selectedNumber(InventoryNumberModel newValue) {
        if(newValue!=null){
            InventoryNumberPresenter.get().setSelectedObject(newValue);
            InventoryNumberPresenter.get().setInventoryNumberModel(newValue);
        }
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

    @Override
    public void primaryClick(String id) {
        if (id.equals("inventoryLog")) {
            new Coordinator().goToInventoryNumberLog(getStage());
            System.out.println("invenotory log");
        }
    }
}
