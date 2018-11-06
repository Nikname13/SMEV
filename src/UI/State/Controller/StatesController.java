package UI.State.Controller;

import Model.State.StateModel;
import Presenter.StatePresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatesController extends BaseController implements IUpdateUI {

    @FXML
    private TreeTableView<StateModel> mTreeTableState;
    @FXML
    private TreeTableColumn<StateModel, String> mNameColumn;
    @FXML
    private AnchorPane mStatePane;

    public StatesController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize(){
        initTable();
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mTreeTableState, BasePopup.getBaseListPopup(), null);
    }

    private void initTable() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mNameColumn.setCellFactory((TreeTableColumn<StateModel, String> param) -> new GenericEditableTreeTableCell<>());
        mNameColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<StateModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<StateModel, String> event) {
                if (!event.getNewValue().trim().isEmpty()) {
                    TreeItem<StateModel> currentEditItem = mTreeTableState.getTreeItem(event.getTreeTablePosition().getRow());
                    currentEditItem.getValue().setName(event.getNewValue());
                    StatePresenter.get().editState(currentEditItem.getValue());
                } else {
                    refreshControl(StateModel.class);
                }
            }
        });
        mTreeTableState.setEditable(true);
        mTreeTableState.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedState(newValue));
    }

    private void selectedState(TreeItem<StateModel> newValue) {
        if (newValue != null) {
            StatePresenter.get().setState(newValue.getValue());
        } else {
            StatePresenter.get().setState(null);
        }
    }

    private void updateTable(ObservableList<StateModel> observableState) {
        TreeItem<StateModel> rootItem = new TreeItem<>();
        for (StateModel state : observableState) {
            rootItem.getChildren().add(new TreeItem<>(state));
        }
        mTreeTableState.setRoot(rootItem);
        mTreeTableState.setShowRoot(false);
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddStateWindow(getStage());
    }


    @Override
    protected Stage getStage() {
        return (Stage) mStatePane.getScene().getWindow();
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            updateTable(StatePresenter.get().getObservableState());
        }
    }


    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(StateModel.class.getName())) {
            mTreeTableState.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(StateModel.class.getName())) {
            updateTable(StatePresenter.get().getObservableState());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }
}
