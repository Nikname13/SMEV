package Model.Equipment;

import Model.Department.DepartmentModel;
import Model.GenericModel;
import Model.Inventory_number.InventoryNumberModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class EquipmentInventoryModel extends GenericModel<EquipmentStateModel>{

    private int mGuaranty;
    private transient Image mAvatar;
    private String mDescription, mDescription_department, mPhotos;
    private InventoryNumberModel mInventoryNumber;
    private DepartmentModel mDepartmentModel;
    private EquipmentModel mEquipmentModel;

    public EquipmentInventoryModel(int id, InventoryNumberModel inventoryNumber, int guaranty, String description, String photos,
                                   DepartmentModel departmentModel, EquipmentStateModel equipmentState, EquipmentModel equipmentModel) {
        super(id);
        addEntity(equipmentState);
        mGuaranty = guaranty;
        mDescription = description;
        mPhotos = photos;
        mInventoryNumber = inventoryNumber;
        mDepartmentModel = departmentModel;
        mEquipmentModel=equipmentModel;
    }

    public EquipmentInventoryModel(int id,InventoryNumberModel inventoryNumber, EquipmentModel equipmentModel, DepartmentModel departmentModel){
        super(id);
        mInventoryNumber=inventoryNumber;
        mEquipmentModel=equipmentModel;
        mDepartmentModel=departmentModel;
    }

    public int getGuaranty() {
        return mGuaranty;
    }

    public void setGuaranty(int guaranty) {
        mGuaranty = guaranty;
    }

    public Image getAvatar() {
        return mAvatar;
    }

    public void setAvatar(Image avatar) {
        mAvatar = avatar;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription_department() {
        return mDescription_department;
    }

    public void setDescription_department(String description_department) {
        mDescription_department = description_department;
    }

    public String getPhotos() {
        return mPhotos;
    }

    public void setPhotos(String photos) {
        mPhotos = photos;
    }

    public StringProperty description_departmentProperty() {
        return new SimpleStringProperty(mDescription_department);
    }

    public DepartmentModel getDepartmentModel() {
        return mDepartmentModel;
    }

    public void setDepartmentModel(DepartmentModel departmentModel) {
        mDepartmentModel = departmentModel;
    }

    public IntegerProperty guarantyProperty() {
        return new SimpleIntegerProperty(mGuaranty);
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public StringProperty photosProperty() {
        return new SimpleStringProperty(mPhotos);
    }

    public InventoryNumberModel getInventoryNumber() {
        return mInventoryNumber;
    }

    public void setInventoryNumber(InventoryNumberModel inventoryNumber) {
        mInventoryNumber = inventoryNumber;
    }

    public EquipmentModel getEquipmentModel() {
        return mEquipmentModel;
    }

    public void setEquipmentModel(EquipmentModel equipmentModel) {
        mEquipmentModel = equipmentModel;
    }

    @Override
    public String toString() {
        return getEquipmentModel().getName()+" - "+getInventoryNumber().getName();
    }
}
