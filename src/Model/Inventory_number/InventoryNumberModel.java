package Model.Inventory_number;

import Iteractor.IteractorEquipmentInventory;
import Iteractor.IteractorInventoryNumberLog;
import Model.Equipment.EquipmentInventoryModel;
import Model.GenericModel;
import Model.Supply.SupplyModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class InventoryNumberModel extends GenericModel<InventoryNumberLog> {

    private String mDescription;
    private boolean mGroup;
    private SupplyModel mSupply;
    private transient List<EquipmentInventoryModel> mEquipmentInventoryList;

    public InventoryNumberModel(int id, String number, SupplyModel supply, boolean group, String description, InventoryNumberLog numberLog) {
        super(id,number);
        mDescription = description;
        mGroup = group;
        mSupply = supply;
        addEntity(numberLog);
    }

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

    @Override
    public List<InventoryNumberLog> getEntityList() {
            mEntityList=new ArrayList<>();
            mEntityList= new IteractorInventoryNumberLog().getList(getId());
        return super.getEntityList();
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

    public List<EquipmentInventoryModel> getEquipmentInventoryList() {
        if (mEquipmentInventoryList == null) {
            mEquipmentInventoryList = new ArrayList<>();
            mEquipmentInventoryList = new IteractorEquipmentInventory().getList(getId(), "inventoryNumber");
        }
        return mEquipmentInventoryList;
    }

    public void setEquipmentInventoryList(List<EquipmentInventoryModel> equipmentInventoryList) {
        mEquipmentInventoryList = equipmentInventoryList;
    }
}
