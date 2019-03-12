package UI.Equipment.Controller;

import Model.AbstractModel;
import Model.Equipment.EquipmentModel;
import Presenter.EquipmentPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EquipmentsController extends BaseController implements IOnMouseClick {

    public EquipmentsController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private StackPane mStackPaneEquipments;
    @FXML
    private TreeView<AbstractModel<?>> mEquipmentTreeView;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<EquipmentModel> mModelList;
    private FilteredList<EquipmentModel> mFilteredList;

    @FXML
    public void initialize() {
        System.out.println("init equipment");
        mModelList = FXCollections.observableArrayList();
        initTextView();
        initEquipmentTreeView();
        initPopup();
    }

    private void initTextView() {
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        mTextFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            mFilteredList.setPredicate(equipment -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return equipment.getName().toLowerCase().contains(lowerCaseFilter);
            });
            updateTree();
        }));
    }

    private void updateTree() {
        TreeItem<AbstractModel<?>> rootItem = new TreeItem<>();
        SortedList<EquipmentModel> sortedList = new SortedList<>(mFilteredList);
        for (EquipmentModel equipment : sortedList) {
            if (mTextFieldSearch.getText().trim().isEmpty()) {
                onUnSortedList(equipment, rootItem);
            } else {
                onSortedList(equipment, rootItem);
            }
        }
        mEquipmentTreeView.setRoot(rootItem);
        mEquipmentTreeView.setShowRoot(false);
    }

    private void onSortedList(EquipmentModel equipment, TreeItem<AbstractModel<?>> rootItem) {
        rootItem.getChildren().add(new TreeItem<>(equipment));
    }

    private void onUnSortedList(EquipmentModel equipment, TreeItem<AbstractModel<?>> rootItem) {
        boolean flag = false;
        for (TreeItem<AbstractModel<?>> treeEquipment : rootItem.getChildren()) {
            if (treeEquipment.getValue().getId() == equipment.getTypeModel().getId()) {
                treeEquipment.getChildren().add(new TreeItem<>(equipment));
                flag = true;
            }
        }
        if (!flag) {
            TreeItem<AbstractModel<?>> treeItemFirst = new TreeItem<>(equipment.getTypeModel());
            rootItem.getChildren().add(treeItemFirst);
            treeItemFirst.getChildren().add(new TreeItem<>(equipment));
        }
    }

    private void initPopup() {
        new BasePopup(mEquipmentTreeView, BasePopup.getEquipmentListPopup(), this);
    }

    private void initEquipmentTreeView() {
        mEquipmentTreeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
        mEquipmentTreeView.setCellFactory(p -> new TreeCell<>() {
            @Override
            protected void updateItem(AbstractModel<?> item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        //updateTable();
    }

    private void updateTable(ObservableList<EquipmentModel> observableEquipment) {
        mModelList = observableEquipment;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        updateTree();
    }

    @FXML
    private void onClickAdd() {
        new Coordinator().goToAddEquipmentWindow(getStage());
    }

    private void selectedEquipment(TreeItem<AbstractModel<?>> equipment) {
        System.out.println("select equipment");
        if (equipment != null) {
            if (equipment.getValue().getClass().getName().equals(EquipmentModel.class.getName())) {
                EquipmentPresenter.get().setEquipmentModel(equipment.getValue());
                TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditEquipmentResource()));
                ListenersService.get().updateUI(EquipmentModel.class);
            } else {
                EquipmentPresenter.get().setEquipmentModel(null);
            }
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) mStackPaneEquipments.getScene().getWindow();
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            mTextFieldSearch.setText("");
            mEquipmentTreeView.getSelectionModel().clearSelection();
            // updateTable(EquipmentPresenter.get().getObservableEquipment());
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentModel.class.getName())) {
            mTextFieldSearch.setText("");
            updateTable(EquipmentPresenter.get().getObservableEquipment());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {
        if (updateClass.getName().equals(EquipmentModel.class.getName())) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateTable(EquipmentPresenter.get().getObservableEquipment());
                    mEquipmentTreeView.getSelectionModel().select(new TreeItem<>((EquipmentModel) currentItem));
                }
            });

        }
    }

    @Override
    public void primaryClick(String id) {
        if (id.equals("config")) {
            EquipmentPresenter.get().setTypeDocuments(AbstractModel.getTypeConfig());
            new Coordinator().goToFilesEquipmentWindow(getStage());
        }
    }
}
