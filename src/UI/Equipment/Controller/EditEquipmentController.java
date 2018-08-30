package UI.Equipment.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.EquipmentParameterModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.State.StateModel;
import Model.Type.TypeModel;
import Presenter.EquipmentPresenter;
import Service.IUpdateUI;
import Service.LisenersService;
import Service.TabControllerService;
import UI.Coordinator;
import UI.TabPane.Controller.TabPaneSecondLvlTabController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditEquipmentController implements IUpdateUI {

    private static EquipmentModel sEquipmentModel;
    private BaseValidator mBaseValidator = new BaseValidator();

    public EditEquipmentController() {
        LisenersService.get().addListenerUI(this);
        sEquipmentModel = EquipmentPresenter.get().getEquipmentModel();
    }

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
    private AnchorPane anchorPaneEditEquipment;

    @FXML
    public void initialize() {

        mDepartmentEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getDepartmentModel().nameProperty());
        mNumberEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getInventoryNumber().nameProperty());
        mStateEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getStateModel().nameProperty());
        mTreeTableEquipmentInventory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
        initTextField();
        initTextArea();
        initTableVieParameter();
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
                    System.out.println(parameter.getName());
                }
                EquipmentPresenter.get().editEquipment(sEquipmentModel);
            }
        });
        mTreeTableViewParameter.setEditable(true);
    }

    private void updateTableParameter(ObservableList<EquipmentParameterModel> equipmentParameters) {
        TreeItem<EquipmentParameterModel> rootItem = new TreeItem<>();
        for (EquipmentParameterModel log : equipmentParameters) {
            rootItem.getChildren().add(new TreeItem<>(log));
        }
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
    }

    private void selectedEquipment(TreeItem<EquipmentInventoryModel> treeEquipment) {
        if(treeEquipment!=null) {
            EquipmentInventoryModel equipment=treeEquipment.getValue();
            if (equipment != null && equipment.getId()!=-1) {
                EquipmentPresenter.get().setEquipmentInventoryModel(equipment);
                TabControllerService.get().getListenerThirdTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEquipmentInventoryResource()));
                LisenersService.get().updateUI(EquipmentInventoryModel.class);
            }
            //new Coordinator().goToEquipentInventoryWindow((Stage) anchorPaneEditEquipment.getScene().getWindow());
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
            EquipmentPresenter.get().editEquipment(mTextFieldName.getText(), mTextFieldNameFact.getText(), mTextAreaDescription.getText(), null);
            setInvisibleEditButton();
        }
    }

    @FXML
    private void onClickAddEquipmentInventory() {
        new Coordinator().goToAddEquipmentInventoryWindow((Stage) anchorPaneEditEquipment.getScene().getWindow(),100.0,200.0);
    }

    private void updateEquipmentTable(ObservableList<EquipmentInventoryModel> equipmentList) {
        TreeItem<EquipmentInventoryModel> rootItem = new TreeItem<>();
        boolean flag = false;
        for (EquipmentInventoryModel equipment : equipmentList) {
            for (TreeItem<EquipmentInventoryModel> treeEquipment : rootItem.getChildren()) {
                if (treeEquipment.getValue().getInventoryNumber().getId() == equipment.getInventoryNumber().getId()) {
                    treeEquipment.getChildren().add(new TreeItem<>(equipment));
                    flag = true;
                }
            }
            if (!flag) {
                EquipmentInventoryModel emptyEquipment = new EquipmentInventoryModel(
                        -1,
                        new InventoryNumberModel(equipment.getInventoryNumber().getId(), equipment.getInventoryNumber().getName()),
                        null,
                        new DepartmentModel(-1, ""),
                        new StateModel(-1, ""));
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
        if (updateClass.getName().equals(EquipmentModel.class.getName())) {
            sEquipmentModel = EquipmentPresenter.get().getEquipmentModel();
            sEquipmentModel.getEntityList(); // первое обращение в базу для загрузки оборудования
            mTextFieldName.setText(sEquipmentModel.getName());
            mTextFieldNameFact.setText(sEquipmentModel.getNameFact());
            mTextAreaDescription.setText(sEquipmentModel.getDescription());
            updateTableParameter(sEquipmentModel.getObservableEntityList());
            //tableViewEquipment.setItems(sEquipmentModel.getObservableEqInventoryList());
            updateEquipmentTable(sEquipmentModel.getObservableEqInventoryList());
            LisenersService.get().updateUI(TabPaneSecondLvlTabController.class);
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

}
