package UI.Inventory_number.Controller;

import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import Presenter.InventoryNumberPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class AddInventoryNumberController {

    private Object mSupply;

    @FXML
    private TextField numberText;

    @FXML
    private ComboBox<SupplyModel> comboBoxSupply;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private CheckBox checkBoxGroup;

    @FXML
    public void initialize(){
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
        comboBoxSupply.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->supplySelected(newValue));
    }

    private void supplySelected(Object supply){
        mSupply=supply;
    }

    @FXML
    private void onClickAdd(){
        new InventoryNumberPresenter().addInventoryNumber(numberText.getText(),mSupply,checkBoxGroup.isSelected(),textAreaDescription.getText());
    }

}
