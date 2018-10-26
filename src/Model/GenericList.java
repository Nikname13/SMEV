package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;

public abstract class GenericList<T> implements IGenericList<T> {

    private ObservableList<T> mEntityList;

    protected GenericList() {
        mEntityList= FXCollections.observableArrayList();
    }

    public ObservableList<T> getObsEntityList() {
        return mEntityList;
    }

    public ObservableList<T> getEntityListWithout(T entityExcluding) {
        ObservableList<T> list = FXCollections.observableArrayList();
        for (T entity : mEntityList) {
            if (entity.hashCode() != entityExcluding.hashCode()) {
                list.add(entity);
            }
        }
        return list;
    }

    public void setEntityList(ObservableList<T> entityList) {
        mEntityList = entityList;
    }

    public void setEntityList(ObservableList<T> entityList, Comparator c) {
        setEntityList(entityList);
        sortToLowerCase(c);
    }

    @Override
    public void addEntity(T entity) {
        mEntityList.add(entity);
    }


    public void addEntity(T entity, Comparator c) {
        addEntity(entity);
        sortToLowerCase(c);
    }

    @Override
    public T getEntity(int id) {
        for(T value:mEntityList){
            if(value.hashCode()==id) return value;
        }
        return null;
    }

    @Override
    public void deleteEntity(T entity) {
        mEntityList.remove(entity);
    }

    @Override
    public void deleteEntity(int id) {
        mEntityList.remove(getEntity(id));
    }

    @Override
    public void clear() {
        mEntityList.clear();
    }

    @Override
    public abstract void update();

    @Override
    public abstract void replace(T entity);

    @Override
    public void sortToLowerCase(Comparator c) {
        Collections.sort(mEntityList, c);
    }
}
