package UI.Type.Controller;

import Model.Type.TypeModel;
import Presenter.TypePresenter;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static UI.BaseTabController.nextTab;

public class TypesController extends BaseController {

    @FXML
    private TreeTableView<TypeModel> mTreeTableView;
    @FXML
    private TreeTableColumn<TypeModel, String> mNameColumn;
    @FXML
    private JFXTabPane mSecondLvlTabPane;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<TypeModel> mModelList;
    private FilteredList<TypeModel> mFilteredList;

    public TypesController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private AnchorPane mAnchorPaneTypes;

    @FXML
    public void initialize(){
        mModelList = FXCollections.observableArrayList();
        initTextView();
        initTable();
        initPopup();
    }

    private void initTextView() {
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        mTextFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            mFilteredList.setPredicate(type -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return type.getName().toLowerCase().contains(lowerCaseFilter);
            });
            updateTree();
        }));
    }

    private void updateTree() {
        TreeItem<TypeModel> rootItem = new TreeItem<>();
        SortedList<TypeModel> sortedList = new SortedList<>(mFilteredList);
        for (TypeModel type : sortedList) {
            rootItem.getChildren().add(new TreeItem<>(type));
        }
        mTreeTableView.setRoot(rootItem);
        mTreeTableView.setShowRoot(false);
    }

    private void initPopup() {
        new BasePopup(mTreeTableView, BasePopup.getBaseListPopup(), null);
    }

    private void initTable() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mTreeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedType(newValue));
    }

    private void selectedType(TreeItem<TypeModel> newValue) {
        if (newValue != null) {
            TypePresenter.get().setTypeModel(newValue.getValue());
            TabControllerService.get().getListenerSecondTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditTypeResource()));
            ListenersService.get().updateUI(TypeModel.class);
        } else {
            TypePresenter.get().setTypeModel(null);
        }
    }


    private void updateTable(ObservableList<TypeModel> observableType) {
        mModelList = observableType;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        updateTree();
    }


    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddTypeWindow((Stage) mAnchorPaneTypes.getScene().getWindow());
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            mTextFieldSearch.setText("");
            updateTable(TypePresenter.get().getObservableType());
            mTreeTableView.getSelectionModel().clearSelection();
            mSecondLvlTabPane.getSelectionModel().select(0);
            TabControllerService.get().setListenerSecondTabPane((Tab nextTab) -> nextTab(nextTab, mSecondLvlTabPane));
        }
    }


    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(TypeModel.class.getName())) {
            mTreeTableView.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(TypeModel.class.getName())) {
            mTextFieldSearch.setText("");
            updateTable(TypePresenter.get().getObservableType());
        }
    }

    @Override
    protected Stage getStage() {
        return null;
    }


}
