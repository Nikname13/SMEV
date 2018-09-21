package Model.Equipment;

import Iteractor.IteractorInventoryLog;
import Iteractor.IteractorStateLog;
import Model.Department.DepartmentModel;
import Model.GenericModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.State.StateModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipmentInventoryModel extends GenericModel<EquipmentStateLogModel> {

    private int mGuaranty;
    private transient Image mAvatar;
    private String mDescription, mDescription_department;
    private InventoryNumberModel mInventoryNumber;
    private DepartmentModel mDepartmentModel;
    private EquipmentModel mEquipmentModel;
    private StateModel mStateModel;
    private List<EquipmentInventoryLogModel> mInventoryEditLog;

    public EquipmentInventoryModel(int id, InventoryNumberModel inventoryNumber, int guaranty, String description,
                                   DepartmentModel departmentModel, EquipmentModel equipmentModel, StateModel stateModel) {
        super(id);
        mGuaranty = guaranty;
        mDescription = description;
        mInventoryNumber = inventoryNumber;
        mDepartmentModel = departmentModel;
        mEquipmentModel=equipmentModel;
        mStateModel = stateModel;
    }

    public EquipmentInventoryModel(int id, InventoryNumberModel inventoryNumber, EquipmentModel equipmentModel, DepartmentModel departmentModel, StateModel state) {
        super(id);
        mInventoryNumber=inventoryNumber;
        mEquipmentModel=equipmentModel;
        mDepartmentModel=departmentModel;
        mStateModel = state;
    }

    public List<EquipmentInventoryLogModel> getInventoryEditLog() {
            mInventoryEditLog = new IteractorInventoryLog().getList(getId());
        return mInventoryEditLog;
    }

    public void addFirstInventoryNumber() {
        addInventoryEditLog(new EquipmentInventoryLogModel(0, mInventoryNumber.getName(),
                mInventoryNumber.getId(), LocalDate.now(), "Первый номер"));
    }

    @Override
    public List<EquipmentStateLogModel> getEntityList() {
        setEntityList(new IteractorStateLog().getList(getId()));
        return super.getEntityList();
    }

    public void setInventoryEditLog(List<EquipmentInventoryLogModel> inventoryEditLog) {
        mInventoryEditLog = inventoryEditLog;
    }

    public void addInventoryEditLog(EquipmentInventoryLogModel entry) {
        if (mInventoryEditLog == null) mInventoryEditLog = new ArrayList<>();
        mInventoryEditLog.add(entry);
    }

    public ObservableList<EquipmentInventoryLogModel> getObsInventoryLogList() {
        ObservableList<EquipmentInventoryLogModel> list = FXCollections.observableArrayList();
        for (EquipmentInventoryLogModel inventoryLog : getInventoryEditLog()) {
            list.add(inventoryLog);
        }
        return list;
    }

    public StateModel getStateModel() {
        return mStateModel;
    }

    public void setStateModel(StateModel stateModel) {
        mStateModel = stateModel;
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
