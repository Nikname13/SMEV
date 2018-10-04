package Model.Inventory_number;

import Model.AbstractModel;
import Model.Supply.SupplyModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InventoryNumberModel extends AbstractModel {

    private String mDescription;
    private boolean mGroup;
    private SupplyModel mSupply;

    public InventoryNumberModel(int id, String number, SupplyModel supply, boolean group, String description) {
        super(id,number);
        mDescription = description;
        mGroup = group;
        mSupply = supply;
    }

    public InventoryNumberModel(int id, String number, boolean group, String description) {
        super(id, number);
        mDescription = description;
        mGroup = group;
    }

    public InventoryNumberModel(int id, String number){
        super(id,number);
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isGroup() {
        return mGroup;
    }

    public void setGroup(boolean group) {
        mGroup = group;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public BooleanProperty groupProperty() {
        return new SimpleBooleanProperty(mGroup);
    }

    public SupplyModel getSupply() {
        return mSupply;
    }

    public void setSupply(SupplyModel supply) {
        mSupply = supply;
    }


}
