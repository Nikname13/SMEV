package UI.Inventory_number.Controller;

import Model.Inventory_number.InventoryNumberModel;
import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import Presenter.InventoryNumberPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditInventoryNumber {

    private InventoryNumberModel mInvNumber;
    private SupplyModel mSupply;

    public EditInventoryNumber(){
        mInvNumber=new InventoryNumberPresenter().getInventoryNumberModel();
        mSupply=mInvNumber.getSupply();
    }

    @FXML
    private TextField numberText;

    @FXML
    private ComboBox<SupplyModel> comboBoxSupply;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private CheckBox checkBoxGroup;

    public void initialize(){
        numberText.setText(mInvNumber.getName());
        checkBoxGroup.setSelected(mInvNumber.isGroup());
        textAreaDescription.setText(mInvNumber.getDescription());
        comboBoxSupply.setCellFactory(p->new ListCell<SupplyModel>(){
            @Override
            protected void updateItem(SupplyModel supply,boolean empty){
                super.updateItem(supply,empty);
                if(supply!=null && !empty){
                    setText(supply.getName());
                }else{
                    setText(null);
                }
            }
        });
        comboBoxSupply.setItems(Supplys.get().getObsEntityList());
        comboBoxSupply.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->edit(newValue));
    }

    private void edit(SupplyModel supply){
        mSupply=supply;
    }

    @FXML
    private void onClickEdit(){
        new InventoryNumberPresenter().editInventoryNumber(numberText.getText(), mSupply, checkBoxGroup.isSelected(), textAreaDescription.getText());
    }

    @FXML
    private void onClickDelete(){
        new InventoryNumberPresenter().delete(mInvNumber.getId());
    }
}
