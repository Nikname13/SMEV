package UI.Movement.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.SelectedEquipmentInventoryShell;
import Model.Inventory_number.InventoryNumberModel;
import Model.Worker.WorkerModel;
import Presenter.MovementPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AddMovementController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();
    private ObservableList<EquipmentInventoryModel> mEquipmentInventoryList;
    private JFXDialog mDialog;
    private TableView<SelectedEquipmentInventoryShell> mTableEquipmentDialog;
    private Pair mErrorTable;

    @FXML
    private JFXComboBox<DepartmentModel> mComboBoxDepartmentFrom, mComboBoxDepartmentTo;
    @FXML
    private JFXComboBox<WorkerModel> mComboBoxWorkerTo, mComboBoxWorkerFrom;
    @FXML
    private JFXTextArea mTextAreaBase;
    @FXML
    private ValidationFacade mFacadeWorkerFrom, mFacadeWorkerTo, mFacadeDepartmentTo, mFacadeDepartmentFrom;
    @FXML
    private Label mErrorWorkerFrom, mErrorWorkerTo, mErrorDepartmentTo, mErrorDepartmentFrom, mErrorEquipment;
    @FXML
    private TreeTableView<EquipmentInventoryModel> mTreeTableEquipment;
    @FXML
    private TreeTableColumn<EquipmentInventoryModel, String> mNameColumn, mInventoryNumberColumn;
    @FXML
    private StackPane mStackPaneAddMovement;

    @FXML
    public void initialize(){
        mEquipmentInventoryList = FXCollections.observableArrayList();
        mBaseValidator.setValidationFacades(
                new Pair(mFacadeDepartmentFrom, mErrorDepartmentFrom, mComboBoxDepartmentFrom),
                new Pair(mFacadeDepartmentTo, mErrorDepartmentTo, mComboBoxDepartmentTo),
                new Pair(mFacadeWorkerFrom, mErrorWorkerFrom, mComboBoxWorkerFrom),
                new Pair(mFacadeWorkerTo, mErrorWorkerTo, mComboBoxWorkerTo)
        );
        mBaseValidator.setJFXTextAreas(mTextAreaBase);
        initComboBoxDepartment(mComboBoxDepartmentFrom, false);
        initComboBoxDepartment(mComboBoxDepartmentTo, false);
        initDepartment();
        initComboBoxWorker(mComboBoxWorkerFrom, false);
        initComboBoxWorker(mComboBoxWorkerTo, false);
        initTableView();
        updateTableView(mEquipmentInventoryList);
    }

    private void updateTableView(ObservableList<EquipmentInventoryModel> equipmentInventoryList) {
        TreeItem<EquipmentInventoryModel> rootItem = new TreeItem<>();
        if (equipmentInventoryList != null) {
            for (EquipmentInventoryModel equipment : equipmentInventoryList) {
                rootItem.getChildren().add(new TreeItem<>(equipment));
            }
        }
        rootItem.getChildren().add(new TreeItem<>(new EquipmentInventoryModel(-1, new EquipmentModel(-1, "Добавить оборудование"), new InventoryNumberModel(-1, ""))));
        mTreeTableEquipment.setRoot(rootItem);
        mTreeTableEquipment.setShowRoot(false);
        mTreeTableEquipment.setFixedCellSize(50);
        mTreeTableEquipment.prefHeightProperty().bind(Bindings.size(mTreeTableEquipment.getRoot().getChildren())
                .multiply(mTreeTableEquipment.getFixedCellSize()).add(55));
        if (mEquipmentInventoryList.size() >= 1) mErrorTable.getErrorLabel().setVisible(false);
    }

    private void initTableView() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getEquipmentModel().nameFactProperty());
        mInventoryNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getInventoryNumber().nameProperty());
        mTreeTableEquipment.setTooltip(new Tooltip("ПКМ для удаления"));
        mTreeTableEquipment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedEquipment(newValue));
        mTreeTableEquipment.setEditable(true);
        mErrorTable = new Pair(mTreeTableEquipment, mErrorEquipment, "Необходимо добавить оборудование");
        mTreeTableEquipment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    EquipmentInventoryModel equipment = mTreeTableEquipment.getSelectionModel().getSelectedItem().getValue();
                    if (equipment.getId() != -1) {
                        mEquipmentInventoryList.remove(equipment);
                        updateTableView(mEquipmentInventoryList);
                        resizeHeightStage(mTreeTableEquipment.getPrefHeight());
                    }
                }
            }
        });
    }

    private void selectedEquipment(TreeItem<EquipmentInventoryModel> newValue) {
        System.out.println("selected tree table");
        if (newValue != null) {
            if (newValue.getValue().getId() == -1 && mComboBoxDepartmentFrom.getValue() != null) {
                showDialog();
            }
        }
    }

    private void showDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Выберите оборудование"));
        JFXButton button = new JFXButton("Сохранить");
        button.setRipplerFill(Paint.valueOf("#40a85f"));
        button.setPrefHeight(35.0);
        content.setBody(initDialogTableView());
        content.setActions(button);
        mDialog = new JFXDialog(mStackPaneAddMovement, content, JFXDialog.DialogTransition.CENTER);
        mDialog.show();
        mDialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                mTreeTableEquipment.getSelectionModel().clearSelection();
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (SelectedEquipmentInventoryShell equipment : mTableEquipmentDialog.getItems()) {
                    if (equipment.isChosen()) {
                        mEquipmentInventoryList.add(equipment);
                    }
                }
                updateTableView(mEquipmentInventoryList);
                if (mTreeTableEquipment.getPrefHeight() <= mTreeTableEquipment.getMaxHeight()) {
                    resizeHeightStage(mTreeTableEquipment.getPrefHeight());
                } else {
                    resizeHeightStage(mTreeTableEquipment.getMaxHeight());
                }
                mDialog.close();
            }
        });
    }

    private void resizeHeightStage(double tableHeight) {
        if (mEquipmentInventoryList.size() >= 1) {
            getStage().setHeight(getStage().getMinHeight() + (tableHeight - mTreeTableEquipment.getMinHeight()));
        } else {
            getStage().setHeight(getStage().getMinHeight());
        }
    }

    private TableView<SelectedEquipmentInventoryShell> initDialogTableView() {
        mTableEquipmentDialog = new TableView<>();
        TableColumn<SelectedEquipmentInventoryShell, Boolean> checkColumn = new TableColumn<>();
        checkColumn.setMinWidth(50.0);
        mTableEquipmentDialog.getColumns().add(checkColumn);

        TableColumn<SelectedEquipmentInventoryShell, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setMinWidth(150.0);
        mTableEquipmentDialog.getColumns().add(nameColumn);

        TableColumn<SelectedEquipmentInventoryShell, String> inventoryNumber = new TableColumn<>("Номер");
        inventoryNumber.setMinWidth(150.0);
        mTableEquipmentDialog.getColumns().add(inventoryNumber);


        checkColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        checkColumn.setCellValueFactory(cellData -> {
            SelectedEquipmentInventoryShell entity = cellData.getValue();
            BooleanProperty property = entity.chosenProperty();
            property.addListener(((observable, oldValue, newValue) -> entity.setChosen(newValue)));
            return property;
        });
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getEquipmentModel().nameFactProperty());
        inventoryNumber.setCellValueFactory(cellData -> cellData.getValue().getInventoryNumber().nameProperty());
        mTableEquipmentDialog.setItems(MovementPresenter.get().getEquipmentInventoryList(mComboBoxDepartmentFrom.getValue(), mEquipmentInventoryList));

        mTableEquipmentDialog.setMaxWidth(352.0);
        mTableEquipmentDialog.setMaxHeight(500.0);
        mTableEquipmentDialog.setFixedCellSize(50.0);
        mTableEquipmentDialog.setEditable(true);
        return mTableEquipmentDialog;
    }

    private void initDepartment() {
        mComboBoxDepartmentFrom.setItems(MovementPresenter.get().getObservableDepartment());
        mComboBoxDepartmentFrom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedDepartmentFrom(newValue));
        mComboBoxDepartmentTo.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedDepartmentTo(newValue)));
    }

    private void selectedDepartmentTo(DepartmentModel department) {
        if (department != null) {
            mComboBoxWorkerTo.setItems(department.getObsWorkerList());
            mComboBoxWorkerTo.getSelectionModel().selectFirst();
        }
    }

    private void selectedDepartmentFrom(DepartmentModel department) {
        if (department != null) {
            mEquipmentInventoryList = FXCollections.observableArrayList();
            mComboBoxDepartmentTo.setItems(MovementPresenter.get().getObservableDepartmentWithout(department));
            mComboBoxWorkerFrom.setItems(department.getObsWorkerList());
            mComboBoxWorkerFrom.getSelectionModel().selectFirst();
            updateTableView(mEquipmentInventoryList);
            resizeHeightStage(mTreeTableEquipment.getPrefHeight());
        }
    }

    @FXML
    private void onClickAdd(){
        if (validate()) {
            MovementPresenter.get().moveEquipmentInventory(mEquipmentInventoryList,
                    mComboBoxDepartmentTo.getValue(),
                    mComboBoxWorkerFrom.getValue(),
                    mComboBoxWorkerTo.getValue(),
                    mTextAreaBase.getText());
            close(mStackPaneAddMovement);
        }
    }

    private boolean validate() {
        boolean flag = true;
        if (mEquipmentInventoryList.size() < 1) {
            flag = false;
            mErrorTable.getErrorLabel().setVisible(true);
        }
        if (!mBaseValidator.validate()) {
            flag = false;
        }
        return flag;
    }

    @FXML
    private void onClickCancel() {
        close(mStackPaneAddMovement);
    }

    @Override
    protected Stage getStage() {
        return (Stage) mStackPaneAddMovement.getScene().getWindow();
    }
}
