package UI.Department.Controller;

import Model.AbstractModel;
import Model.Area.AreaModel;
import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Location.LocationModel;
import Model.State.StateModel;
import Model.Worker.WorkerModel;
import Presenter.DepartmentPresenter;
import Presenter.EquipmentPresenter;
import Presenter.LocationPresenter;
import Presenter.WorkerPresenter;
import Service.IOnMouseClick;
import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import UI.TabPane.Controller.TabPaneSecondLvlTabController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static UI.BaseTabController.nextTab;

public class EditDepartmentController extends BaseController implements IUpdateUI, IOnMouseClick {

    private static DepartmentModel mDepartmentModel;
    private BaseValidator mBaseValidator = new BaseValidator();
    private static Stage sStage;
    @FXML
    private AnchorPane anchorPaneEditDepartment;

    @FXML
    private JFXTextField mTextFieldNumber, mTextFieldName;

    @FXML
    private JFXRadioButton mRadioButtonRenting, mRadioButtonElQ;

    @FXML
    private JFXComboBox<AreaModel> mComboBoxArea;

    @FXML
    private JFXListView<WorkerModel> mListViewWorker;

    @FXML
    private JFXListView<LocationModel> mListViewLocation;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private TreeTableView<EquipmentInventoryModel> mTreeTableEquipmentInventory;

    @FXML
    private TreeTableColumn<EquipmentInventoryModel, String> mNameEquipmentColumn, mNumberEquipmentColumn, mStateEquipmentColumn, mDescriptionEquipmentColumn;

    @FXML
    private JFXTabPane mSecondLvlTabPane;

    @FXML
    private JFXButton mButtonUpdate;

