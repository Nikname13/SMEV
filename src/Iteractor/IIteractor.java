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

    T edit(T entity);

    boolean delete(Set<Integer> idList);

    boolean delete(int id);

    boolean delete(T entity);
     List<T> getList(int id);
     List<FileDumpModel> uploadFile(int id, List<File> files, String type) throws IOException;
     List<FileDumpModel> getFiles(int id, String type);
     File downloadFile(int id, String type, String path, File file);
     /*переопределяемые методы*/
     String getGson(T entity);
     void setList(ObservableList<T> list);
     void setEntity(T entity);
     void deleteEntity(int id);
     void deleteEntity(T entity);
     String getLoadFileURL();

}
