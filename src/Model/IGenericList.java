package Model;

import java.util.Comparator;

public interface IGenericList<T> {

    /*общие методы*/
    void addEntity(T entity);
     T getEntity(int id);
    void deleteEntity(T entity);
    void deleteEntity(int id);
    void clear();

    void sortToLowerCase(Comparator c);
    /*переопределяемые методы*/
    void update();
    void replace(T entity);


}
