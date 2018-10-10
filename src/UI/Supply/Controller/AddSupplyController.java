package UI.Supply.Controller;

import Model.Inventory_number.InventoryNumberModel;
import Model.Provider.ProviderModel;
import Presenter.SupplyPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;


public class AddSupplyController extends BaseController {

    private String mTypeSupply;
    private BaseValidator mBaseValidator = new BaseValidator();
    private BaseValidator mBaseValidatorDialog = new BaseValidator();
    private ObservableList<InventoryNumberModel> mInventoryNumberList;
    private JFXDialog mDialog;
    private static String sSupply = "поставки";
    private static String sAuction = "аукциона";


    @FXML
    private JFXRadioButton mSupplyButton, mAuctionButton;
    @FXML
    private JFXComboBox<ProviderModel> mComboBoxProvider;
    @FXML
    private JFXTextField mNumberTextField;
    @FXML
    private ValidationFacade mFacadeDate, mFacadeProvider;
    @FXML
    private Label mErrorDate, mErrorProvider;
    @FXML
    private JFXTextArea mTextAreaDescription;
    @FXML
    private TreeTableView<InventoryNumberModel> mTreeTableNumber;
    @FXML
    private TreeTableColumn<InventoryNumberModel, String> mNumberColumn;
    @FXML
    private JFXDatePicker mDatePicker;
    @FXML
    private StackPane mStackPaneAddSupply;


    @FXML
    public void initialize(){
        mInventoryNumberList = FXCollections.observableArrayList();
        mBaseValidator.setJFXTextFields(mNumberTextField);
        mBaseValidator.setValidationFacades(new Pair(mFacadeProvider, mErrorProvider));
        initRadioButton();
        initDatePicker();
        initComboBoxProvider(mComboBoxProvider, false);
        initTableView();
        updateTable(mInventoryNumberList);
    }

    private void initDatePicker() {
        mDatePicker.setValue(LocalDate.now());
    }

    private void updateTable(ObservableList<InventoryNumberModel> inventoryNumberList) {
        TreeItem<InventoryNumberModel> rootItem = new TreeItem<>();
        if (inventoryNumberList != null) {
            for (InventoryNumberModel number : inventoryNumberList) {
                rootItem.getChildren().add(new TreeItem<>(number));
            }
        }
        rootItem.getChildren().add(new TreeItem<>(new InventoryNumberModel(-1, "Добавить номер")));
        mTreeTableNumber.setRoot(rootItem);
        mTreeTableNumber.setShowRoot(false);
        mTreeTableNumber.setFixedCellSize(50.0);
        mTreeTableNumber.prefHeightProperty().bind(Bindings.size(mTreeTableNumber.getRoot().getChildren()).multiply(mTreeTableNumber.getFixedCellSize()).add(55));

    }

    private void selectedItem(TreeItem<InventoryNumberModel> newValue) {
        if (newValue != null) {
            if (newValue.getValue().getId() == -1) {
                showDialog();
            }
        }
    }

    private void showDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Новый номер"));
        JFXButton button = new JFXButton("Сохранить");
        button.setRipplerFill(Paint.valueOf("#40a85f"));
        button.setPrefHeight(35.0);

        JFXTextField text = new JFXTextField();
        text.setLabelFloat(true);
        text.setPromptText("Инвентарный номер");
        text.setFocusColor(Paint.valueOf("#40a85f"));

        mBaseValidatorDialog.setJFXTextFields(text);

        JFXCheckBox groupBox = new JFXCheckBox("В составе");

        HBox hBox = new HBox();
        hBox.getChildren().add(text);
        hBox.getChildren().add(groupBox);
        hBox.setSpacing(10);

        JFXTextArea areaDescription = new JFXTextArea();
        areaDescription.setLabelFloat(true);
        areaDescription.setPrefHeight(30);
        areaDescription.setPromptText("Комментарий");
        areaDescription.setFocusColor(Paint.valueOf("#40a85f"));

        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(areaDescription);
        vBox.setSpacing(40);
        content.setBody(vBox);
        content.setActions(button);
        mDialog = new JFXDialog(mStackPaneAddSupply, content, JFXDialog.DialogTransition.CENTER);
        mDialog.show();
        mDialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                mTreeTableNumber.getSelectionModel().clearSelection();
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mBaseValidatorDialog.validate()) {
                    mInventoryNumberList.add(new InventoryNumberModel(
                            0,
                            text.getText(),
                            groupBox.isSelected(),
                            areaDescription.getText().trim()));
                    updateTable(mInventoryNumberList);
                    if (mTreeTableNumber.getPrefHeight() <= mTreeTableNumber.getMaxHeight())
                        resizeHeightStage();
                    mDialog.close();
                }
            }
        });
    }

    private void resizeHeightStage() {
        if (mInventoryNumberList.size() >= 1) {
            getStage().setHeight(getStage().getMinHeight() + (mTreeTableNumber.getPrefHeight() - mTreeTableNumber.getMinHeight()));
        } else {
            getStage().setHeight(getStage().getMinHeight());
        }
    }

    private void initTableView() {
        mNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mTreeTableNumber.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedItem(newValue)));
    }

    @Override
    protected void initComboBoxProvider(JFXComboBox<ProviderModel> comboBox, boolean isSelectionItem) {
        super.initComboBoxProvider(comboBox, isSelectionItem);
        comboBox.setItems(SupplyPresenter.get().getObservableProvide());
        //comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> providerListener(newValue));
    }

    private void initRadioButton() {
        ToggleGroup group=new ToggleGroup();
        mSupplyButton.setToggleGroup(group);
        mAuctionButton.setToggleGroup(group);
        mSupplyButton.setSelected(true);
        selectedButton(mSupplyButton);
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> selectedButton(newValue));
    }

    private void selectedButton(Object o) {
        mTypeSupply=((RadioButton)o).getText();
        switch (((RadioButton) o).getId()) {
            case "mSupplyButton":
                setPromptText(sSupply);
                break;
            case "mAuctionButton":
                setPromptText(sAuction);
                break;
        }
        System.out.println(mTypeSupply);
    }

    private void setPromptText(String type) {
        mNumberTextField.setPromptText("Введите номер " + type);
        mDatePicker.setPromptText("Дата " + type);
    }

    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            SupplyPresenter.get().addSupply(mNumberTextField.getText(), mTypeSupply, mDatePicker.getValue(),
                    mInventoryNumberList, mTextAreaDescription.getText(), mComboBoxProvider.getValue());
        }
    }

    @FXML
    private void onClickCancel() {

    }

    @Override
    protected Stage getStage() {
        return (Stage) mStackPaneAddSupply.getScene().getWindow();
    }
}
