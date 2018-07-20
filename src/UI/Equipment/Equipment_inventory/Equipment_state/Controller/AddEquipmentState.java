package UI.Equipment.Equipment_inventory.Equipment_state.Controller;

import Model.Equipment.EquipmentInventoryModel;
import Presenter.EquipmentPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.time.LocalDate;

public class AddEquipmentState {
    private static EquipmentInventoryModel sEquipmentInventoryModel;

    public AddEquipmentState(){
        sEquipmentInventoryModel=EquipmentPresenter.get().getEquipmentInventoryModel();
    }

    @FXML
    private Label labelData, labelState;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    public void initialize(){
        labelData.setText(String.valueOf(LocalDate.now()));
        labelState.setText(EquipmentPresenter.get().getStateModel().getName());
    }

    @FXML
    private void onClickAdd(){
        EquipmentPresenter.get().addEquipmentState(textAreaDescription.getText(),LocalDate.now());
    }

}
