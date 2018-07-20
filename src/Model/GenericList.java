package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GenericList<T> implements IGenericList<T>{

    private ObservableList<T> mEntityList;

    protected GenericList() {
        mEntityList= FXCollections.observableArrayList();
    }

    public ObservableList<T> getEntityList() {
        return mEntityList;
    }

    public void setEntityList(ObservableList<T> entityList) {
        mEntityList = entityList;
    }

    @Override
    public void addEntity(T entity) {
        mEntityList.add(entity);
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
    public void update() {
        System.out.println("Метод не переопределен");
    }

    @Override
    public void replace(T entity) {
        System.out.println("Метод не переопределен");
    }

}
