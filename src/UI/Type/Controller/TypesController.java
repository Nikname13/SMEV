package UI.Type.Controller;

import Model.Type.TypeModel;
import Presenter.TypePresenter;
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

public class TypesController extends BaseController implements IUpdateUI {

    @FXML
    private TreeTableView<TypeModel> mTreeTableView;
    @FXML
    private TreeTableColumn<TypeModel, String> mNameColumn;

    public TypesController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private AnchorPane mAnchorPaneTypes;

    @FXML
    public void initialize(){
        initTable();
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mTreeTableView, BasePopup.getBaseListPopup(), null);
    }

    private void initTable() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mNameColumn.setCellFactory((TreeTableColumn<TypeModel, String> param) -> new GenericEditableTreeTableCell<>());
        mNameColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<TypeModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<TypeModel, String> event) {
                if (!event.getNewValue().trim().isEmpty()) {
                    TreeItem<TypeModel> currentEditItem = mTreeTableView.getTreeItem(event.getTreeTablePosition().getRow());
                    currentEditItem.getValue().setName(event.getNewValue());
                    TypePresenter.get().editType(currentEditItem.getValue());
                } else {
                    ListenersService.get().refreshControl(TypeModel.class);
                }

            }
        });
        mTreeTableView.setEditable(true);
        mTreeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedType(newValue));
    }

    private void selectedType(TreeItem<TypeModel> newValue) {
        if (newValue != null) {
            TypePresenter.get().setTypeModel(newValue.getValue());
        } else {
            TypePresenter.get().setTypeModel(null);
        }
    }


    private void updateTable(ObservableList<TypeModel> observableType) {
        TreeItem<TypeModel> rootItem = new TreeItem<>();
        for (TypeModel type : observableType) {
            rootItem.getChildren().add(new TreeItem<>(type));
        }
        mTreeTableView.setRoot(rootItem);
        mTreeTableView.setShowRoot(false);
    }


    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddTypeWindow((Stage) mAnchorPaneTypes.getScene().getWindow());
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            updateTable(TypePresenter.get().getObservableType());
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
            updateTable(TypePresenter.get().getObservableType());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }

    @Override
    protected Stage getStage() {
        return null;
    }
}
