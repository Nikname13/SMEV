package UI.Parameter.Controller;

import Model.Parameter.ParameterModel;
import Presenter.ParameterPresenter;
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

public class ParametersController extends BaseController implements IUpdateUI {

    @FXML
    private TreeTableView<ParameterModel> mTreeTableParameters;
    @FXML
    private TreeTableColumn<ParameterModel, String> mNameColumn;

    public ParametersController() {
        ListenersService.get().addListenerUI(this);
    }
    @FXML
    private AnchorPane mAnchorPaneParameters;

    @FXML
    public void initialize() {
        System.out.println("init");
        initTable();
        initPopup();

    }

    private void initPopup() {
        new BasePopup(mTreeTableParameters, BasePopup.getBaseListPopup(), null);
    }

    private void initTable() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mNameColumn.setCellFactory((TreeTableColumn<ParameterModel, String> param) -> new GenericEditableTreeTableCell<>());
        mNameColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<ParameterModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<ParameterModel, String> event) {
                if (!event.getNewValue().trim().isEmpty()) {
                    TreeItem<ParameterModel> currentEditItem = mTreeTableParameters.getTreeItem(event.getTreeTablePosition().getRow());
                    currentEditItem.getValue().setName(event.getNewValue());
                    ParameterPresenter.get().editParameter(currentEditItem.getValue());
                } else {
                    ListenersService.get().refreshControl(ParameterModel.class);
                }
            }
        });
        mTreeTableParameters.setEditable(true);
        mTreeTableParameters.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedItem(newValue)));
    }

    private void selectedItem(TreeItem<ParameterModel> newValue) {
        if (newValue != null) {
            ParameterPresenter.get().setParameter(newValue.getValue());
        } else {
            ParameterPresenter.get().setParameter(null);
        }
    }

    @FXML
    private void onClickAdd() {
        new Coordinator().goToAddParameterWindow(getStage());
    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneParameters.getScene().getWindow();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            mTreeTableParameters.getSelectionModel().clearSelection();
            updateTable(ParameterPresenter.get().getObservableParameter());
        }
    }

    private void updateTable(ObservableList<ParameterModel> parametersList) {
        TreeItem<ParameterModel> rootItem = new TreeItem<>();
        for (ParameterModel log : parametersList) {
            rootItem.getChildren().add(new TreeItem<>(log));
        }
        mTreeTableParameters.setRoot(rootItem);
        mTreeTableParameters.setShowRoot(false);
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(ParameterModel.class.getName())) {
            mTreeTableParameters.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(ParameterModel.class.getName())) {
            updateTable(ParameterPresenter.get().getObservableParameter());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }
}
