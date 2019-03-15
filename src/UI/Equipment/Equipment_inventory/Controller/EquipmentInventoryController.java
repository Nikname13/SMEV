package UI.Equipment.Equipment_inventory.Controller;

import Model.AbstractModel;
import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.State.StateModel;
import Presenter.EquipmentInventoryPresenter;
import Presenter.EquipmentPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EquipmentInventoryController extends BaseController implements IOnMouseClick {

    private EquipmentInventoryModel mEquipmentInventory;
    private EquipmentModel mEquipmentModel;
    private DepartmentModel mDepartmentModel;
    private BaseValidator mBaseValidator = new BaseValidator();

    public EquipmentInventoryController(){
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private JFXTextField mTextFieldGuaranty;

    @FXML
    private JFXComboBox<InventoryNumberModel> mComboBoxInventoryNumber;

    @FXML
    private JFXComboBox<StateModel> mComboBoxState;

    @FXML
    private JFXComboBox<DepartmentModel> mComboBoxDepartment;

    @FXML
    private JFXTextArea mTextAreaDescription, mTextAreaDescriptionDepartment;

    @FXML
    private JFXButton mButtonSave;

    @FXML
    private BorderPane mAvatarImage;

    @FXML
    private AnchorPane mPaneEquipmentInventory;

    @FXML
    public void initialize(){
        System.out.println("equipment inventory initialize");
        mButtonSave.setFocusTraversable(false);
        initComboBox();
        initTextField();
        initTextArea();
        initPopup();
    }

    private void initComboBox() {
        initJFXComboBox(new DepartmentModel(), mComboBoxDepartment, true, "Выберите отдел", "Отдел");
        initJFXComboBox(new InventoryNumberModel(), mComboBoxInventoryNumber, true, "Выберите номер", "Номер");
        initJFXComboBox(new StateModel(), mComboBoxState, true, "Выберите состояние", "Состояние");
        mComboBoxState.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedState());
        mComboBoxInventoryNumber.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedNumber()));
        mComboBoxDepartment.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment()));


    }

    private void initPopup() {
        new BasePopup(mAvatarImage, BasePopup.getAvatarPopup(), this, true);
    }

    private void initTextArea() {
        mTextAreaDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextAreaDescription.focusedProperty().get()) mButtonSave.setVisible(true);
            }
        });
    }

    private void initTextField() {
        mBaseValidator.setJFXTextFields(mTextFieldGuaranty);
        mTextFieldGuaranty.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextFieldGuaranty.focusedProperty().get()) mButtonSave.setVisible(true);
            }
        });
    }

    private void selectedNumber() {
        if (mComboBoxInventoryNumber.getSelectionModel().getSelectedIndex() != -1 && mComboBoxInventoryNumber.focusedProperty().get()) {
            System.out.println("переход в модальное окно");
            EquipmentInventoryPresenter.get().setInventoryNumberModel(mComboBoxInventoryNumber.getValue());
            new Coordinator().goToAddInventoryNumberLog(getStage());

        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) mPaneEquipmentInventory.getScene().getWindow();
    }

    @FXML
    private void onClickSave(){
        mButtonSave.setVisible(false);
        if (mTextFieldGuaranty.validate()) {
            EquipmentInventoryPresenter.get().editEquipmentInventory(
                    mEquipmentInventory.getInventoryNumber(),
                    Integer.parseInt(mTextFieldGuaranty.getText()),
                    mTextAreaDescription.getText(),
                    getDepartment(),
                    mEquipmentModel,
                    mComboBoxState.getValue());
        }
    }

    @FXML
    private void onClickConfig() {
        EquipmentPresenter.get().setTypeDocuments(AbstractModel.getTypeConfig());
        EquipmentPresenter.get().setEquipmentModel(mEquipmentModel);
        new Coordinator().goToFilesEquipmentWindow(getStage());
    }

    @FXML
    private void onClickPhoto() {
        EquipmentPresenter.get().setEquipmentModel(mEquipmentModel);
        EquipmentPresenter.get().setTypeDocuments(AbstractModel.getTypePhoto());
       // new Coordinator().goToPhotoEquipmentWindow(getStage());
        new Coordinator().goToFilesEquipmentWindow(getStage());
    }

    private DepartmentModel getDepartment() {
        return mDepartmentModel == null ? mEquipmentInventory.getDepartmentModel() : mDepartmentModel;
    }

    private StateModel getState() {
        return EquipmentInventoryPresenter.get().getEquipmentStateLog() == null ? mEquipmentInventory.getStateModel() : EquipmentInventoryPresenter.get().getStateModel();
    }

    private InventoryNumberModel getInventoryNumber() {
        return EquipmentInventoryPresenter.get().getEquipmentInventoryLogModel() == null ? mEquipmentInventory.getInventoryNumber() : EquipmentInventoryPresenter.get().getInventoryNumberModel();
    }

    private void selectedDepartment() {
        if (mComboBoxDepartment.focusedProperty().get()) {
            EquipmentInventoryPresenter.get().setEquipmentInventoryModel(mEquipmentInventory);
            EquipmentInventoryPresenter.get().setDepartmentModel(mComboBoxDepartment.getValue());
            new Coordinator().goToMoveEquipmentInventoryWindow((Stage) mPaneEquipmentInventory.getScene().getWindow());
        }
    }

    private void selectedState() {
        if (mComboBoxState.getSelectionModel().getSelectedIndex() != -1 && mComboBoxState.focusedProperty().get()) {
            EquipmentInventoryPresenter.get().setEquipmentStateLog(null);
            EquipmentInventoryPresenter.get().setStateModel(mComboBoxState.getValue());
            new Coordinator().goToAddEquipmentStateLog((Stage) mPaneEquipmentInventory.getScene().getWindow());
        }
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if(updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            EquipmentInventoryPresenter.get().setEquipmentStateLog(null);
            mEquipmentInventory = EquipmentInventoryPresenter.get().getEquipmentInventoryModel();
            mEquipmentModel = EquipmentPresenter.get().getEquipmentModel();
            mComboBoxDepartment.setItems(EquipmentPresenter.get().getObservableDepartment());
            mComboBoxDepartment.getSelectionModel().select(mEquipmentInventory.getDepartmentModel());
            mComboBoxInventoryNumber.setItems(EquipmentPresenter.get().getObservableInventory());
            mComboBoxInventoryNumber.getSelectionModel().select(mEquipmentInventory.getInventoryNumber());
            mTextFieldGuaranty.setText(String.valueOf(mEquipmentInventory.getGuaranty()));
            mTextAreaDescription.setText(mEquipmentInventory.getDescription());
            mComboBoxState.setItems(EquipmentPresenter.get().getObservableState());
            mComboBoxState.getSelectionModel().select(mEquipmentInventory.getStateModel());
            setAvatar(EquipmentInventoryPresenter.get().getPathAvatar(), mAvatarImage);
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            mComboBoxState.getSelectionModel().select(getState());
            mComboBoxDepartment.getSelectionModel().select(getDepartment());
            mComboBoxInventoryNumber.getSelectionModel().select(getInventoryNumber());
            setAvatar(EquipmentInventoryPresenter.get().getPathAvatar(), mAvatarImage);
        }
    }

    @Override
    public void primaryClick(String id) {
        switch (id) {

            case "editAvatar":
                EquipmentInventoryPresenter.get().setTypeDocuments(AbstractModel.getTypePhoto());
                EquipmentInventoryPresenter.get().uploadAvatar(getStage());
                break;
            case "deleteAvatar":
                System.out.println(" delete avatar");
                break;
            case "mAvatarImage":
                System.out.println("open avatar");
                break;
        }
    }
}
