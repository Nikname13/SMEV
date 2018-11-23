package Model.Supply;

import Iteractor.IteractorInventoryNumber;
import Iteractor.IteractorSupply;
import Model.FileDumpModel;
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

    public SupplyModel(int id, String name) {
        super(id, name);
    }

    @Override
    public List<InventoryNumberModel> getEntityList() {
            mEntityList = new IteractorInventoryNumber().getList(getId());
        return mEntityList;
    }

    public SupplyModel(){}

    @Override
    public GenericModel<InventoryNumberModel> create(String name) {
        return new SupplyModel(-1, name);
    }

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

    @Override
    public List<FileDumpModel> getFileDumpDocList() {
        List<FileDumpModel> list = new IteractorSupply().getFilesList(getId(), getTypeDoc(), this);
        if (list != null) {
            setFileDumpDocList(list);
        }
        return super.getFileDumpDocList();
    }
}
