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
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.validation.ValidationFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;


public class AddEquipmentController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();
    private ObservableList<EquipmentParameterModel> mParameterModel;
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
    public void initialize(){
        mBaseValidator.setJFXTextFields(mTextFieldName, mTextFieldNameFact);
        mBaseValidator.setValidationFacades(new Pair(mFacadeType, mErrorType));
        initComboBoxType(mComboBoxType, false);
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
                for (EquipmentParameterModel parameter : mParameterModel) {
                    System.out.println(parameter.getName());
                }
            }
        });
        mTreeTableViewParameter.setEditable(true);
    }

    @Override
    protected void initComboBoxType(JFXComboBox<TypeModel> comboBoxType, boolean isSelectionItem) {
        super.initComboBoxType(comboBoxType, isSelectionItem);
        mComboBoxType.setItems(EquipmentPresenter.get().getObservableType());
        mComboBoxType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedType());
    }

    private void selectedType() {
        mParameterModel = FXCollections.observableArrayList();
        for (ParameterModel parameter : mComboBoxType.getValue().getEntityList()) {
            mParameterModel.add(new EquipmentParameterModel(0, "", parameter));
        }
        updateTableParameter(mParameterModel);
    }

    private void updateTableParameter(ObservableList<EquipmentParameterModel> equipmentParameters) {
        TreeItem<EquipmentParameterModel> rootItem = new TreeItem<>();
        for (EquipmentParameterModel log : equipmentParameters) {
            rootItem.getChildren().add(new TreeItem<>(log));
        }
        mTreeTableViewParameter.setRoot(rootItem);
        mTreeTableViewParameter.setShowRoot(false);
    }

    @FXML
    private void onClickAdd(){
        EquipmentPresenter.get().addEquipment(
                mTextFieldName.getText(),
                mTextFieldNameFact.getText(),
                mTextAreaDescription.getText(),
                mComboBoxType.getValue(),
                mParameterModel
        );
    }

    @FXML
    public void onClickCancel() {

    }
}
