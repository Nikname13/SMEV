package UI.Supply.Controller;

import Model.Inventory_number.InventoryNumberModel;
import Model.Provider.ProviderModel;
import Model.Supply.SupplyModel;
import Presenter.InventoryNumberPresenter;
import Presenter.SupplyPresenter;
import Service.IOnMouseClick;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class EditSupplyController extends BaseController implements IUpdateUI, IOnMouseClick {

    private SupplyModel mSupplyModel;
    private String mTypeSupply;
    private static String sSupply = "поставки";
    private static String sAuction = "аукциона";
    private BaseValidator mBaseValidator = new BaseValidator();
    @FXML
    private JFXRadioButton mSupplyButton, mAuctionButton;
    @FXML
    private JFXDatePicker mDatePicker;
    @FXML
    private JFXComboBox<ProviderModel> mComboBoxProvider;
    @FXML
    private JFXTextField mNumberTextField;
    @FXML
    private JFXTextArea mTextAreaDescription;
    @FXML
    private JFXButton mButtonEdit;
    @FXML
    private TreeTableView<InventoryNumberModel> mTreeTableInventory;
    @FXML
    private TreeTableColumn<InventoryNumberModel, String> mNumberColumn, mDescriptionColumn;
    @FXML
    private AnchorPane mAnchorPaneEditSupply;

    public EditSupplyController(){
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize(){
        initComboBoxProvider(mComboBoxProvider, true, "Выберите поставщика", "Поставщик");
        initTextField();
        initTextArea();
        initRadioButton();
        initTreeTable();
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mTreeTableInventory, BasePopup.getInventoryNumberPopup(), this);
    }

    private void initTreeTable() {
        mNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().descriptionProperty());
        mTreeTableInventory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedNumber(newValue)));
    }

    private void selectedNumber(TreeItem<InventoryNumberModel> newValue) {
        if(newValue!=null){
            SupplyPresenter.get().setSelectedObject(newValue.getValue());
            InventoryNumberPresenter.get().setInventoryNumberModel(newValue.getValue());
        }
    }

    private void initTextArea() {
        mTextAreaDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextAreaDescription.focusedProperty().get()) {
                    setVisibleEditButton();
                }
            }
        });
    }

    private void initTextField() {
        mBaseValidator.setJFXTextFields(mNumberTextField);
        mNumberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mNumberTextField.focusedProperty().get()) {
                    setVisibleEditButton();
                }
            }
        });
    }

    private void initRadioButton() {
        ToggleGroup group=new ToggleGroup();
        mSupplyButton.setToggleGroup(group);
        mAuctionButton.setToggleGroup(group);
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> selectedButton(newValue));

    }

    private void setVisibleEditButton() {
        mTextAreaDescription.setPrefHeight(mTextAreaDescription.getMinHeight());
        mButtonEdit.setVisible(true);
    }

    private void setInvisibleEditButton() {
        mButtonEdit.setVisible(false);
        mTextAreaDescription.setPrefHeight(mTextAreaDescription.getMaxHeight());
    }

    @Override
    protected void initComboBoxProvider(JFXComboBox<ProviderModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        super.initComboBoxProvider(comboBox, isSelectionItem, promptText, label);
        comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> providerListener());
    }

    private void providerListener() {
        if (mComboBoxProvider.focusedProperty().get()) {
            setVisibleEditButton();
        }

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
        if (mSupplyButton.focusedProperty().get() || mAuctionButton.focusedProperty().get()) {
            setVisibleEditButton();
        }
        System.out.println(mTypeSupply);
    }

    private void setPromptText(String type) {
        mNumberTextField.setPromptText("Номер " + type);
        mDatePicker.setPromptText("Дата " + type);

    }



    @FXML
    private void onClickAdd() {
        if (mBaseValidator.validate()) {
            SupplyPresenter.get().editSupply(mNumberTextField.getText(), mTypeSupply, mDatePicker.getValue(), mTextAreaDescription.getText(), mComboBoxProvider.getValue());
            setInvisibleEditButton();
        }
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(SupplyModel.class.getName())) {
            mSupplyModel = SupplyPresenter.get().getSupplyModel();
            mNumberTextField.setText(mSupplyModel.getName());
            mDatePicker.setValue(mSupplyModel.getDate());
            mComboBoxProvider.setItems(SupplyPresenter.get().getObservableProvide());
            mComboBoxProvider.getSelectionModel().select(mSupplyModel.getProviderModel());
            mTextAreaDescription.setText(mSupplyModel.getDescription());
            setTypeSupply(mSupplyModel.getTypeSupply());
            setInvisibleEditButton();
            updateTreeTable(mSupplyModel.getEntityList());
        }
    }

    private void updateTreeTable(List<InventoryNumberModel> observableEntityList) {
        TreeItem<InventoryNumberModel> rootItem = new TreeItem<>();
        for (InventoryNumberModel number : observableEntityList) {
            rootItem.getChildren().add(new TreeItem<>(number));
        }
        mTreeTableInventory.setRoot(rootItem);
        mTreeTableInventory.setShowRoot(false);
    }

    private void setTypeSupply(String type) {
        switch (type) {
            case "Поставка":
                mSupplyButton.setSelected(true);
                break;
            case "Аукцион":
                mAuctionButton.setSelected(true);
                break;
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneEditSupply.getScene().getWindow();
    }

    @Override
    public void primaryClick(String id) {
        if (id.equals("inventoryLog")) {
            System.out.println("invenotory log");
            new Coordinator().goToInventoryNumberLog(getStage());
        }
    }
}
