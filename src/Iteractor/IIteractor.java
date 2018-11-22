package Iteractor;


import Model.FileDumpModel;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IIteractor<T> {

    /*общие методы*/
    void loadData();

    void loadData(int id);

    T addNew(T entity);

    List<T> addNew(List<T> entity);

    T edit(T entity);

    List<T> edit(List<T> entity);

    boolean delete(Set<Integer> idList);

    boolean delete(int id);

    boolean delete(T entity);

    boolean delete(int idEntity, int idFile, String type);

    List<T> getList(int id);

    List<T> getList(int id, String nameField);

    List<FileDumpModel> uploadFile(int id, List<File> files, String type) throws IOException;

    List<FileDumpModel> getFilesList(int id, String type, T entity);

    FileDumpModel editFile(FileDumpModel entity, int id, String type);

    File downloadFile(int id, String type, String path, File file);

    /*переопределяемые методы*/
    String getGson(T entity);

    void setList(ObservableList<T> list);

    void setEntity(T entity);

    void deleteEntity(int id);

    void deleteEntity(T entity);

    void deleteFile(int idEntity, int idFile, String type);

    String getLoadFileURL();

}
