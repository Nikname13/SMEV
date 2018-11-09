package UI.Equipment.Controller;

import Model.Equipment.EquipmentParameterModel;
import Model.Parameter.ParameterModel;
import Model.Type.TypeModel;
import Presenter.EquipmentPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


public class AddEquipmentController extends BaseController {

    private BaseValidator mBaseValidator = new BaseValidator();
    private BaseValidator mBaseValidatorDialog = new BaseValidator();
    private ObservableList<EquipmentParameterModel> mEquipmentParameterList;
    private JFXDialog mDialog;

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
    private StackPane mStackPaneAddEquipment;

    @FXML
    public void initialize() {
        mBaseValidator.setJFXTextFields(mTextFieldName, mTextFieldNameFact);
        mBaseValidator.setValidationFacades(new Pair(mFacadeType, mErrorType, mComboBoxType));
        initComboBoxType(mComboBoxType, false, "Выберите тип", "Тип");
        initTableVieParameter();
    }

    private void initTableVieParameter() {
        mColumnNameParameter.setCellValueFactory(cellData -> cellData.getValue().getValue().getParameterModel().nameProperty());
        mColumnValueParameter.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mColumnValueParameter.setCellFactory((TreeTableColumn<EquipmentParameterModel, String> param) -> new GenericEditableTreeTableCell<>());
        mColumnValueParameter.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<EquipmentParameterModel, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<EquipmentParameterModel, String> event) {
                TreeItem<EquipmentParameterModel> currentEditEquipment = mTreeTableViewParameter.getTreeItem(event.getTreeTablePosition().getRow());
                currentEditEquipment.getValue().setName(event.getNewValue());
            }
        });
        mTreeTableViewParameter.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipmentParameter(newValue)));
        mTreeTableViewParameter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println("secondary delete item");
                    EquipmentParameterModel equipmentParameter = mTreeTableViewParameter.getSelectionModel().getSelectedItem().getValue();
                    if (equipmentParameter.getId() != -1) {
                        mEquipmentParameterList.remove(equipmentParameter);
                        updateTableParameter(mEquipmentParameterList);
                        resizeHeightStage();
                    }
                }
            }
        });
        mTreeTableViewParameter.setTooltip(new Tooltip("ПКМ для удаления"));
    }


    private void selectedEquipmentParameter(TreeItem<EquipmentParameterModel> newValue) {
        if (newValue != null && mEquipmentParameterList != null) {
            if (newValue.getValue().getId() == -1) {
                showDialog();
            }
        }
    }

    private void showDialog() {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Добавить параметр"));

        JFXComboBox<ParameterModel> comboBox = initComboBoxParameter(
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

        mDialog = new JFXDialog(mStackPaneAddEquipment, content, JFXDialog.DialogTransition.CENTER);
        mDialog.show();
        mDialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
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
                    updateTableParameter(mEquipmentParameterList);
                    if (mTreeTableViewParameter.getPrefHeight() <= mTreeTableViewParameter.getMaxHeight())
                        resizeHeightStage();
                    mDialog.close();
                }
            }
        });
    }


    @Override
    protected Stage getStage() {
        return (Stage) mStackPaneAddEquipment.getScene().getWindow();
    }

    @Override
    protected void initComboBoxType(JFXComboBox<TypeModel> comboBoxType, boolean isSelectionItem, String promptText, String label) {
        super.initComboBoxType(comboBoxType, isSelectionItem, promptText, label);
        mComboBoxType.setItems(EquipmentPresenter.get().getObservableType());
        mComboBoxType.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> selectedType());
    }

    private void selectedType() {
        mEquipmentParameterList = FXCollections.observableArrayList();
        for (ParameterModel parameter : mComboBoxType.getValue().getEntityList()) {
            mEquipmentParameterList.add(new EquipmentParameterModel(0, "", parameter));
        }
        updateTableParameter(mEquipmentParameterList);
        resizeHeightStage();
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
        rootItem.getChildren().add(new TreeItem<>(new EquipmentParameterModel(-1, "", new ParameterModel(0, newParameter))));
        mTreeTableViewParameter.setRoot(rootItem);
        mTreeTableViewParameter.setShowRoot(false);
        mTreeTableViewParameter.setFixedCellSize(50);
        mTreeTableViewParameter.prefHeightProperty().bind(Bindings.size(mTreeTableViewParameter.getRoot().getChildren())
                .multiply(mTreeTableViewParameter.getFixedCellSize()).add(55));

    }

    private void resizeHeightStage() {
        if (mEquipmentParameterList.size() >= 1) {
            getStage().setHeight(getStage().getMinHeight() + (mTreeTableViewParameter.getPrefHeight() - mTreeTableViewParameter.getMinHeight()));
        } else {
            getStage().setHeight(getStage().getMinHeight());
        }
    }

    @FXML
    private void onClickAdd() {
        if (mBaseValidator.validate()) {
            EquipmentPresenter.get().addEquipment(
                    mTextFieldName.getText(),
                    mTextFieldNameFact.getText(),
                    mTextAreaDescription.getText(),
                    mComboBoxType.getValue(),
                    mEquipmentParameterList
            );
            close(mStackPaneAddEquipment);
        }
    }

    @FXML
    public void onClickCancel() {
        close(mStackPaneAddEquipment);
    }
}
