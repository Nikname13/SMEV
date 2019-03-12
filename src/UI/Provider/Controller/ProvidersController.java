package UI.Provider.Controller;

import Model.Provider.ProviderModel;
import Presenter.ProviderPresenter;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProvidersController extends BaseController {

    @FXML
    private TreeTableView<ProviderModel> mTreeTableViewProvider;
    @FXML
    private TreeTableColumn<ProviderModel, String> mNameColumn, mDescriptionColumn;
    @FXML
    private AnchorPane mAnchorPaneProviders;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<ProviderModel> mModelList;
    private FilteredList<ProviderModel> mFilteredList;

    public ProvidersController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
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
                return number.getName().toLowerCase().contains(lowerCaseFilter);
            });
            updateTree();
        }));
    }

    private void updateTree() {
        SortedList<ProviderModel> sortedList = new SortedList<>(mFilteredList);
        TreeItem<ProviderModel> rootItem = new TreeItem<>();
        for (ProviderModel provider : sortedList) {
            rootItem.getChildren().add(new TreeItem<>(provider));
        }
        mTreeTableViewProvider.setRoot(rootItem);
        mTreeTableViewProvider.setShowRoot(false);
    }

    private void initPopup() {
        new BasePopup(mTreeTableViewProvider, BasePopup.getBaseListPopup(), null);
    }

    private void initTable() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mNameColumn.setCellFactory((TreeTableColumn<ProviderModel, String> param) -> new GenericEditableTreeTableCell<>());
        mNameColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<ProviderModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<ProviderModel, String> event) {
                if (!event.getNewValue().trim().isEmpty()) {
                    TreeItem<ProviderModel> currentEditItem = mTreeTableViewProvider.getTreeItem(event.getTreeTablePosition().getRow());
                    currentEditItem.getValue().setName(event.getNewValue());
                    ProviderPresenter.get().editProvider(currentEditItem.getValue());
                } else {
                    ListenersService.get().refreshControl(ProviderModel.class);
                }
            }
        });
        mDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().descriptionProperty());
        mDescriptionColumn.setCellFactory((TreeTableColumn<ProviderModel, String> param) -> new GenericEditableTreeTableCell<>());
        mDescriptionColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<ProviderModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<ProviderModel, String> event) {
                TreeItem<ProviderModel> currentEditItem = mTreeTableViewProvider.getTreeItem(event.getTreeTablePosition().getRow());
                currentEditItem.getValue().setDescription(event.getNewValue());
                ProviderPresenter.get().editProvider(currentEditItem.getValue());
            }
        });
        mTreeTableViewProvider.setEditable(true);
        mTreeTableViewProvider.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedProvider(newValue));
    }

    private void selectedProvider(TreeItem<ProviderModel> newValue) {
        if (newValue != null) {
            ProviderPresenter.get().setProviderModel(newValue.getValue());
        } else {
            ProviderPresenter.get().setProviderModel(null);
        }
    }


    private void updateTable(ObservableList<ProviderModel> observableProvider) {
        mModelList = observableProvider;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        updateTree();
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
        if (updateClass.getName().equals(this.getClass().getName())) {
            mTextFieldSearch.setText("");
            updateTable(ProviderPresenter.get().getObservableProvider());
        }
    }


    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(ProviderModel.class.getName())) {
            mTreeTableViewProvider.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(ProviderModel.class.getName())) {
            mTextFieldSearch.setText("");
            updateTable(ProviderPresenter.get().getObservableProvider());
        }
    }

}
