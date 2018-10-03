package UI.Supply.Controller;

import Model.Provider.ProviderModel;
import Model.Supply.SupplyModel;
import Presenter.SupplyPresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SupplysController extends BaseController implements IUpdateUI {

    @FXML
    private TreeTableView<SupplyModel> mTreeTableSupply;
    @FXML
    private TreeTableColumn<SupplyModel, String> mProviderColumn, mDateColumn, mNumberColumn;
    @FXML
    private StackPane mStackPaneSupply;

    public SupplysController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize(){
        initTable();
    }

    private void initTable() {
        mProviderColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getProviderModel().nameProperty());
        mDateColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().dateSupply());
        mNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mTreeTableSupply.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedItem(newValue)));
    }


    private void updateTable(ObservableList<SupplyModel> observableSupply) {
        TreeItem<SupplyModel> rootItem = new TreeItem<>();
        boolean flag = false;
        for (SupplyModel supplyModel : observableSupply) {
            for (TreeItem<SupplyModel> rootProviderItem : rootItem.getChildren()) {
                if (rootProviderItem.getValue().getProviderModel().getId() == supplyModel.getProviderModel().getId()) {
                    rootProviderItem.getChildren().add(new TreeItem<>(supplyModel));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                TreeItem<SupplyModel> treeItemFirst = new TreeItem<>(new SupplyModel(
                        -1,
                        "",
                        new ProviderModel(
                                supplyModel.getProviderModel().getId(),
                                supplyModel.getProviderModel().getName(),
                                ""
                        )));
                treeItemFirst.getChildren().add(new TreeItem<>(supplyModel));
                rootItem.getChildren().add(treeItemFirst);

            } else {
                flag = false;
            }
        }
        mTreeTableSupply.setRoot(rootItem);
        mTreeTableSupply.setShowRoot(false);
    }

    private void selectedItem(TreeItem<SupplyModel> newValue) {
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
        if (updateClass.getName().equals(SupplyModel.class.getName())) {
            updateTable(SupplyPresenter.get().getObservableSupply());
        }
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
