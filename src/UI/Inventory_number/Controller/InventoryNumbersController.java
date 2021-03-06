package UI.Inventory_number.Controller;

import Model.Inventory_number.InventoryNumberModel;
import Presenter.InventoryNumberPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static UI.BaseTabController.nextTab;


public class InventoryNumbersController extends BaseController implements IOnMouseClick {


    @FXML
    private TableView<InventoryNumberModel> mTableViewNumber;
    @FXML
    private TableColumn<InventoryNumberModel, String> mNumberInventoryColumn, mNumberSupplyColumn;
    @FXML
    private JFXTabPane mSecondLvlTabPane;
    @FXML
    private AnchorPane mAnchorPaneInventoryNumber;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<InventoryNumberModel> mModelList;
    private FilteredList<InventoryNumberModel> mFilteredList;

    public InventoryNumbersController() {
        ListenersService.get().addListenerUI(this);
    }

    public void initialize(){
        System.out.println("inventoryNumber");
        mModelList = FXCollections.observableArrayList();
        initTextView();
        initTable();
        initPopup();
    }

    private void initTextView() {
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        mTextFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            mFilteredList.setPredicate(number -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (number.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return number.getSupply().getName().toLowerCase().contains(lowerCaseFilter);
            });
        }));
    }

    private void initPopup() {
        new BasePopup(mTableViewNumber, BasePopup.getInventoryNumberPopup(), this);
    }

    private void initTable() {
        mNumberInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mNumberSupplyColumn.setCellValueFactory(cellData -> cellData.getValue().getSupply().nameProperty());
        mTableViewNumber.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedNumber(newValue)));
        mTableViewNumber.setFixedCellSize(50.0);


    }

    private void selectedNumber(InventoryNumberModel newValue) {
        if(newValue!=null){
            InventoryNumberPresenter.get().setInventoryNumberModel(newValue);
            TabControllerService.get().getListenerSecondTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getInventoryNumberEditResource()));
            ListenersService.get().updateUI(InventoryNumberModel.class);
        } else {
            InventoryNumberPresenter.get().setInventoryNumberModel(null);
        }
    }

    private void updateTable(ObservableList<InventoryNumberModel> observableInventory) {
        mModelList = observableInventory;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        SortedList<InventoryNumberModel> sortedList = new SortedList<>(mFilteredList);
        mTableViewNumber.setItems(sortedList);
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
            mTextFieldSearch.setText("");
            updateTable(InventoryNumberPresenter.get().getObservableInventory());
            mTableViewNumber.getSelectionModel().clearSelection();
            mSecondLvlTabPane.getSelectionModel().select(0);
            TabControllerService.get().setListenerSecondTabPane(((Tab nextTab) -> nextTab(nextTab, mSecondLvlTabPane)));
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(InventoryNumberModel.class.getName())) {
            //updateTable(InventoryNumberPresenter.get().getObservableInventory());
            mTableViewNumber.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(InventoryNumberModel.class.getName())) {
            mTextFieldSearch.setText("");
            updateTable(InventoryNumberPresenter.get().getObservableInventory());
        }
    }

    @Override
    public void primaryClick(String id) {
        if (id.equals("inventoryLog")) {
            new Coordinator().goToInventoryNumberLog(getStage());
            System.out.println("invenotory log");
        }
    }
}
