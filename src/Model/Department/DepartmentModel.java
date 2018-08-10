package Model.Department;

import Iteractor.IteractorDepartment;
import Iteractor.IteractorLocation;
import Model.Area.AreaModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.FileDumpModel;
import Model.GenericModel;
import Model.Location.LocationModel;
import Model.Worker.WorkerModel;
import com.google.gson.annotations.Expose;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DepartmentModel extends GenericModel<PurchaseModel> {

    private String mNumber, mDescription;
    private boolean mElectronicQ, mRenting;
    private AreaModel mAreaModel;
    @Expose
    private List<WorkerModel> mWorkerList;
    @Expose
    private List<LocationModel> mLocationList;
    @Expose
    private List<EquipmentInventoryModel> mEquipmentList;
    private transient List<FileDumpModel> mFileDumpDocList, mFileDumpConfigList, mFileDumpPhotoList;

    public DepartmentModel(int id, String number, String name, boolean eleсtronicQ, boolean renting, String description, AreaModel area) {
        super(id, name);
        mNumber = number;
        mDescription = description;
        mElectronicQ = eleсtronicQ;
        mRenting = renting;
        mAreaModel = area;
        mLocationList = new ArrayList<>();
        mWorkerList = new ArrayList<>();
        mEquipmentList = new ArrayList<>();
    }

    public DepartmentModel(int id, String name) {
        super(id, name);
    }

    @Override
    public List<PurchaseModel> getEntityList() {
        if (!isLoad()) {
            new IteractorDepartment().loadData(getId());
        }
        return super.getEntityList();
    }

    public ObservableList<EquipmentInventoryModel> getObsEquipmnetList() {
        ObservableList<EquipmentInventoryModel> equipmentList = FXCollections.observableArrayList();
        for (EquipmentInventoryModel equipment : getEquipmentList()) {
            equipmentList.add(equipment);
        }
        return equipmentList;
    }

    public List<EquipmentInventoryModel> getEquipmentList() {
        if (!isLoad()) {
            new IteractorDepartment().loadData(getId());
        }
        return mEquipmentList;
    }

    public void setEquipmentList(List<EquipmentInventoryModel> list) {
        mEquipmentList = list;
    }

    public void addEquipment(EquipmentInventoryModel equipment) {
        mEquipmentList.add(equipment);
    }

    public EquipmentInventoryModel getEquipment(int id) {
        for (EquipmentInventoryModel equipment : mEquipmentList) {
            if (equipment.getId() == id) return equipment;
        }
        return null;
    }

    public void deleteEquipment(EquipmentInventoryModel equipment) {
        mEquipmentList.remove(equipment);
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isElectronicQ() {
        return mElectronicQ;
    }

    public void setElectronicQ(boolean electronicQ) {
        mElectronicQ = electronicQ;
    }

    public boolean isRenting() {
        return mRenting;
    }

    public void setRenting(boolean renting) {
        mRenting = renting;
    }


    public StringProperty numberProperty() {
        return new SimpleStringProperty(mNumber);
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public BooleanProperty electronicQProperty() {
        return new SimpleBooleanProperty(mElectronicQ);
    }

    public BooleanProperty rentingProperty() {
        return new SimpleBooleanProperty(mRenting);
    }

    public AreaModel getAreaModel() {
        return mAreaModel;
    }

    public void setAreaModel(AreaModel areaModel) {
        mAreaModel = areaModel;
    }

    public ObservableList<WorkerModel> getObsWorkerList() {
        ObservableList<WorkerModel> workerList = FXCollections.observableArrayList();
        for (WorkerModel worker : getWorkerList()) {
            workerList.add(worker);
        }
        return workerList;
    }

    public List<WorkerModel> getWorkerList() {
        if (!isLoad()) {
            new IteractorDepartment().loadData(getId());
        }
        return mWorkerList;
    }

    public void setWorkerList(List<WorkerModel> workerList) {
        mWorkerList = workerList;
    }

    public void addWorker(WorkerModel worker) {
        mWorkerList.add(worker);
    }

    public WorkerModel getWorker(int id) {
        for (WorkerModel worker : mWorkerList) {
            if (worker.getId() == id) return worker;
        }
        return null;
    }

    public ObservableList<LocationModel> getObsLocationList() {
        ObservableList<LocationModel> locationList = FXCollections.observableArrayList();
        for (LocationModel location : getLocationList()) {
            locationList.add(location);
        }
        return locationList;
    }

    public List<LocationModel> getLocationList() {
        if (mLocationList == null) {
            mLocationList = new ArrayList<>();
            mLocationList = new IteractorLocation().getList(getId());
        }
        return mLocationList;
    }

    public void setLocationList(List<LocationModel> locationList) {
        mLocationList = locationList;
    }

    public void addLocation(LocationModel location) {
        mLocationList.add(location);
    }

    public LocationModel getLocation(int id) {
        for (LocationModel location : mLocationList) {
            if (location.getId() == id) return location;
        }
        return null;
    }

    public List<FileDumpModel> getFileDumpDocList() {
        if (mFileDumpDocList == null) mFileDumpDocList = new ArrayList<>();
        mFileDumpDocList = new IteractorDepartment().getFiles(getId(), getTypeDoc());
        return mFileDumpDocList;
    }

    public void setFileDumpDocList(List<File> fileList) throws IOException {
        mFileDumpDocList = new IteractorDepartment().uploadFile(getId(), fileList, getTypeDoc());
        for (FileDumpModel file : mFileDumpDocList) {
            System.out.println(file.toString());
        }
    }

    public ObservableList<FileDumpModel> getObsFileDumpDocList() {
        if (mFileDumpDocList == null) getFileDumpDocList();
        ObservableList<FileDumpModel> list = FXCollections.observableArrayList();
        for (FileDumpModel file : mFileDumpDocList) {
            list.add(file);
        }
        return list;
    }

    public void addFileDumpDocList(List<File> fileList) throws IOException {
        if (mFileDumpDocList == null) {
            mFileDumpDocList = new ArrayList<>();
            setFileDumpDocList(fileList);
        } else {
            mFileDumpDocList.addAll(new IteractorDepartment().uploadFile(getId(), fileList, getTypeDoc()));
        }
    }

    public List<FileDumpModel> getFileDumpConfigList() {
        return mFileDumpConfigList;
    }

    public void setFileDumpConfigList(List<FileDumpModel> fileDumpConfigList) {
        mFileDumpConfigList = fileDumpConfigList;
    }

    public List<FileDumpModel> getFileDumpPhotoList() {
        return mFileDumpPhotoList;
    }

    public void setFileDumpPhotoList(List<FileDumpModel> fileDumpPhotoList) {
        mFileDumpPhotoList = fileDumpPhotoList;
    }

    @Override
    public void replace(PurchaseModel entity) {
        PurchaseModel purchase = getEntity(entity.getId());
        purchase.setName(entity.getName());
        purchase.setDate(entity.getDate());
        purchase.setDescription(entity.getDescription());
    }

    public void replace(EquipmentInventoryModel entity) {
        EquipmentInventoryModel equipment = getEquipment(entity.getId());
        //equipment.setLoad(entity.isLoad());
        equipment.setDescription(entity.getDescription());
        equipment.setDescription_department(entity.getDescription_department());
        equipment.setGuaranty(entity.getGuaranty());
        equipment.setInventoryNumber(entity.getInventoryNumber());
    }

    @Override
    public String toString() {
        return mNumber + " - " + getName();
    }
}