    public EditDepartmentController() {
        mDepartmentModel = DepartmentPresenter.get().getDepartmentModel();
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private AnchorPane mAnchorPaneBasicInfoDepartment;


    @FXML
    public void initialize() {
        System.out.println("edit department initialize ");
        mButtonUpdate.setFocusTraversable(false);
        initTextFields();
        initTreeTableEquipmentInventory();
        initComboBoxArea(mComboBoxArea, true);
        initTextAreaDescription();
        initRadioButton();
        initLocationListView();
        initWorkerListView();
        initPopup();
    }

    public void setStage(Stage stage) {
        sStage = stage;
    }

    private void initTextFields() {
        mBaseValidator.setJFXTextFields(mTextFieldName, mTextFieldNumber);
        mTextFieldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setVisibleEditButton();
            }
        });
        mTextFieldNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setVisibleEditButton();
            }
        });
    }

    @Override
    protected Stage getStage() {
        return (Stage) anchorPaneEditDepartment.getScene().getWindow();
    }

    @Override
    protected void initComboBoxArea(JFXComboBox<AreaModel> comboBoxArea, boolean isSelectionItem) {
        super.initComboBoxArea(comboBoxArea, isSelectionItem);
        comboBoxArea.getSelectionModel().selectedIndexProperty().addListener((observable -> selectedArea()));
    }

    private void selectedArea() {
        setVisibleEditButton();
    }

    private void initLocationListView() {
        mListViewLocation.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(LocationModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        mListViewLocation.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            LocationPresenter.get().setLocation(newValue);
            LocationPresenter.get().setSelectedObject(newValue);
        }));
        mListViewLocation.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue)
                    LocationPresenter.get().setSelectedObject(mListViewLocation.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void initTreeTableEquipmentInventory() {
        mNameEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getEquipmentModel().nameProperty());
        mNumberEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getInventoryNumber().nameProperty());
        mStateEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getStateModel().nameProperty());
        mDescriptionEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().description_departmentProperty());
        mDescriptionEquipmentColumn.setCellFactory((TreeTableColumn<EquipmentInventoryModel, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));

        mDescriptionEquipmentColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<EquipmentInventoryModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<EquipmentInventoryModel, String> event) {
                TreeItem<EquipmentInventoryModel> currentEditEquipment = mTreeTableEquipmentInventory.getTreeItem(event.getTreeTablePosition().getRow());
                if (currentEditEquipment.getValue().getId() != -1) {
                    currentEditEquipment.getValue().setDescription_department(event.getNewValue());
                    EquipmentPresenter.get().editEquipmentInventory(currentEditEquipment.getValue());
                } else {
                    mTreeTableEquipmentInventory.refresh();
                }
                System.out.println(currentEditEquipment.getValue().getId());
            }
        });
        mTreeTableEquipmentInventory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
        mTreeTableEquipmentInventory.setEditable(true);
        mTreeTableEquipmentInventory.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (mTreeTableEquipmentInventory.getSelectionModel().getSelectedItem() != null) {
                        EquipmentInventoryModel equipment = mTreeTableEquipmentInventory.getSelectionModel().getSelectedItem().getValue();
                        if (equipment != null && equipment.getId() != -1)
                            EquipmentPresenter.get().setSelectedObject(equipment);
                    }
                }
            }
        });

    }

    private void initRadioButton() {
        mRadioButtonElQ.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                setVisibleEditButton();
            }
        });
        mRadioButtonRenting.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                setVisibleEditButton();
            }
        });
    }

    private void initTextAreaDescription() {
        mTextAreaDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextAreaDescription.focusedProperty().get()) {
                    setVisibleEditButton();
                }
            }
        });
    }

    private void initWorkerListView() {

        mListViewWorker.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName() + " " + item.getPost().getName());
                } else setText(null);
            }
        });
        mListViewWorker.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setDepartmentModel(mDepartmentModel);
                WorkerPresenter.get().setWorkerModel(newValue);
                WorkerPresenter.get().setSelectedObject(newValue);
            }
        }));
        mListViewWorker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue)
                    WorkerPresenter.get().setSelectedObject(mListViewWorker.getSelectionModel().getSelectedItem());
            }
        });

    }

    private void initPopup() {
        new BasePopup(mTreeTableEquipmentInventory, BasePopup.getEquipmentInventoryPopup(), this);
        new BasePopup(mListViewLocation, BasePopup.getBaseListPopup(), null);
        new BasePopup(mListViewWorker, BasePopup.getBaseListPopup(), this);
    }

    private void setVisibleEditButton() {
        mTextAreaDescription.setPrefHeight(mTextAreaDescription.getMinHeight());
        mButtonUpdate.setVisible(true);
    }

    private void setInvisibleEditButton(){

        mButtonUpdate.setVisible(false);
        mTextAreaDescription.setPrefHeight(mTextAreaDescription.getMaxHeight());
    }

    private void selectedEquipment(TreeItem<EquipmentInventoryModel> treeEquipment) {
        if (treeEquipment != null) {
            EquipmentInventoryModel equipment = treeEquipment.getValue();
            if (equipment != null && equipment.getId() != -1) {
                EquipmentPresenter.get().setEquipmentInventoryModel(equipment);
                EquipmentPresenter.get().setEquipmentModel(equipment.getEquipmentModel());
                EquipmentPresenter.get().setSelectedObject(equipment);
                TabControllerService.get().getListenerSecondTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEquipmentInventoryResource()));
                ListenersService.get().updateUI(EquipmentInventoryModel.class);
            } else {
                EquipmentPresenter.get().setSelectedObject(null);
            }
        }
    }

    @FXML
    private void onClickAddWorker() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddWorkerDepartmentWindow(getStage());
    }

    @FXML
    private void onClickAddLocation() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddLocationWindow(getStage());
    }

    @FXML
    private void onClickEdit() {
        if (mBaseValidator.validate()) {
            setInvisibleEditButton();
            DepartmentPresenter.get().editDepartment(mTextFieldNumber.getText(), mTextFieldName.getText(), mRadioButtonElQ.isSelected(), mRadioButtonRenting.isSelected(),
                    mTextAreaDescription.getText(), mComboBoxArea.getValue());
        }
    }

    @FXML
    private void onClickConfig() {
        DepartmentPresenter.get().setTypeDocuments(AbstractModel.getTypeDoc());
        new Coordinator().goToFilesDepartmentWindow((Stage) anchorPaneEditDepartment.getScene().getWindow());

    }

    @FXML
    private void onClickPhoto() {
        DepartmentPresenter.get().setTypeDocuments(AbstractModel.getTypePhoto());
        new Coordinator().goToPhotoDepartmentWindow((Stage) anchorPaneEditDepartment.getScene().getWindow());
    }

    private EquipmentInventoryModel getEmptySecondLvlEquipment(EquipmentInventoryModel equipment) {
        return new EquipmentInventoryModel(
                -1,
                new InventoryNumberModel(equipment.getInventoryNumber().getId(), equipment.getInventoryNumber().getName()),
                new EquipmentModel(-1, equipment.getEquipmentModel().getName()),
                null,
                new StateModel(-1, ""));
    }

    private EquipmentInventoryModel getEmptyRootEquipment(EquipmentInventoryModel equipment) {
        return new EquipmentInventoryModel(
                -1,
                new InventoryNumberModel(-1, ""),
                new EquipmentModel(equipment.getEquipmentModel().getId(), equipment.getEquipmentModel().getName() + " (1)"),
                null,
                new StateModel(-1, ""));
    }

    private int getSizeItem(TreeItem<EquipmentInventoryModel> item) {
        int size = 0;
        for (TreeItem<EquipmentInventoryModel> rootEquipment : item.getChildren()) {
            size += rootEquipment.getChildren().size();
        }
        return size;
    }

    private void updateEquipmentTable(ObservableList<EquipmentInventoryModel> equipmentList) {
        TreeItem<EquipmentInventoryModel> rootItem = new TreeItem<>();
        boolean firstLvlFlag = false;
        boolean secondLvlFlag = false;
        for (EquipmentInventoryModel equipment : equipmentList) {
            for (TreeItem<EquipmentInventoryModel> rootEquipment : rootItem.getChildren()) {
                if (rootEquipment.getValue().getEquipmentModel().getId() == equipment.getEquipmentModel().getId()) {
                    for (TreeItem<EquipmentInventoryModel> secondLvlEquipment : rootEquipment.getChildren()) {
                        if (secondLvlEquipment.getValue().getInventoryNumber().getId() == equipment.getInventoryNumber().getId()) {
                            secondLvlFlag = true;
                            secondLvlEquipment.getChildren().add(new TreeItem<>(equipment));
                            secondLvlEquipment.getValue().getEquipmentModel().setName(equipment.getEquipmentModel().getName() + " (" + secondLvlEquipment.getChildren().size() + ")");
                            break;
                        }
                    }
                    if (!secondLvlFlag) {
                        TreeItem<EquipmentInventoryModel> treeItemSecond = new TreeItem<>(getEmptySecondLvlEquipment(equipment));
                        treeItemSecond.getChildren().add(new TreeItem<>(equipment));
                        rootEquipment.getChildren().add(treeItemSecond);
                    } else {
                        secondLvlFlag = false;
                    }
                    firstLvlFlag = true;
                    rootEquipment.getValue().getEquipmentModel().setName(equipment.getEquipmentModel().getName() + " (" + getSizeItem(rootEquipment) + ")");
                    break;
                }
            }
            if (!firstLvlFlag) {
                TreeItem<EquipmentInventoryModel> treeItemFirst = new TreeItem<>(getEmptyRootEquipment(equipment));
                TreeItem<EquipmentInventoryModel> treeItemSecond = new TreeItem<>(getEmptySecondLvlEquipment(equipment));
                treeItemSecond.getChildren().add(new TreeItem<>(equipment));
                treeItemFirst.getChildren().add(treeItemSecond);
                rootItem.getChildren().add(treeItemFirst);

            } else {
                firstLvlFlag = false;
            }
        }
        mTreeTableEquipmentInventory.setRoot(rootItem);
        mTreeTableEquipmentInventory.setShowRoot(false);
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(DepartmentModel.class.getName())) {
            mDepartmentModel = DepartmentPresenter.get().getDepartmentModel();
            mTextFieldName.setText(mDepartmentModel.getName());
            mTextFieldNumber.setText(mDepartmentModel.getNumber());
            mTextAreaDescription.setText(mDepartmentModel.getDescription());
            mRadioButtonElQ.setSelected(mDepartmentModel.isElectronicQ());
            mRadioButtonRenting.setSelected(mDepartmentModel.isRenting());
            mListViewWorker.setItems(mDepartmentModel.getObsWorkerList());
            mListViewLocation.setItems(mDepartmentModel.getObsLocationList());

            mComboBoxArea.setItems(DepartmentPresenter.get().getObservableArea());
            mComboBoxArea.getSelectionModel().select(mDepartmentModel.getAreaModel());
            setInvisibleEditButton();
            updateEquipmentTable(mDepartmentModel.getObsEquipmentList());
            mSecondLvlTabPane.getSelectionModel().select(0);
            if (mSecondLvlTabPane.getTabs().size() > 1) mSecondLvlTabPane.getTabs().remove(1);
            TabControllerService.get().setListenerSecondTabPane(((Tab nextTab) -> nextTab(nextTab, mSecondLvlTabPane)));

        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            mTreeTableEquipmentInventory.refresh();
        }
        if (updateClass.getName().equals(WorkerModel.class.getName())) {
            mListViewWorker.refresh();
        }

    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            updateEquipmentTable(mDepartmentModel.getObsEquipmentList());
            ListenersService.get().updateUI(TabPaneSecondLvlTabController.class);
        }
        if (updateClass.getName().equals(WorkerModel.class.getName())) {
            mListViewWorker.setItems(mDepartmentModel.getObsWorkerList());
        }
        if (updateClass.getName().equals(LocationModel.class.getName())) {
            mListViewLocation.setItems(mDepartmentModel.getObsLocationList());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }

    @Override
    public void primaryClick(String id) {
        System.out.println("node id " + id);
        switch (id) {
            case "mListViewWorker":
                new Coordinator().goToEditWorkerDepartmentWindow(getStage());
                break;
            case "inventoryLog":
                new Coordinator().goToInventoryNumberLog(getStage());
                break;
            case "statusLog":
                new Coordinator().goToEquipmentStateLog(getStage());
                break;
            case "moveLog":
                new Coordinator().goToMovementsEquipmentInventoryLog(getStage());
                break;
        }
    }
}
