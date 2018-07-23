package UI.Department.Controller;

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
import Service.UpdateService;
import UI.Coordinator;
import Service.IUpdateUI;
import Service.TabControllerService;
import UI.TabPane.Controller.TabPaneSecondLvlController;
import UI.Validator.ControllerValidator;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class EditDepartmentController implements IUpdateUI {

    private static DepartmentModel mDepartmentModel;

    public EditDepartmentController() {
        mDepartmentModel = DepartmentPresenter.get().getDepartmentModel();
        UpdateService.get().addListener(this::updateUI);
        System.out.println("edit department controller");
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

    /*    @FXML
    private TableView<EquipmentInventoryModel> tableViewEquipmentInventory;

    @FXML
    private TableColumn<EquipmentInventoryModel, String> nameEquipmentColumn, numberEquipmentColumn, stateEquipmentColumn, descriptionEquipmentColumn;*/

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

    private List<JFXTextField> mValidTextFields;
    private AreaModel mSelectedArea;

    @FXML
    public void initialize() {
        System.out.println("edit department initialize ");
        mAnchorPaneBasicInfoDepartment.setPrefSize(450.0, 190.0);
/*        nameEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getEquipmentModel().nameProperty());
        numberEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getInventoryNumber().nameProperty());
        stateEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getLastEntity().getStateModel().nameProperty());
        descriptionEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().description_departmentProperty());
        tableViewEquipmentInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedEquipment(newValue));*/

        mNameEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getEquipmentModel().nameProperty());
        mNumberEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getInventoryNumber().nameProperty());
        mStateEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getLastEntity().getStateModel().nameProperty());
        mTreeTableEquipmentInventory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
        mButtonUpdate.setFocusTraversable(false);

        mValidTextFields = ControllerValidator.setTextFieldValidator(mTextFieldName, mTextFieldNumber);

        for (JFXTextField text : mValidTextFields) {
            text.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (text.focusedProperty().get()) {
                        setVisibleEditButton();
                    }
                }
            });
        }
        mTextAreaDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextAreaDescription.focusedProperty().get()) {
                    setVisibleEditButton();
                }
            }
        });

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
                return new AreaModel(-1,mComboBoxArea.getEditor().getText());
            }
        });
        mComboBoxArea.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(AreaModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        mComboBoxArea.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            mSelectedArea = newValue;
            setVisibleEditButton();
        }));
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

        mListViewLocation.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(LocationModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });

        mListViewWorker.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName() + " " + item.getPost());
                } else setText(null);
            }
        });
        mListViewPurchase.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(PurchaseModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getDate().toString() + " " + item.getDescription());
                } else setText(null);
            }
        });

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
                System.out.println("selected equipment");
                EquipmentPresenter.get().setEquipmentInventoryModel(equipment);
                EquipmentPresenter.get().setEquipmentModel(equipment.getEquipmentModel());
                /*TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEquipmentInventoryResource()));*/
                TabControllerService.get().getListenerThirdTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEquipmentInventoryResource()));
                UpdateService.get().updateUI(EquipmentInventoryModel.class);
            }
        }
    }

    @FXML
    private void onClickAddWorker() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddWorkerDepartmentWindow((Stage) anchorPaneEditDepartment.getScene().getWindow(), 100.0, 200.0);
    }

    @FXML
    private void onClickAddLocation() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddLocationWindow((Stage) anchorPaneEditDepartment.getScene().getWindow(), 270.0, 150.0);
    }

    @FXML
    private void onClickAddPurchase() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddPurchaseWindow((Stage) anchorPaneEditDepartment.getScene().getWindow(), 360.0, 335.0);
    }

    @FXML
    private void onClickDelete() {
        DepartmentPresenter.get().deleteDepartment(mDepartmentModel.getId());
    }

    @FXML
    private void onClickEdit() {
        setInvisibleEditButton();
/*        DepartmentPresenter.get().editDepartment(mTextFieldNumber.getText(), mTextFieldName.getText(), mRadioButtonElQ.isSelected(), mRadioButtonRenting.isSelected(),
                mTextAreaDescription.getText(), mDepartmentModel.getAreaModel());*/
    }

    @FXML
    private void onClickConfig() {
        DepartmentPresenter.get().setTypeDocuments(mDepartmentModel.getTypeDoc());
        new Coordinator().goToFilesDepartmentWindow((Stage) anchorPaneEditDepartment.getScene().getWindow(), 100.0, 200.0);

    }

    @FXML
    private void onClickPhoto() {
        DepartmentPresenter.get().setTypeDocuments(mDepartmentModel.getTypePhoto());
        new Coordinator().goToPhotoDepartmentWindow((Stage) anchorPaneEditDepartment.getScene().getWindow(), 100.0, 200.0);
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
            // tableViewEquipmentInventory.setItems(mDepartmentModel.getObsEquipmnetList());
            mListViewLocation.setItems(mDepartmentModel.getObsLocationList());
            mListViewPurchase.setItems(mDepartmentModel.getObservableList());
            mComboBoxArea.setItems(DepartmentPresenter.get().getObservableArea());
            mComboBoxArea.getSelectionModel().select(mDepartmentModel.getAreaModel());
            setInvisibleEditButton();
            updateEquipmentTable(mDepartmentModel.getObsEquipmnetList());
            UpdateService.get().updateUI(TabPaneSecondLvlController.class);
        }
    }

}
