package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericModel<T> extends AbstractModel<GenericModel<T>> implements IGenericModel<T>, Serializable {


    protected List<T> mEntityList;
    private FileDumpModel mAvatar;
    private transient boolean mIsLoad;
    private transient List<FileDumpModel> mFileDumpDocList, mFileDumpConfigList, mFileDumpPhotoList;

    public GenericModel(int id, String name, List<T> entityList) {
        super(id, name);
        mEntityList = entityList;
    }

    public GenericModel(int id, String name, List<T> entityList, LocalDate date) {
        super(id, name, date);
        mEntityList = entityList;
    }

    public GenericModel(int id, String name){
        super(id,name);
    }

    public GenericModel(int id, String name, LocalDate date) {
        super(id, name, date);
    }

    public GenericModel(int id){
        super(id);
    }

    public GenericModel() {
    }

    public GenericModel(int id, String number, LocalDate dateSupply, List<T> inventoryList) {
        super(id, number, dateSupply);
        mEntityList = inventoryList;
    }

    public List<T> getEntityList() {
        if(mEntityList==null) mEntityList=new ArrayList<>();
        return mEntityList;
    }

    public void setEntityList(List<T> entityList) {
        mEntityList = entityList;
    }

    public boolean isLoad() {
        return mIsLoad;
    }

    public void setLoad(boolean load) {
        mIsLoad = load;
    }


    public FileDumpModel getAvatar() {
        return mAvatar;
    }

    public void setAvatar(FileDumpModel avatar) {
        mAvatar = avatar;
    }


    @Override
    public ObservableList<T> getObservableEntityList() {
        ObservableList<T> obsList= FXCollections.observableArrayList();
        for (T value : getEntityList()) {
            obsList.add(value);
        }
        return obsList;
    }

    @Override
    public T getEntity(int id) {
        for(T value:mEntityList){
            if(value.hashCode()==id) return value;
        }
        return null;
    }

    @Override
    public void addEntity(T entity) {
        if(mEntityList == null) mEntityList=new ArrayList<>();
            mEntityList.add(entity);
    }

    public List<FileDumpModel> getFileDumpDocList() {
        return mFileDumpDocList;
    }

    public void setFileDumpDocList(List<FileDumpModel> fileDumpDocList) {
        mFileDumpDocList = fileDumpDocList;
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

    public ObservableList<FileDumpModel> getObsFileDumpDocList() {
        ObservableList<FileDumpModel> list = FXCollections.observableArrayList();
        for (FileDumpModel file : getFileDumpDocList()) {
            list.add(file);
        }
        return list;
    }

    public ObservableList<FileDumpModel> getObsFileDumpConfigList() {
        ObservableList<FileDumpModel> list = FXCollections.observableArrayList();
        for (FileDumpModel file : getFileDumpConfigList()) {
            list.add(file);
        }
        return list;
    }


    private ObservableList<FileDumpModel> getObsPhotoList() {
        ObservableList<FileDumpModel> list = FXCollections.observableArrayList();
        for (FileDumpModel file : getFileDumpPhotoList()) {
            list.add(file);
        }
        return list;
    }

    public ObservableList<FileDumpModel> getFilesList(String type) {
        switch (type) {
            case "doc":
                return getObsFileDumpDocList();
            case "config":
                return getObsFileDumpConfigList();
            case "photo":
                return getObsPhotoList();
        }
        return null;
    }

    public LocalDateTime getLastFileDumpUpdate(String type) {
        switch (type) {
            case "doc":
                return getLastUpdateDocList();
            case "config":
                return getLastUpdateConfigList();
            case "photo":
                return getObsLastUpdatePhotoList();
        }
        return null;
    }

    private LocalDateTime getObsLastUpdatePhotoList() {
        if (mFileDumpPhotoList != null && mFileDumpPhotoList.size() != 0) {
            LocalDateTime lastUpdate = mFileDumpPhotoList.get(0).getLastUpdate();
            for (FileDumpModel file : mFileDumpPhotoList) {
                if (file.getLastUpdate().isAfter(lastUpdate)) {
                    lastUpdate = file.getLastUpdate();
                }
            }
            return lastUpdate;
        }
        return null;
    }

    private LocalDateTime getLastUpdateConfigList() {
        if (mFileDumpConfigList != null && mFileDumpConfigList.size() != 0) {
            LocalDateTime lastUpdate = mFileDumpConfigList.get(0).getLastUpdate();
            for (FileDumpModel file : mFileDumpConfigList) {
                if (file.getLastUpdate().isAfter(lastUpdate)) {
                    lastUpdate = file.getLastUpdate();
                }
            }
            return lastUpdate;
        }
        return null;
    }

    private LocalDateTime getLastUpdateDocList() {
        if (mFileDumpDocList != null && mFileDumpDocList.size() != 0) {
            LocalDateTime lastUpdate = mFileDumpDocList.get(0).getLastUpdate();
            for (FileDumpModel file : mFileDumpDocList) {
                if (file.getLastUpdate().isAfter(lastUpdate)) {
                    lastUpdate = file.getLastUpdate();
                }
            }
            return lastUpdate;
        }
        return null;
    }


    @Override
    public void deleteEntity(T entity) {
        mEntityList.remove(entity);
    }

    @Override
    public void deleteEntity(int id) {
    }

    public void deleteFile(int id, List<FileDumpModel> list) {
        if (list != null) {
            for (FileDumpModel file : list) {
                if (file.getId() == id) {
                    list.remove(file);
                    break;
                }
            }
        }
    }

    @Override
    public void replace(T entity){
    }
}
