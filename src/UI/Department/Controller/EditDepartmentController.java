package UI.Department.Controller;

import Model.AbstractModel;
import Model.Area.AreaModel;
import Model.Department.DepartmentModel;
import Model.Department.PurchaseModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.EquipmentStateModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Location.LocationModel;
import Model.State.StateModel;
import Model.Worker.WorkerModel;
import Presenter.DepartmentPresenter;
import Presenter.EquipmentPresenter;
import Presenter.LocationPresenter;
import Presenter.WorkerPresenter;
import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.Coordinator;
import UI.Popup.BasePopup;
import UI.TabPane.Controller.TabPaneSecondLvlController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EditDepartmentController implements IUpdateUI {

    private static DepartmentModel mDepartmentModel;
    private BaseValidator mBaseValidator = new BaseValidator();

    public EditDepartmentController() {
        mDepartmentModel = DepartmentPresenter.get().getDepartmentModel();
        UpdateService.get().addListenerUI(this);
    }

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
    private JFXListView<PurchaseModel> mListViewPurchase;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private TreeTableView<EquipmentInventoryModel> mTreeTableEquipmentInventory;

    @FXML
    private TreeTableColumn<EquipmentInventoryModel, String> mNameEquipmentColumn, mNumberEquipmentColumn, mStateEquipmentColumn, mDescriptionEquipmentColumn;

    @FXML
    private JFXButton mButtonUpdate;

    @FXML
    private AnchorPane anchorPaneEditDepartment, mAnchorPaneBasicInfoDepartment;

    @FXML
    public void initialize() {
        System.out.println("edit department initialize ");
        mAnchorPaneBasicInfoDepartment.setPrefSize(450.0, 190.0);
        mButtonUpdate.setFocusTraversable(false);

        initTextFields();
        initTreeTableEquipmentInventory();
        initComboBoxArea();
        initTextAreaDescription();
        initRadioButton();
        initPurchaseListView();
        initLocationListView();
        initWorkerListView();
        initPopup();
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

    private void initPurchaseListView() {
        mListViewPurchase.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(PurchaseModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getDate().toString() + " " + item.getDescription());
                } else setText(null);
            }
        });
        mListViewPurchase.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setDepartment(mDepartmentModel);
                DepartmentPresenter.get().setSelectedObject(newValue);
                DepartmentPresenter.get().setPurchaseModel(newValue);
            }
        }));
        mListViewPurchase.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue)
                    DepartmentPresenter.get().setSelectedObject(mListViewPurchase.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void initTreeTableEquipmentInventory() {
        mNameEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getEquipmentModel().nameProperty());
        mNumberEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getInventoryNumber().nameProperty());
        mStateEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getLastEntity().getStateModel().nameProperty());
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

    private void initWorkerListView() {


        mListViewWorker.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName() + " " + item.getPost());
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

    private void initComboBoxArea() {
        mComboBoxArea.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(AreaModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        mComboBoxArea.setConverter(new StringConverter<AreaModel>() {
            @Override
            public String toString(AreaModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public AreaModel fromString(String string) {
                if (!string.isEmpty())
                    return new AreaModel(-1, string);
                else return null;
            }
        });
        mComboBoxArea.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(AreaModel item, boolean empty) {
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        mComboBoxArea.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            setVisibleEditButton();
        }));
    }

    private void initPopup() {
        new BasePopup(mTreeTableEquipmentInventory, BasePopup.getEquipmentInventoryPopup());
        new BasePopup(mListViewPurchase, BasePopup.getBaseListPopup());
        new BasePopup(mListViewLocation, BasePopup.getBaseListPopup());
        new BasePopup(mListViewWorker, BasePopup.getBaseListPopup());
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
                TabControllerService.get().getListenerThirdTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEquipmentInventoryResource()));
                UpdateService.get().updateUI(EquipmentInventoryModel.class);
            }
        }
    }

    @FXML
    private void onClickAddWorker() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddWorkerDepartmentWindow((Stage) anchorPaneEditDepartment.getScene().getWindow());
    }

    @FXML
    private void onClickAddLocation() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddLocationWindow((Stage) anchorPaneEditDepartment.getScene().getWindow());
    }

    @FXML
    private void onClickAddPurchase() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddPurchaseWindow((Stage) anchorPaneEditDepartment.getScene().getWindow());
    }

    @FXML
    private void onClickDelete() {
        DepartmentPresenter.get().deleteDepartment(mDepartmentModel.getId());
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

    private void updateEquipmentTable(ObservableList<EquipmentInventoryModel> equipmentList) {
        TreeItem<EquipmentInventoryModel> rootItem = new TreeItem<>();
        boolean flag = false;
        for (EquipmentInventoryModel equipment : equipmentList) {
            for (TreeItem<EquipmentInventoryModel> treeEquipment : rootItem.getChildren()) {
                if (treeEquipment.getValue().getEquipmentModel().getId() == equipment.getEquipmentModel().getId()) {
                    treeEquipment.getChildren().add(new TreeItem<>(equipment));
                    flag = true;
                }
            }
            if (!flag) {
                EquipmentInventoryModel emptyEquipment = new EquipmentInventoryModel(
                        -1,
                        new InventoryNumberModel(-1, ""),
                        new EquipmentModel(equipment.getEquipmentModel().getId(), equipment.getEquipmentModel().getName()),
                        null);
                emptyEquipment.getEntityList().add(new EquipmentStateModel(-1, new StateModel(-1, "")));
                TreeItem<EquipmentInventoryModel> treeItemFirst = new TreeItem<>(emptyEquipment);
                treeItemFirst.getChildren().add(new TreeItem<>(equipment));
                rootItem.getChildren().add(treeItemFirst);

            } else {
                flag = false;
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
            mListViewPurchase.setItems(mDepartmentModel.getObservableEntityList());
            mComboBoxArea.setItems(DepartmentPresenter.get().getObservableArea());
            mComboBoxArea.getSelectionModel().select(mDepartmentModel.getAreaModel());
            setInvisibleEditButton();
            updateEquipmentTable(mDepartmentModel.getObsEquipmnetList());
            UpdateService.get().updateUI(TabPaneSecondLvlController.class);

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
            updateEquipmentTable(mDepartmentModel.getObsEquipmnetList());
            UpdateService.get().updateUI(TabPaneSecondLvlController.class);
        }
        if (updateClass.getName().equals(WorkerModel.class.getName())) {
            mListViewWorker.setItems(mDepartmentModel.getObsWorkerList());
        }
        if (updateClass.getName().equals(LocationModel.class.getName())) {
            mListViewLocation.setItems(mDepartmentModel.getObsLocationList());
        }
        if (updateClass.getName().equals(PurchaseModel.class.getName())) {
            mListViewPurchase.setItems(mDepartmentModel.getObservableEntityList());
        }
    }

}
