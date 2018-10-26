package Model.Equipment;

import Iteractor.IteractorEquipmentInventory;
import Iteractor.IteractorEquipmentParameter;
import Model.GenericModel;
import Model.Type.TypeModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EquipmentModel extends GenericModel<EquipmentParameterModel> {

    private String mNameFact, mDescription;
    private TypeModel mTypeModel;

    private transient List<EquipmentInventoryModel> mEqInventoryList;

    public EquipmentModel(int id, String name, String nameFact, String description,
                          TypeModel typeModel, List<EquipmentParameterModel> valueList,
                          List<EquipmentInventoryModel> equipmentInventoryList) {
        super(id,name,valueList);
        mNameFact = nameFact;
        mDescription = description;
        mTypeModel = typeModel;
        mEqInventoryList = equipmentInventoryList;
    }

    public EquipmentModel(int id, String name){
        super(id,name);
        mNameFact = name;
    }

    public EquipmentModel(){}


    public List<EquipmentInventoryModel> getEquipmentInventoryList() {
        //mEqInventoryList = new ArrayList<>();
            mEqInventoryList = new IteractorEquipmentInventory().getList(getId(), "equipment");
        return mEqInventoryList;
    }

    public EquipmentInventoryModel getEquipmentInventory(int id) {
        for (EquipmentInventoryModel equipment : getEquipmentInventoryList()) {
            if (equipment.getId() == id) return equipment;
        }
        return null;
    }

    public EquipmentInventoryModel getEquipmentInventory(EquipmentInventoryModel equipment) {
        return getEquipmentInventory(equipment.getId());
    }

    public void setEquipmentInventoryList(List<EquipmentInventoryModel> eqInvList) {
        mEqInventoryList = eqInvList;
    }

    @Override
    public List<EquipmentParameterModel> getEntityList(){
            mEntityList = new IteractorEquipmentParameter().getList(getId());
        return mEntityList;
    }

    public ObservableList<EquipmentInventoryModel> getObservableEqInventoryList() {
        ObservableList<EquipmentInventoryModel> obsList=FXCollections.observableArrayList();
        for (EquipmentInventoryModel value : getEquipmentInventoryList()) {
            obsList.add(value);
        }
        return obsList;
    }

    public String getNameFact() {
        return mNameFact;
    }

    public void setNameFact(String nameFact) {
        mNameFact = nameFact;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void addEquipmentInventory(EquipmentInventoryModel value){
        mEqInventoryList.add(value);
    }

    public StringProperty nameFactProperty() {
        return new SimpleStringProperty(mNameFact);
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public TypeModel getTypeModel() {
        return mTypeModel;
    }

    public void setTypeModel(TypeModel typeModel) {
        mTypeModel = typeModel;
    }

    public void replace(EquipmentInventoryModel entity) {
        EquipmentInventoryModel equipment = getEquipmentInventory(entity);
        equipment.setLoad(entity.isLoad());
        equipment.setDescription(entity.getDescription());
        equipment.setDescription_department(entity.getDescription_department());
        equipment.setGuaranty(entity.getGuaranty());
        equipment.setInventoryNumber(entity.getInventoryNumber());
        equipment.setEntityList(entity.getEntityList());
    }

    @Override
    public void replace(EquipmentParameterModel entity) {
        super.replace(entity);
    }
}
