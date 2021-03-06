package UI.Equipment.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.EquipmentParameterModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Parameter.ParameterModel;
import Model.State.StateModel;
import Model.Type.TypeModel;
import Presenter.EquipmentInventoryPresenter;
import Presenter.EquipmentPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static UI.BaseTabController.nextTab;

public class EditEquipmentController extends BaseController implements IOnMouseClick {

    private static EquipmentModel sEquipmentModel;
    private BaseValidator mBaseValidator = new BaseValidator();
    private BaseValidator mBaseValidatorDialog = new BaseValidator();
    private ObservableList<EquipmentParameterModel> mEquipmentParameterList;

    @FXML
    private JFXTabPane mSecondLvlTabPane;

    @FXML
    private JFXTextField mTextFieldName, mTextFieldNameFact;
    @FXML
    private JFXTextArea mTextAreaDescription;
    @FXML
    private JFXButton mButtonUpdate;
    @FXML
    private ComboBox<TypeModel> comboBoxType;
    @FXML
    private TreeTableView<EquipmentParameterModel> mTreeTableViewParameter;
    @FXML
    private TreeTableColumn<EquipmentParameterModel, String> mColumnNameParameter, mColumnValueParameter;
    @FXML
    private TreeTableView<EquipmentInventoryModel> mTreeTableEquipmentInventory;
    @FXML
    private TreeTableColumn<EquipmentInventoryModel, String> mDepartmentEquipmentColumn, mNumberEquipmentColumn, mStateEquipmentColumn, mDescriptionEquipmentColumn;
    @FXML
    private StackPane mStackPaneEditEquipment;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<EquipmentInventoryModel> mModelList;
    private FilteredList<EquipmentInventoryModel> mFilteredList;

