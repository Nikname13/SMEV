package Model.Inventory_number;

import Model.AbstractModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class InventoryNumberLog extends AbstractModel {

    private String mSupplyNumber;
    private String mDescription;

    public InventoryNumberLog(int id, String name, LocalDate date, String supplyNumber, String description) {
        super(id, name, date);
        mSupplyNumber = supplyNumber;
        mDescription = description;
    }

    public InventoryNumberLog(int id, String name) {
        super(id, name);
    }

    public InventoryNumberLog() {
    }

    public String getSupplyNumber() {
        return mSupplyNumber;
    }

    public void setSupplyNumber(String supplyNumber) {
        mSupplyNumber = supplyNumber;
    }

    public StringProperty supplyNumberProperty(){
        return new SimpleStringProperty(getSupplyNumber());
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public StringProperty descriptionProperty(){
        return new SimpleStringProperty(getDescription());
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new InventoryNumberLog(-1, name);
    }
}
