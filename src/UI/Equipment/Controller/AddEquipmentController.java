package UI.Equipment.Controller;

import Model.Equipment.EquipmentParameterModel;
import Model.Parameter.ParameterModel;
import Model.Type.TypeModel;
import Presenter.EquipmentPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AddEquipmentController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();
    private ObservableList<EquipmentParameterModel> mParameterModel;
    private double mHeightStage;
    @FXML
    private JFXTextField mTextFieldName, mTextFieldNameFact;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private JFXComboBox<TypeModel> mComboBoxType;

    @FXML
    private TreeTableView<EquipmentParameterModel> mTreeTableViewParameter;

    @FXML
    private TreeTableColumn<EquipmentParameterModel, String> mColumnNameParameter, mColumnValueParameter;

    @FXML
    private ValidationFacade mFacadeType;

    @FXML
    private Label mErrorType;

    @FXML
    private AnchorPane mAnchorPaneAddEquipment;

    @FXML
    public void initialize() {
       /* mHeightStage=getStage().getHeight();
        System.out.println("height stage "+mHeightStage);*/
        mBaseValidator.setJFXTextFields(mTextFieldName, mTextFieldNameFact);
        mBaseValidator.setValidationFacades(new Pair(mFacadeType, mErrorType));
        initComboBoxType(mComboBoxType, false);
        initTableVieParameter();
        updateTableParameter(mParameterModel);
    }

    private Stage getStage() {
        return (Stage) mAnchorPaneAddEquipment.getScene().getWindow();
    }

    private void initTableVieParameter() {
        mColumnNameParameter.setCellValueFactory(cellData -> cellData.getValue().getValue().getParameterModel().nameProperty());
/*        mColumnNameParameter.setCellFactory((TreeTableColumn<EquipmentParameterModel, String> param) -> new EditableTreeTableCell<>(
                new ComboBoxEditorBuilder(initComboBoxParameter(new JFXComboBox(EquipmentPresenter.get().getObservableParameter()), false))));*/
        mColumnValueParameter.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mColumnValueParameter.setCellFactory((TreeTableColumn<EquipmentParameterModel, String> param) -> new GenericEditableTreeTableCell<>());
        mColumnValueParameter.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<EquipmentParameterModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<EquipmentParameterModel, String> event) {
/*                TreeItem<EquipmentParameterModel> currentEditEquipment = mTreeTableViewParameter.getTreeItem(event.getTreeTablePosition().getRow());
                currentEditEquipment.getValue().setName(event.getNewValue());
                for (EquipmentParameterModel parameter : mParameterModel) {
                    System.out.println(parameter.getName());
                }*/
            }
        });

    }

    @Override
    protected void initComboBoxType(JFXComboBox<TypeModel> comboBoxType, boolean isSelectionItem) {
        super.initComboBoxType(comboBoxType, isSelectionItem);
        mComboBoxType.setItems(EquipmentPresenter.get().getObservableType());
        mComboBoxType.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> selectedType());

    }

    private void selectedType() {
        mParameterModel = FXCollections.observableArrayList();
        for (ParameterModel parameter : mComboBoxType.getValue().getEntityList()) {
            mParameterModel.add(new EquipmentParameterModel(0, "", parameter));
        }
        updateTableParameter(mParameterModel);
        if (mParameterModel.size() >= 1)
            resizeHeightStage(mTreeTableViewParameter.getPrefHeight() - 100);
        else {
            resizeHeightStage(0);
        }
    }

    private void updateTableParameter(ObservableList<EquipmentParameterModel> equipmentParameters) {
        TreeItem<EquipmentParameterModel> rootItem = new TreeItem<>();
        String newParameter = "";
        if (equipmentParameters != null) {
            for (EquipmentParameterModel log : equipmentParameters) {
                rootItem.getChildren().add(new TreeItem<>(log));
            }
            mTreeTableViewParameter.setEditable(true);
            newParameter = "Добавить параметр";
        }
        rootItem.getChildren().add(new TreeItem<>(new EquipmentParameterModel(0, "", new ParameterModel(0, newParameter))));
        mTreeTableViewParameter.setRoot(rootItem);
        mTreeTableViewParameter.setShowRoot(false);
        mTreeTableViewParameter.setVisible(true);
        mTreeTableViewParameter.setFixedCellSize(50);
        mTreeTableViewParameter.prefHeightProperty().bind(Bindings.size(mTreeTableViewParameter.getRoot().getChildren())
                .multiply(mTreeTableViewParameter.getFixedCellSize()).add(55));

        System.out.println(mTreeTableViewParameter.getRoot().getChildren().size() + " " + mTreeTableViewParameter.getPrefHeight());
    }

    private void resizeHeightStage(double height) {
        getStage().setHeight(getStage().getMinHeight() + height);
        System.out.println(getStage().heightProperty().toString());
    }

    @FXML
    private void onClickAdd() {
        if (mBaseValidator.validate()) {
            EquipmentPresenter.get().addEquipment(
                    mTextFieldName.getText(),
                    mTextFieldNameFact.getText(),
                    mTextAreaDescription.getText(),
                    mComboBoxType.getValue(),
                    mParameterModel
            );
        }
    }

    @FXML
    public void onClickCancel() {

    }
}
