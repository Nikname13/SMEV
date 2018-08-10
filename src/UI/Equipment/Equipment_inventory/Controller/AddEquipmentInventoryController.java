package UI.Equipment.Equipment_inventory.Controller;

import Model.Equipment.EquipmentModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.State.StateModel;
import Presenter.EquipmentPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class AddEquipmentInventoryController {

    private EquipmentModel mEquipment;
    private InventoryNumberModel mInventory;
    private StateModel mState;
    public AddEquipmentInventoryController(){
        mEquipment=EquipmentPresenter.get().getEquipmentModel();
    }

    @FXML
    private TextField textFieldGuaranty;

    @FXML
    private Label labelPhotos, labelDepartment, labelName;

    @FXML
    private ComboBox<StateModel> comboBoxState;

    @FXML
    private ComboBox<InventoryNumberModel> comboBoxInventory;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private AnchorPane anchorPaneEquipmentInventory;

    public void initialize(){
        labelName.setText(mEquipment.getName());
        comboBoxInventory.setCellFactory(p->new ListCell<InventoryNumberModel>(){
            @Override
            protected void updateItem(InventoryNumberModel inventory, boolean empty){
                super.updateItem(inventory,empty);
                if(inventory!=null && !empty){
                    setText(inventory.getName());
                }else {
                    setText(null);
                }
            }
        });
        comboBoxInventory.setItems(EquipmentPresenter.get().getObservableInventory());
        comboBoxInventory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedInventory(newValue)));
        comboBoxState.setCellFactory(p-> new ListCell<StateModel>(){
            @Override
            protected void updateItem(StateModel state, boolean empty){
                super.updateItem(state,empty);
                if(state!=null && !empty){
                    setText(state.getName());
                }else {
                    setText(null);
                }
            }
        });
        comboBoxState.setItems(EquipmentPresenter.get().getObservableState());
    }

    private void selectedInventory(InventoryNumberModel inventory){
        mInventory=inventory;
    }

    @FXML
    private void onClickAdd(){
        EquipmentPresenter.get().addEquipmentInventory(
                mInventory,
                Integer.parseInt(textFieldGuaranty.getText()),
                textAreaDescription.getText(),
                null,
                mEquipment,
                comboBoxState.getValue());
    }
}
