package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericModel<T> extends AbstractModel<GenericModel<T>> implements IGenericModel<T>, Serializable {


    protected List<T> mEntityList;
    private transient boolean mIsLoad;

    public GenericModel(int id, String name, List<T> entityList) {
        super(id, name);
        mEntityList = entityList;
    }

    public GenericModel(int id, String name){
        super(id,name);
    }

    public GenericModel(int id){
        super(id);
    }

    public GenericModel() {
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
        //if (getEntity(entity.hashCode()) == null)
            mEntityList.add(entity);
    }

    @Override
    public void deleteEntity(T entity) {
        mEntityList.remove(entity);
    }

    @Override
    public void deleteEntity(int id) {
    }

    @Override
    public void replace(T entity){
    }
}
