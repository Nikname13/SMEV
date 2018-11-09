package UI.Area.Controller;

import Model.Area.AreaModel;
import Presenter.AreaPresenter;
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

public class AreasController extends BaseController {

    public AreasController(){
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private TreeTableView<AreaModel> mTreeTableArea;

    @FXML
    private TreeTableColumn<AreaModel, String> mNameColumn;

    @FXML
    private AnchorPane mAnchorPaneArea;

    @FXML
    public void initialize(){
        initTable();
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mTreeTableArea, BasePopup.getBaseListPopup(), null);
    }

    private void initTable() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mNameColumn.setCellFactory((TreeTableColumn<AreaModel, String> param) -> new GenericEditableTreeTableCell<>());
        mNameColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<AreaModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<AreaModel, String> event) {
                if (!event.getNewValue().trim().isEmpty()) {
                    TreeItem<AreaModel> currentEditItem = mTreeTableArea.getTreeItem(event.getTreeTablePosition().getRow());
                    currentEditItem.getValue().setName(event.getNewValue());
                    AreaPresenter.get().editArea(currentEditItem.getValue());
                } else {
                    refreshControl(AreaModel.class);
                }
            }
        });
        mTreeTableArea.setEditable(true);
        mTreeTableArea.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedArea(newValue)));
    }

    private void selectedArea(TreeItem<AreaModel> newValue) {
        if (newValue != null) {
            AreaPresenter.get().setArea(newValue.getValue());
        } else {
            AreaPresenter.get().setArea(null);
        }
    }

    private void updateTable(ObservableList<AreaModel> observableArea) {
        TreeItem<AreaModel> rootItem = new TreeItem<>();
        for (AreaModel model : observableArea) {
            rootItem.getChildren().add(new TreeItem<>(model));
        }
        mTreeTableArea.setRoot(rootItem);
        mTreeTableArea.setShowRoot(false);
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddAreaWindow(getStage());
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            updateTable(AreaPresenter.get().getObservableArea());
        }
    }


    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(AreaModel.class.getName())) {
            mTreeTableArea.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(AreaModel.class.getName())) {
            updateTable(AreaPresenter.get().getObservableArea());
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneArea.getScene().getWindow();
    }

}
