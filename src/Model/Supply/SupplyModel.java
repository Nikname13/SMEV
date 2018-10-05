package Model.Supply;

import Model.GenericModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Provider.ProviderModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.List;

public class SupplyModel extends GenericModel<InventoryNumberModel> {

    private String mDescription, mTypeSupply;
    private ProviderModel mProviderModel;

    public SupplyModel(int id, String number, String typeSupply, LocalDate dateSupply, List<InventoryNumberModel> inventoryList, String description, ProviderModel providerModel) {
        super(id, number, dateSupply, inventoryList);
        mDescription = description;
        mTypeSupply = typeSupply;
        mProviderModel = providerModel;
    }

    public SupplyModel(int id, String number, ProviderModel provider) {
        super(id, number);
        mProviderModel = provider;
    }

    public SupplyModel(){}

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTypeSupply() {
        return mTypeSupply;
    }

    public void setTypeSupply(String typeSupply) {
        mTypeSupply = typeSupply;
    }

    public StringProperty typeSupplyProperty() {
        return new SimpleStringProperty(mTypeSupply);
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public ProviderModel getProviderModel() {
        return mProviderModel;
    }

    public void setProviderModel(ProviderModel providerModel) {
        mProviderModel = providerModel;
    }
}
