package Model;

import javafx.collections.ObservableList;

public interface IGenericModel<T> {

    ObservableList<T> getObservableEntityList();
    T getEntity(int id);
    void addEntity(T entity);
    T getLastEntity();
    void deleteEntity(T entity);
    void deleteEntity(int id);
    void replace(T entity);
}
