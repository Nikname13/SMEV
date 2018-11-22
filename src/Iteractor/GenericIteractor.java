package Iteractor;

import Model.AbstractModel;
import Model.FileDumpModel;
import Net.Connector;
import Net.URLBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class GenericIteractor<T extends AbstractModel> implements IIteractor<T> {

    private String sURI;
    private Class<T> mModel;
    private Type mListType;

    public GenericIteractor(String uri, Class<T> model,Type listType) {
        sURI = uri;
        mModel=model;
        mListType = listType;
    }

    @Override
    public void loadData() {
        List<T> list = new Gson().fromJson(Connector.get(new URLBuilder(sURI).build()), mListType);
        if (list!=null) {
            ObservableList<T> entityList = FXCollections.observableArrayList();
            for (T parameter : list) {
                entityList.add(parameter);
            }
            setList(entityList);
        }
    }

    @Override
    public void loadData(int id) {
        String url = new URLBuilder(sURI).withParam("id", id).build();
        setEntity(new GsonBuilder().create().fromJson(Connector.get(url),mModel));
    }

    @Override
    public String getGson(T entity){
        return new GsonBuilder().create().toJson(entity);
    }

    public String getGson(List<T> entity) {
        return new GsonBuilder().create().toJson(entity);
    }

    @Override
    public T addNew(T entity) {
        List<T> list = new ArrayList();
        list.add(entity);
        return addNew(list).iterator().next();
    }

    @Override
    public List<T> addNew(List<T> entityList) {
        String json = Connector.post(new URLBuilder(sURI).build(), getGson(entityList));
        if (json != null) {
            List<T> list = new Gson().fromJson(json, mListType);
            for (T entity : list) {
                setEntity(entity);
            }
            return list;
        } else {
            System.out.println("Ошибка при добавлении записи");
            return null;
        }
    }

    @Override
    public T edit(T entity) {
        List<T> list = new ArrayList<>();
        list.add(entity);
        return edit(list).iterator().next();
    }

    @Override
    public List<T> edit(List<T> entityList) {
        String json = Connector.put(new URLBuilder(sURI).build(), getGson(entityList));
        if (json != null) {
            List<T> list = new Gson().fromJson(json, mListType);
            for (T entity : list) {
                setEntity(entity);
            }
            return list;
        } else {
            System.out.println("Ошибка при обновлении записи");
            return null;
        }
    }

    @Override
    public boolean delete(Set<Integer> idList) {
        URLBuilder url=new URLBuilder(sURI);
        for(int id:idList){
            url.withParam("id", id);
        }
        if(Connector.delete(url.build())==200){
            for(Integer id:idList) {
                deleteEntity(id);
            }
        } else{
            System.out.println("Ошибка удаления");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        URLBuilder url = new URLBuilder(sURI).withParam("id", id);
        if(Connector.delete(url.build())==200){
            deleteEntity(id);
        }else{
            System.out.println("Ошибка удаления");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(T entity) {
        URLBuilder url = new URLBuilder(sURI).withParam("id", entity.getId());
        if(Connector.delete(url.build())==200){
            deleteEntity(entity);
        }else{
            System.out.println("Ошибка удаления");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int idEntity, int idFile, String type) {
        URLBuilder url = new URLBuilder(getLoadFileURL()).withParam("id", idFile).withParam("idEntity", idEntity).withParam("type", type);
        if (Connector.delete(url.build()) == 200) {
            deleteFile(idEntity, idFile, type);
        } else {
            System.out.println("Ошибка удаления");
            return false;
        }
        return true;
    }

    @Override
    public List<T> getList(int id) {
        URLBuilder url=new URLBuilder(sURI);
        return new Gson().fromJson(Connector.get(url.withParam("id", id).withParam("type", "default").build()), mListType);
    }

    @Override
    public List<T> getList(int id, String nameField) {
        URLBuilder url = new URLBuilder(sURI);
        return new Gson().fromJson(Connector.get(url.withParam("id", id).withParam("type", nameField).build()), mListType);
    }

    @Override
    public List<FileDumpModel> uploadFile(int id, List<File> files, String type) {
        try {
            URLBuilder url = new URLBuilder(getLoadFileURL());
            List<FileDumpModel> list = new Gson().fromJson(
                    Connector.post(url.withParam("id", id).withParam("type", type).build(), files),
                    new TypeToken<ArrayList<FileDumpModel>>() {
                    }.getType());
            return list;
        } catch (IOException ex) {
            System.out.println("создать еррор диалог " + ex);
            return null;
        }
    }

    @Override
    public List<FileDumpModel> getFilesList(int id, String type, T entity) {
        URLBuilder url=new URLBuilder(getLoadFileURL());
        List<FileDumpModel> list= new Gson().fromJson(
                Connector.get(url.withParam("id", String.valueOf(id)).withParam("type", type).withParam("lastUpdate", getLastUpdate(entity)).build()),
                new TypeToken<ArrayList<FileDumpModel>>(){}.getType());
        return list;
    }

    protected LocalDateTime getLastUpdate(T entity) {
        return null;
    }

    @Override
    public File downloadFile(int id, String type, String path, File file) {
        URLBuilder url=new URLBuilder(getLoadFileURL());
        return Connector.get(url.withParam("id", id).withParam("type", type).withParam("path", path).build(), file);
    }

    @Override
    public FileDumpModel editFile(FileDumpModel entity, int id, String type) {
        URLBuilder url = new URLBuilder(getLoadFileURL());
        String json = Connector.put(url.withParam("id", id).withParam("type", type).build(), new GsonBuilder().create().toJson(entity));
        if (json != null) {
            return new Gson().fromJson(json, FileDumpModel.class);
        }
        return null;
    }

    @Override
    public void setList(ObservableList<T> list) {

    }

    @Override
    public void setEntity(T entity) {

    }

    @Override
    public void deleteEntity(int id) {

    }

    @Override
    public void deleteEntity(T entity) {

    }

    @Override
    public void deleteFile(int idEntity, int idFile, String type) {
    }

    @Override
    public String getLoadFileURL() {
        return null;
    }

}
