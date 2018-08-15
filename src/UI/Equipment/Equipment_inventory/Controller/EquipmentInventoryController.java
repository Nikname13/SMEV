package UI.Equipment.Equipment_inventory.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.State.StateModel;
import Presenter.EquipmentPresenter;
import Service.IUpdateUI;
import Service.LisenersService;
import UI.Coordinator;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EquipmentInventoryController implements IUpdateUI{

    private EquipmentInventoryModel mEquipmentInventory;
    private EquipmentModel mEquipmentModel;
    private DepartmentModel mDepartmentModel;
    private BaseValidator mBaseValidator = new BaseValidator();

    public EquipmentInventoryController(){
        LisenersService.get().addListenerUI(this);
    }

    @FXML
    private JFXTextField mTextFieldGuaranty;

    @FXML
    private JFXComboBox<InventoryNumberModel>  mComboBoxNumber;

    @FXML
    private JFXComboBox<StateModel> mComboBoxState;

    @FXML
    private JFXComboBox<DepartmentModel> mComboBoxDepartment;

    @FXML
    private JFXTextArea mTextAreaDescription, mTextAreaDescriptionDepartment;

    @FXML
    private JFXButton mButtonSave;

    @FXML
    private AnchorPane anchorPaneEquipmentInventory;

    @FXML
    public void initialize(){
        System.out.println("equipment inventory initialize");
        mButtonSave.setFocusTraversable(false);
        initComboBoxDepartment();
        initComboBoxNumber();
        initComboBoxState();
        initTextField();
        initTextArea();
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

    private void initComboBoxState() {
        mComboBoxState.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(StateModel state, boolean empty) {
                super.updateItem(state, empty);
                if (state != null && !empty) {
                    setText(state.getName());
                }else {
                    setText(null);
                }
            }
        });
        mComboBoxState.setConverter(new StringConverter<StateModel>() {
            @Override
            public String toString(StateModel object) {
                //System.out.println(object.getName());
                if(object!=null) return object.getName();
                else return null;
            }

            @Override
            public StateModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new StateModel(-1, string);
                else return null;
            }
        });
        mComboBoxState.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(StateModel item, boolean empty) {
                /*super.updateItem(item, empty);*/
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }

            }
        });
        mComboBoxState.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedState());
    }

    private void initComboBoxNumber() {
        mComboBoxNumber.setCellFactory(p->new ListCell<>(){
            @Override
            protected void updateItem(InventoryNumberModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getName());
                }else {
                    setText(null);
                }
            }
        });
        mComboBoxNumber.setConverter(new StringConverter<InventoryNumberModel>() {
            @Override
            public String toString(InventoryNumberModel object) {
                if(object!=null) return object.getName();
                else return null;
            }

            @Override
            public InventoryNumberModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new InventoryNumberModel(-1, string);
                else return null;
            }
        });
        mComboBoxNumber.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(InventoryNumberModel item, boolean empty) {
                /*super.updateItem(item, empty);*/
                if(item!=null && !empty){
                    setText(item.getName());
                }else {
                    setText(null);
                }

            }
        });
        mComboBoxNumber.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedNumber()));
    }

    private void selectedNumber() {
        if (mComboBoxNumber.getSelectionModel().getSelectedIndex() != -1 && mComboBoxNumber.focusedProperty().get()) {
            System.out.println("переход в модальное окно");
            EquipmentPresenter.get().setInventoryNumberModel(mComboBoxNumber.getValue());
            new Coordinator().goToInventoryNumberLog((Stage) anchorPaneEquipmentInventory.getScene().getWindow());

        }
    }

    private void initComboBoxDepartment() {
        mComboBoxDepartment.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                }else {
                    setText(null);
                }
            }
        });
        mComboBoxDepartment.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                /*super.updateItem(item, empty);*/
                if(item!=null && !empty){
                    setText(item.getName());
                }else {
                    setText(null);
                }
            }
        });
        mComboBoxDepartment.setConverter(new StringConverter<DepartmentModel>() {
            @Override
            public String toString(DepartmentModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public DepartmentModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new DepartmentModel(-1, string);
                else return null;
            }
        });
        mComboBoxDepartment.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment()));
    }

    @FXML
    private void onClickSave(){
        mButtonSave.setVisible(false);
        if (mTextFieldGuaranty.validate()) {
            EquipmentPresenter.get().editEquipmentInventory(
                    mEquipmentInventory.getInventoryNumber(),
                    Integer.parseInt(mTextFieldGuaranty.getText()),
                    mTextAreaDescription.getText(),
                    getDepartment(),
                    null,
                    mEquipmentModel,
                    mComboBoxState.getValue());
        }
    }

    private DepartmentModel getDepartment() {
        return mDepartmentModel == null ? mEquipmentInventory.getDepartmentModel() : mDepartmentModel;
    }

    private StateModel getState() {
        return EquipmentPresenter.get().getEquipmentStateLog() == null ? mEquipmentInventory.getStateModel() : EquipmentPresenter.get().getStateModel();
    }

    private InventoryNumberModel getInventoryNumber() {
        return EquipmentPresenter.get().getEquipmentInventoryLogModel() == null ? mEquipmentInventory.getInventoryNumber() : EquipmentPresenter.get().getInventoryNumberModel();
    }

    private void selectedDepartment() {
        if (mComboBoxDepartment.focusedProperty().get()) {
            EquipmentPresenter.get().setEquipmentInventoryModel(mEquipmentInventory);
            EquipmentPresenter.get().setDepartmentModel(mComboBoxDepartment.getValue());
            new Coordinator().goToMoveEquipmentInventoryWindow((Stage) anchorPaneEquipmentInventory.getScene().getWindow());
        }
    }

    private void selectedState() {
        if (mComboBoxState.getSelectionModel().getSelectedIndex() != -1 && mComboBoxState.focusedProperty().get()) {
            EquipmentPresenter.get().setEquipmentStateLog(null);
            EquipmentPresenter.get().setStateModel(mComboBoxState.getValue());
            new Coordinator().goToEquipmentStateLog((Stage) anchorPaneEquipmentInventory.getScene().getWindow());
        }
    }

    @FXML
    private void onClickDelete(){
        EquipmentPresenter.get().deleteEquipmentInventory(mEquipmentInventory);
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if(updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            EquipmentPresenter.get().setEquipmentStateLog(null);
            mEquipmentInventory = EquipmentPresenter.get().getEquipmentInventoryModel();
            mEquipmentModel = EquipmentPresenter.get().getEquipmentModel();
            mComboBoxDepartment.setItems(EquipmentPresenter.get().getObservableDepartment());
            mComboBoxDepartment.getSelectionModel().select(mEquipmentInventory.getDepartmentModel());
            mComboBoxNumber.setItems(EquipmentPresenter.get().getObservableInventory());
            mComboBoxNumber.getSelectionModel().select(mEquipmentInventory.getInventoryNumber());
            mTextFieldGuaranty.setText(String.valueOf(mEquipmentInventory.getGuaranty()));
            mTextAreaDescription.setText(mEquipmentInventory.getDescription());
            mComboBoxState.setItems(EquipmentPresenter.get().getObservableState());
            mComboBoxState.getSelectionModel().select(mEquipmentInventory.getStateModel());
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentInventoryModel.class.getName())) {
            mComboBoxState.getSelectionModel().select(getState());
            mComboBoxDepartment.getSelectionModel().select(getDepartment());
            mComboBoxNumber.getSelectionModel().select(getInventoryNumber());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }
}
