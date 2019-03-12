package UI.Supply.Controller;

import Model.AbstractModel;
import Model.Provider.ProviderModel;
import Model.Supply.SupplyModel;
import Presenter.SupplyPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SupplyController extends BaseController implements IOnMouseClick {

    @FXML
    private TreeTableView<SupplyModel> mTreeTableSupply;
    @FXML
    private TreeTableColumn<SupplyModel, String> mProviderColumn, mDateColumn, mNumberColumn;
    @FXML
    private StackPane mStackPaneSupply;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<SupplyModel> mModelList;
    private FilteredList<SupplyModel> mFilteredList;

    public SupplyController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize(){
        System.out.println("init supple");
        mModelList = FXCollections.observableArrayList();
        initTextView();
        initTable();
        initPopup();
    }

    private void initTextView() {
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        mTextFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            mFilteredList.setPredicate(supply -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (supply.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (supply.getProviderModel().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (supply.getDate().toString().contains(lowerCaseFilter)) {
                    System.out.print(supply.getDate().toString());
                    return true;
                }
                return false;
            });
            updateTree();
        }));
    }

    private void updateTree() {
        TreeItem<SupplyModel> rootItem = new TreeItem<>();

        SortedList<SupplyModel> sortedList = new SortedList<>(mFilteredList);
        for (SupplyModel supplyModel : sortedList) {
            if (mTextFieldSearch.getText().trim().isEmpty()) {
                onUnSortedList(supplyModel, rootItem);
            } else {
                onSortedList(supplyModel, rootItem);
            }
        }
        mTreeTableSupply.setRoot(rootItem);
        mTreeTableSupply.setShowRoot(false);
    }

    private void onSortedList(SupplyModel supplyModel, TreeItem<SupplyModel> rootItem) {
        rootItem.getChildren().add(new TreeItem<>(supplyModel));
    }

    private void onUnSortedList(SupplyModel supplyModel, TreeItem<SupplyModel> rootItem) {
        boolean flag = false;
        for (TreeItem<SupplyModel> rootProviderItem : rootItem.getChildren()) {
            if (rootProviderItem.getValue().getProviderModel().getId() == supplyModel.getProviderModel().getId()) {
                rootProviderItem.getChildren().add(new TreeItem<>(supplyModel));
                flag = true;
                break;
            }
        }
        if (!flag) {
            SupplyModel emptySupply = new SupplyModel(
                    -1,
                    "",
                    new ProviderModel(
                            supplyModel.getProviderModel().getId(),
                            supplyModel.getProviderModel().getName(),
                            ""
                    )
            );
            emptySupply.setDisplayDate("");
            TreeItem<SupplyModel> treeItemFirst = new TreeItem<>(emptySupply);
            treeItemFirst.getChildren().add(new TreeItem<>(supplyModel));
            rootItem.getChildren().add(treeItemFirst);

        }
    }

    private void initPopup() {
        new BasePopup(mTreeTableSupply, BasePopup.getSupplyListPopup(), this);
    }

    private void initTable() {
        mProviderColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getProviderModel().nameProperty());
        mDateColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().dateToString());
        mNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mTreeTableSupply.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedItem(newValue)));
    }


    private void updateTable(ObservableList<SupplyModel> observableSupply) {
        mModelList = observableSupply;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        updateTree();
    }

    private void selectedItem(TreeItem<SupplyModel> newValue) {
        if (newValue != null) {
            SupplyModel supplyModel = newValue.getValue();
            if (supplyModel != null && supplyModel.getId() != -1) {
                SupplyPresenter.get().setSupplyModel(supplyModel);
                TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditSupplyResource()));
                ListenersService.get().updateUI(SupplyModel.class);
            } else {
                SupplyPresenter.get().setSupplyModel(null);
            }

        }
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddSupplyWindow(getStage());
    }

    @Override
    protected Stage getStage() {
        return (Stage) mStackPaneSupply.getScene().getWindow();
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            System.out.println("updateUI supple");
            mTextFieldSearch.setText("");
            updateTable(SupplyPresenter.get().getObservableSupply());
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(SupplyModel.class.getName())) {
            mTreeTableSupply.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(SupplyModel.class.getName())) {
            mTextFieldSearch.setText("");
            System.out.print("supply updateControl");
            updateTable(SupplyPresenter.get().getObservableSupply());
        }
    }

    @Override
    public void primaryClick(String id) {
        if (id.equals("documentation")) {
            SupplyPresenter.get().setTypeDocuments(AbstractModel.getTypeDoc());
            new Coordinator().goToFilesSupplyWindow(getStage());
        }
    }
}
