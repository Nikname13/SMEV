package UI.Type.Controller;

import Model.Type.TypeModel;
import Presenter.TypePresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static UI.BaseTabController.nextTab;

public class TypesController extends BaseController implements IUpdateUI {

    @FXML
    private TreeTableView<TypeModel> mTreeTableView;
    @FXML
    private TreeTableColumn<TypeModel, String> mNameColumn;
    @FXML
    private JFXTabPane mSecondLvlTabPane;

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

    @Override
    public void destroy() {

    }

}