    public EditEquipmentController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
        mModelList = FXCollections.observableArrayList();
        initTableViewEquipment();
        initTextField();
        initTextArea();
        initTableVieParameter();
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mTreeTableEquipmentInventory, BasePopup.getEquipmentInventoryPopup(), this);
    }

    private void initTableViewEquipment() {
        mDepartmentEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getDepartmentModel().nameProperty());
        mNumberEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getInventoryNumber().nameProperty());
        mStateEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getStateModel().nameProperty());
        mDescriptionEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().descriptionProperty());
        mTreeTableEquipmentInventory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
    }

    private void initTableVieParameter() {
        mColumnNameParameter.setCellValueFactory(cellData -> cellData.getValue().getValue().getParameterModel().nameProperty());
        mColumnValueParameter.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mColumnValueParameter.setCellFactory((TreeTableColumn<EquipmentParameterModel, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        mColumnValueParameter.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<EquipmentParameterModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<EquipmentParameterModel, String> event) {
                TreeItem<EquipmentParameterModel> currentEditEquipment = mTreeTableViewParameter.getTreeItem(event.getTreeTablePosition().getRow());
                currentEditEquipment.getValue().setName(event.getNewValue());
                for (EquipmentParameterModel parameter : sEquipmentModel.getEntityList()) {
                    if (parameter.getId() == currentEditEquipment.getValue().getId()) {
                        parameter.setName(event.getNewValue());
                    }

                }
                EquipmentPresenter.get().editEquipment(sEquipmentModel);
            }
        });
        mTreeTableViewParameter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println("secondary delete item");
                    EquipmentParameterModel equipmentParameter = mTreeTableViewParameter.getSelectionModel().getSelectedItem().getValue();
                    if (equipmentParameter.getId() != -1) {
                        mEquipmentParameterList.remove(equipmentParameter);
                        sEquipmentModel.setEntityList(mEquipmentParameterList);
                        EquipmentPresenter.get().deleteEquipmentParameter(equipmentParameter);
                        updateTableParameter(mEquipmentParameterList);
                    }
                }
            }
        });
        mTreeTableViewParameter.setTooltip(new Tooltip("ПКМ для удаления"));
        mTreeTableViewParameter.setEditable(true);
        mTreeTableViewParameter.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedParameter(newValue)));
    }

    private void selectedParameter(TreeItem<EquipmentParameterModel> newValue) {
        if (newValue != null) {
            if (newValue.getValue().getId() == -1) {
                createDialog();
            }
        }
    }

    private void createDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Добавить параметр"));

        JFXComboBox<ParameterModel> comboBox = initJFXComboBox(new EquipmentParameterModel(),
                new JFXComboBox(EquipmentPresenter.get().getObservableEquipmentParameter(mEquipmentParameterList)), false, "Выберите параметр", "Параметр");
        comboBox.setLabelFloat(true);
        comboBox.setPromptText("Параметр");
        comboBox.setFocusColor(Paint.valueOf("#40a85f"));
        comboBox.setPrefWidth(200);

        ValidationFacade facade = new ValidationFacade();
        facade.setControl(comboBox);

        Label errorLabel = new Label();
        errorLabel.setFont(new Font(11));
        errorLabel.setVisible(false);

        mBaseValidatorDialog.setValidationFacades(new Pair(facade, errorLabel, comboBox));

        JFXTextField text = new JFXTextField();
        text.setLabelFloat(true);
        text.setPromptText("Значение");
        text.setFocusColor(Paint.valueOf("#40a85f"));

        Pane pane = new Pane();
        pane.setPrefHeight(10.0);
        pane.getChildren().add(facade);
        pane.getChildren().add(errorLabel);

        HBox box = new HBox();
        box.getChildren().add(pane);
        box.getChildren().add(text);
        box.setSpacing(10);

        JFXButton button = new JFXButton("Сохранить");
        button.setRipplerFill(Paint.valueOf("#40a85f"));
        button.setPrefHeight(35.0);

        content.setBody(box);
        content.setActions(button);
        JFXDialog dialog = new JFXDialog(mStackPaneEditEquipment, content, JFXDialog.DialogTransition.TOP);
        dialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                mTreeTableViewParameter.getSelectionModel().clearSelection();
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mBaseValidatorDialog.validate()) {
                    mEquipmentParameterList.add(new EquipmentParameterModel(0, text.getText().trim(), comboBox.getValue()));
                    sEquipmentModel.setEntityList(mEquipmentParameterList);
                    EquipmentPresenter.get().editEquipment(sEquipmentModel);
                    updateTableParameter(mEquipmentParameterList);
                    dialog.close();
                }
            }
        });
        dialog.show();
    }

    private void updateTableParameter(ObservableList<EquipmentParameterModel> equipmentParameters) {
        TreeItem<EquipmentParameterModel> rootItem = new TreeItem<>();
        for (EquipmentParameterModel log : equipmentParameters) {
            rootItem.getChildren().add(new TreeItem<>(log));
        }
        rootItem.getChildren().add(new TreeItem<>(new EquipmentParameterModel(-1, "", new ParameterModel(-1, "Добавить параметр"))));
        mTreeTableViewParameter.setRoot(rootItem);
        mTreeTableViewParameter.setShowRoot(false);
    }

    private void initTextArea() {
        mTextAreaDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextAreaDescription.focusedProperty().get())
                    setVisibleEditButton();
            }
        });
    }

    private void initTextField() {
        mBaseValidator.setJFXTextFields(mTextFieldName, mTextFieldNameFact);
        mTextFieldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextFieldName.focusedProperty().get())
                    setVisibleEditButton();
            }
        });
        mTextFieldNameFact.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextFieldNameFact.focusedProperty().get())
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
                if (equipment.getInventoryNumber().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (equipment.getDepartmentModel().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (equipment.getStateModel().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else
                    return equipment.getDescription() != null && equipment.getDescription().toLowerCase().contains(lowerCaseFilter);
            });
            updateTree();
        }));
    }

    private void updateTree() {
        TreeItem<EquipmentInventoryModel> rootItem = new TreeItem<>();
        SortedList<EquipmentInventoryModel> sortedList = new SortedList<>(mFilteredList);
        for (EquipmentInventoryModel equipmentModel : sortedList) {
            if (mTextFieldSearch.getText().trim().isEmpty()) {
                onUnSortedList(equipmentModel, rootItem);
            } else {
                onSortedList(equipmentModel, rootItem);
            }
        }
        mTreeTableEquipmentInventory.setRoot(rootItem);
        mTreeTableEquipmentInventory.setShowRoot(false);
    }

    private void onSortedList(EquipmentInventoryModel equipmentModel, TreeItem<EquipmentInventoryModel> rootItem) {
        rootItem.getChildren().add(new TreeItem<>(equipmentModel));
    }

    private void onUnSortedList(EquipmentInventoryModel equipmentModel, TreeItem<EquipmentInventoryModel> rootItem) {
        boolean flag = false;
        for (TreeItem<EquipmentInventoryModel> treeEquipment : rootItem.getChildren()) {
            if (treeEquipment.getValue().getInventoryNumber().getId() == equipmentModel.getInventoryNumber().getId()) {
                treeEquipment.getChildren().add(new TreeItem<>(equipmentModel));
                flag = true;
                treeEquipment.getValue().getInventoryNumber().setName(equipmentModel.getInventoryNumber().getName() + " (" + treeEquipment.getChildren().size() + ")");
                break;
            }
        }
        if (!flag) {
            EquipmentInventoryModel emptyEquipment = new EquipmentInventoryModel(
                    -1,
                    new InventoryNumberModel(equipmentModel.getInventoryNumber().getId(), equipmentModel.getInventoryNumber().getName()),
                    null,
                    new DepartmentModel(-1, ""),
                    new StateModel(-1, ""));
            TreeItem<EquipmentInventoryModel> treeItemFirst = new TreeItem<>(emptyEquipment);
            treeItemFirst.getChildren().add(new TreeItem<>(equipmentModel));
            rootItem.getChildren().add(treeItemFirst);

        }
    }

    private void selectedEquipment(TreeItem<EquipmentInventoryModel> treeEquipment) {
        if (treeEquipment != null) {
            EquipmentInventoryModel equipment = treeEquipment.getValue();
            if (equipment != null && equipment.getId() != -1) {
                EquipmentInventoryPresenter.get().setEquipmentInventoryModel(equipment);
                TabControllerService.get().getListenerSecondTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEquipmentInventoryResource()));
                ListenersService.get().updateUI(EquipmentInventoryModel.class);
            } else {
                EquipmentInventoryPresenter.get().setEquipmentInventoryModel(null);
            }
            //new Coordinator().goToEquipentInventoryWindow((Stage) mStackPaneEditEquipment.getScene().getWindow());
        }
    }


    private void setVisibleEditButton() {
        mTextAreaDescription.setPrefHeight(mTextAreaDescription.getMinHeight());
        mButtonUpdate.setVisible(true);
    }

    private void setInvisibleEditButton() {

        mButtonUpdate.setVisible(false);
        mTextAreaDescription.setPrefHeight(mTextAreaDescription.getMaxHeight());
    }

    @FXML
    private void onClickEdit() {
        if (mBaseValidator.validate()) {
            EquipmentPresenter.get().editEquipment(mTextFieldName.getText(), mTextFieldNameFact.getText(), mTextAreaDescription.getText(), mEquipmentParameterList);
            setInvisibleEditButton();
        }
    }

    @FXML
    private void onClickAddEquipmentInventory() {
        new Coordinator().goToAddEquipmentInventoryWindow(getStage());
    }

    private void updateEquipmentTable(ObservableList<EquipmentInventoryModel> equipmentList) {
        mModelList = equipmentList;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        updateTree();
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentModel.class.getName())) {
            System.out.println("updateUI edit equipment");
            mTextFieldSearch.setText("");
            sEquipmentModel = EquipmentPresenter.get().getEquipmentModel();
            mEquipmentParameterList = sEquipmentModel.getObservableEntityList();
            mTextFieldName.setText(sEquipmentModel.getName());
            mTextFieldNameFact.setText(sEquipmentModel.getNameFact());
            mTextAreaDescription.setText(sEquipmentModel.getDescription());
            updateTableParameter(mEquipmentParameterList);
            updateEquipmentTable(sEquipmentModel.getObservableEqInventoryList());
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
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            mTextFieldSearch.setText("");
            updateEquipmentTable(sEquipmentModel.getObservableEqInventoryList());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {
        if (updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            mTextFieldSearch.setText("");
            mSecondLvlTabPane.getSelectionModel().select(0);
            updateEquipmentTable(sEquipmentModel.getObservableEqInventoryList());
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) mStackPaneEditEquipment.getScene().getWindow();
    }

    @Override
    public void primaryClick(String id) {
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
        }
    }
}
