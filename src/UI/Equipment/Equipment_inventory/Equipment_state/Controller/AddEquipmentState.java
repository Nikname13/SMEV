package UI.Equipment.Equipment_inventory.Equipment_state.Controller;

import Model.Equipment.EquipmentInventoryModel;
import Model.State.StateModel;
import Presenter.EquipmentPresenter;
import UI.Validator.ControllerValidator;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import java.time.LocalDate;

public class AddEquipmentState {
    private static EquipmentInventoryModel sEquipmentInventoryModel;

    public AddEquipmentState(){
        sEquipmentInventoryModel=EquipmentPresenter.get().getEquipmentInventoryModel();
    }

    @FXML
    private Label labelData;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private JFXComboBox<StateModel> mComboBoxState;


    @FXML
    public void initialize(){
        labelData.setText(String.valueOf(LocalDate.now()));
        mComboBoxState.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(StateModel state, boolean empty) {
                super.updateItem(state, empty);
                if (state != null && !empty) {
                    setText(state.getName());
                } else {
                    setText(null);
                }
            }
        });
        mComboBoxState.setConverter(new StringConverter<StateModel>() {
            @Override
            public String toString(StateModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public StateModel fromString(String string) {
                return new StateModel(-1, mComboBoxState.getEditor().getText());
            }
        });
        mComboBoxState.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(StateModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        mComboBoxState.setItems(EquipmentPresenter.get().getObservableState());
        mComboBoxState.getSelectionModel().select(EquipmentPresenter.get().getStateModel());
        ControllerValidator.setTextAreaValidator(mTextAreaDescription);
    }

    @FXML
    private void onClickAdd(){
        if (mTextAreaDescription.validate()) {
            EquipmentPresenter.get().addEquipmentState(mTextAreaDescription.getText(), LocalDate.now());
        }
    }

}
