package UI.Department.Controller;

import Model.AbstractModel;
import Model.Area.AreaModel;
import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Location.LocationModel;
import Model.State.StateModel;
import Model.Worker.WorkerModel;
import Presenter.*;
import Service.IOnMouseClick;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static UI.BaseTabController.nextTab;

public class EditDepartmentController extends BaseController implements IOnMouseClick {

    private static DepartmentModel mDepartmentModel;
    private BaseValidator mBaseValidator = new BaseValidator();
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
    @FXML
    private BorderPane mAvatarImage;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<EquipmentInventoryModel> mModelList;
    private FilteredList<EquipmentInventoryModel> mFilteredList;

    public EditDepartmentController() {
        mDepartmentModel = DepartmentPresenter.get().getDepartmentModel();
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
        System.out.println("edit department initialize ");
        mButtonUpdate.setFocusTraversable(false);
        mModelList = FXCollections.observableArrayList();
        initTextFields();
        initTreeTableEquipmentInventory();
        initComboBox();
        initTextAreaDescription();
        initRadioButton();
        initLocationListView();
        initWorkerListView();
        initPopup();

    }

    private void initComboBox() {
        initJFXComboBox(new AreaModel(), mComboBoxArea, true, "Выберите район", "Район");
        mComboBoxArea.getSelectionModel().selectedIndexProperty().addListener((observable -> selectedArea()));
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
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        mTextFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            mFilteredList.setPredicate(equipment -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (equipment.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (equipment.getInventoryNumber().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (equipment.getStateModel().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else
                    return equipment.getDescription_department() != null && equipment.getDescription_department().toLowerCase().contains(lowerCaseFilter);
            });
            updateTree();
        }));
    }

    private void updateTree() {
        TreeItem<EquipmentInventoryModel> rootItem = new TreeItem<>();

        SortedList<EquipmentInventoryModel> sortedList = new SortedList<>(mFilteredList);
        for (EquipmentInventoryModel equipment : sortedList) {
            if (mTextFieldSearch.getText().trim().isEmpty()) {
                onUnSortedList(equipment, rootItem);
            } else {
                onSortedList(equipment, rootItem);
            }
        }
        mTreeTableEquipmentInventory.setRoot(rootItem);
        mTreeTableEquipmentInventory.setShowRoot(false);
    }

    private void onSortedList(EquipmentInventoryModel equipment, TreeItem<EquipmentInventoryModel> rootItem) {
        rootItem.getChildren().add(new TreeItem<>(equipment));
    }

    private void onUnSortedList(EquipmentInventoryModel equipment, TreeItem<EquipmentInventoryModel> rootItem) {
        boolean firstLvlFlag = false;
        boolean secondLvlFlag = false;
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

        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) anchorPaneEditDepartment.getScene().getWindow();
    }

    @Override
    public void destroy() {

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
        }));
        mListViewLocation.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
              /*  if (newValue)
                    LocationPresenter.get().setLocation(mListViewLocation.getSelectionModel().getSelectedItem());*/
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
                    EquipmentInventoryPresenter.get().editEquipmentInventory(currentEditEquipment.getValue());
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
                            EquipmentInventoryPresenter.get().setEquipmentInventoryModel(equipment);
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
                System.out.println("selected worker");

            } else {
                WorkerPresenter.get().setWorkerModel(null);
            }

        }));
        mListViewWorker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    WorkerPresenter.get().setWorkerModel(mListViewWorker.getSelectionModel().getSelectedItem());
                }
                System.out.println("focus");
            }
        });

    }

    private void initPopup() {
        new BasePopup(mTreeTableEquipmentInventory, BasePopup.getEquipmentInventoryReadPopup(), this);
        new BasePopup(mListViewLocation, BasePopup.getBaseListPopup());
        new BasePopup(mListViewWorker, BasePopup.getBaseListPopup(), this);
        new BasePopup(mAvatarImage, BasePopup.getAvatarPopup(), this, true);
        //new BasePopup(mAvatarImage, BasePopup.getAvatarPopup(),this,true);

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
                EquipmentPresenter.get().setEquipmentModel(equipment.getEquipmentModel());
                EquipmentInventoryPresenter.get().setEquipmentInventoryModel(equipment);
                TabControllerService.get().getListenerSecondTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEquipmentInventoryResource()));
                ListenersService.get().updateUI(EquipmentInventoryModel.class);
            } else {
                EquipmentInventoryPresenter.get().setEquipmentInventoryModel(null);
            }
        }
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
        mModelList = equipmentList;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        updateTree();
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
    private void onClickDoc() {
        DepartmentPresenter.get().setTypeDocuments(AbstractModel.getTypeDoc());
        new Coordinator().goToFilesDepartmentWindow(getStage());
    }

    @FXML
    private void onClickConfig() {
        DepartmentPresenter.get().setTypeDocuments(AbstractModel.getTypeConfig());
        new Coordinator().goToFilesDepartmentWindow(getStage());
    }

    @FXML
    private void onClickPhoto() {
        DepartmentPresenter.get().setTypeDocuments(AbstractModel.getTypePhoto());
        new Coordinator().goToFilesDepartmentWindow(getStage());
        //new Coordinator().goToPhotoDepartmentWindow((Stage) anchorPaneEditDepartment.getScene().getWindow());
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(DepartmentModel.class.getName())) {
            mTextFieldSearch.setText("");
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
            TabControllerService.get().setListenerSecondTabPane(
                    ((Tab nextTab) -> nextTab(nextTab, mSecondLvlTabPane)));
            setAvatar(DepartmentPresenter.get().getPathAvatar(), mAvatarImage);
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
        if (updateClass.getName().equals(Departments.class.getName())) {
            setAvatar(DepartmentPresenter.get().getPathAvatar(), mAvatarImage);
        }

    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            mTextFieldSearch.setText("");
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
                new Coordinator().goToEditWorkerWindow(getStage());
                break;
            case "inventoryLog":
                new Coordinator().goToInventoryNumberEquipmentLog(getStage());
                break;
            case "statusLog":
                new Coordinator().goToEquipmentStateLog(getStage());
                break;
            case "moveLog":
                new Coordinator().goToMovementsEquipmentInventoryLog(getStage());
                break;
            case "editAvatar":
                DepartmentPresenter.get().setTypeDocuments(AbstractModel.getTypePhoto());
                DepartmentPresenter.get().uploadAvatar(getStage());
                break;
            case "deleteAvatar":
                System.out.println(" delete avatar");
                break;
            case "mAvatarImage":
                System.out.println("open avatar");
                break;
        }
    }
}
