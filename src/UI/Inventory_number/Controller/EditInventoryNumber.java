package UI.Inventory_number.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.State.StateModel;
import Model.Supply.SupplyModel;
import Presenter.EquipmentInventoryPresenter;
import Presenter.InventoryNumberPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class EditInventoryNumber extends BaseController implements IOnMouseClick {

    private BaseValidator mBaseValidator=new BaseValidator();
    private String mOldNumber;
    private JFXDialog mJFXDialog;
    private InventoryNumberModel mInventoryNumberModel;

    @FXML
    private JFXTextField mTextFieldNumber;
    @FXML
    private JFXCheckBox mCheckBoxGroup;
    @FXML
    private JFXComboBox<SupplyModel> mComboBoxSupply;
    @FXML
    private JFXTextArea mTextAreaDescription;
    @FXML
    private TreeTableView<EquipmentInventoryModel> mTreeTableEquipmentInventory;
    @FXML
    private TreeTableColumn<EquipmentInventoryModel,String> mNameEquipmentColumn,mDepartmentEquipmentColumn,mStateEquipmentColumn,mDescriptionEquipmentColumn;
    @FXML
    private JFXButton mButtonUpdate;
    @FXML
    private AnchorPane mEditInventoryNumberPane;

    public EditInventoryNumber() {
        ListenersService.get().addListenerUI(this);
    }

    public void initialize(){
        initCheckBox();
        initTextFieldNumber();
        initTreeTable();
        initPromptText(mTextAreaDescription, "Добавить комментарий", "Комментарий");
        initTextArea();
        initPopup();
        initComboBox();
    }

    private void initCheckBox() {
        mCheckBoxGroup.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                setVisibleEditButton();
            }
        });
    }

    private void initComboBox() {
        initJFXComboBox(new SupplyModel(), mComboBoxSupply, true, "Выберите поставку", "Номер поставки");
        mComboBoxSupply.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedSupply()));

    }

    private void initPopup() {
        new BasePopup(mTreeTableEquipmentInventory, BasePopup.getEquipmentInventoryReadPopup(), this);
    }

    private void initTextArea() {
        mTextAreaDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(mTextAreaDescription.focusedProperty().get())
                setVisibleEditButton();
            }
        });
    }

    private void initTreeTable() {
        mNameEquipmentColumn.setCellValueFactory(cellData->cellData.getValue().getValue().getEquipmentModel().nameFactProperty());
        mDepartmentEquipmentColumn.setCellValueFactory(cellData->cellData.getValue().getValue().getDepartmentModel().nameProperty());
        mStateEquipmentColumn.setCellValueFactory(cellData->cellData.getValue().getValue().getStateModel().nameProperty());
        mDescriptionEquipmentColumn.setCellValueFactory(cellData->cellData.getValue().getValue().descriptionProperty());
        mTreeTableEquipmentInventory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
    }

    private void selectedEquipment(TreeItem<EquipmentInventoryModel> newValue) {
        if (newValue != null) {
            if (newValue.getValue().getId() != -1) {
                EquipmentInventoryPresenter.get().setEquipmentInventoryModel(newValue.getValue());
            } else {
                EquipmentInventoryPresenter.get().setEquipmentInventoryModel(null);
            }
        }
    }

    private void selectedSupply() {
        if(mComboBoxSupply.focusedProperty().get()){
            setVisibleEditButton();
        }
    }

    private void setVisibleEditButton() {
        mTextAreaDescription.setPrefHeight(mTextAreaDescription.getMinHeight());
        mButtonUpdate.setVisible(true);
    }

    private void setInvisibleEditButton(){

        mButtonUpdate.setVisible(false);
        mTextAreaDescription.setPrefHeight(mTextAreaDescription.getMaxHeight());
    }

    private void initTextFieldNumber() {
        initPromptText(mTextFieldNumber, "Введите инвентарный номер", "Инвентарный номер");
        mTextFieldNumber.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    showDialog();
                }
            }
        });
    }

    private void showDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Смена инвентарного номера"));
        JFXButton button = new JFXButton("Сохранить");
        button.setRipplerFill(Paint.valueOf("#40a85f"));
        button.setPrefHeight(35.0);

        JFXTextField text = new JFXTextField();
        text.setLabelFloat(true);
        text.setPromptText("Введите инвентарный номер");
        text.setFocusColor(Paint.valueOf("#40a85f"));
        initPromptText(text, "Введите инвентарный номер","Инвентарный номер");
        text.setPrefWidth(200);
        mBaseValidator.setJFXTextFields(text);

        JFXTextArea textArea=new JFXTextArea();
        textArea.setLabelFloat(true);
        textArea.setPromptText("Введите обоснование");
        textArea.setFocusColor(Paint.valueOf("#40a85f"));
        textArea.setPrefHeight(150);
        initPromptText(textArea, "Введите обоснование","Обоснование");
        text.setPrefWidth(200);
        mBaseValidator.setJFXTextAreas(textArea);

        VBox vBox=new VBox();
        vBox.getChildren().add(text);
        vBox.getChildren().add(textArea);
        vBox.setSpacing(50);

        content.setBody(vBox);
        content.setActions(button);
        mJFXDialog=new JFXDialog(getParentStackPane(),content,JFXDialog.DialogTransition.CENTER);
        mJFXDialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                mTextFieldNumber.getParent().requestFocus();
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mBaseValidator.validate()) {
                    InventoryNumberPresenter.get().setInventoryNumberModel(mInventoryNumberModel);
                    InventoryNumberPresenter.get().addInventoryNumberLog(text.getText(), textArea.getText());
                    mJFXDialog.close();
                }
            }
        });
        mJFXDialog.show();
    }

    private EquipmentInventoryModel getEmptyRootEquipment(EquipmentInventoryModel equipment) {
        return new EquipmentInventoryModel(
                -1,
                new InventoryNumberModel(-1, ""),
                new EquipmentModel(equipment.getEquipmentModel().getId(), equipment.getEquipmentModel().getName()),
                new DepartmentModel(-1,""),
                new StateModel(-1, ""));
    }

    private void updateTable(List<EquipmentInventoryModel> equipmentList) {
        TreeItem<EquipmentInventoryModel> rootItem=new TreeItem<>();
        boolean flag=false;
        for(EquipmentInventoryModel equipment:equipmentList){
            for(TreeItem<EquipmentInventoryModel> rootEquipment:rootItem.getChildren()){
                if(rootEquipment.getValue().getEquipmentModel().getId()==equipment.getEquipmentModel().getId()){
                    flag=true;
                    rootEquipment.getChildren().add(new TreeItem<>(equipment));
                    break;
                }
            }
            if(!flag){
                TreeItem<EquipmentInventoryModel> treeItem=new TreeItem<>(getEmptyRootEquipment(equipment));
                treeItem.getChildren().add(new TreeItem<>(equipment));
                rootItem.getChildren().add(treeItem);
            }
            flag=false;
        }
        mTreeTableEquipmentInventory.setRoot(rootItem);
        mTreeTableEquipmentInventory.setShowRoot(false);
    }

    @FXML
    private void onClickAdd(){
     InventoryNumberPresenter.get().editInventoryNumber(mComboBoxSupply.getValue(), mTextAreaDescription.getText(), mCheckBoxGroup.isSelected());
     setInvisibleEditButton();
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if(updateClass.getName().equals(InventoryNumberModel.class.getName())){
            mInventoryNumberModel=InventoryNumberPresenter.get().getInventoryNumberModel();
            mTextFieldNumber.setText(mInventoryNumberModel.getName());
            mCheckBoxGroup.setSelected(mInventoryNumberModel.isGroup());
            mComboBoxSupply.setItems(InventoryNumberPresenter.get().getObservableSupply());
            mComboBoxSupply.getSelectionModel().select(mInventoryNumberModel.getSupply());
            mTextAreaDescription.setText(mInventoryNumberModel.getDescription());
            setInvisibleEditButton();
            updateTable(mInventoryNumberModel.getEquipmentInventoryList());
        }
    }


    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(InventoryNumberModel.class.getName())) {
            updateUI(InventoryNumberModel.class);
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }

    @Override
    protected Stage getStage() {
        return (Stage)mEditInventoryNumberPane.getScene().getWindow();
    }

    @Override
    public void primaryClick(String id) {
        switch (id) {
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
