package Iteractor;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class GenericIteractor<T> implements IIteractor<T> {

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
        String url=new URLBuilder(sURI).withParam("id",String.valueOf(id)).build();
        setEntity(new GsonBuilder().create().fromJson(Connector.get(url),mModel));
    }

    @Override
    public String getGson(T entity){
        return new GsonBuilder().create().toJson(entity);
    }

    @Override
    public T addNew(T entity) {
        String json=Connector.post(new URLBuilder(sURI).build(), getGson(entity));
        if(json!=null){
            setEntity(new Gson().fromJson(json,mModel));
            return new Gson().fromJson(json,mModel);
        }else{
            System.out.println("Ошибка при добавлении записи");
            return null;
        }
    }

    @Override
    public T addNew(T entity, int count) {
        URLBuilder url = new URLBuilder(sURI).withParam("count", String.valueOf(count));
        String json = Connector.post(url.build(), getGson(entity));
        if (json != null) {
            setEntity(new Gson().fromJson(json, mModel));
            return new Gson().fromJson(json, mModel);
        } else {
            System.out.println("Ошибка при добавлении записи");
            return null;
        }
    }

    @Override
    public T edit(T entity) {
        String json=Connector.put(new URLBuilder(sURI).build(),getGson(entity));
        if(json != null) {
            setEntity(new Gson().fromJson(json,mModel));
            return new Gson().fromJson(json, mModel);
        }else{
            System.out.println("Ошибка при обновлении записи");
            return null;
        }
    }

    @Override
    public boolean delete(Set<Integer> idList) {
        URLBuilder url=new URLBuilder(sURI);
        for(int id:idList){
            url.withParam("id",String.valueOf(id));
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
        URLBuilder url=new URLBuilder(sURI).withParam("id", String.valueOf(id));
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
        URLBuilder url=new URLBuilder(sURI).withParam("id", String.valueOf(entity.hashCode()));
        if(Connector.delete(url.build())==200){
            deleteEntity(entity);
        }else{
            System.out.println("Ошибка удаления");
            return false;
        }
        return true;
    }

    @Override
    public List<T> getList(int id) {
        URLBuilder url=new URLBuilder(sURI);
        return new Gson().fromJson(Connector.get(url.withParam("id", String.valueOf(id)).withParam("type", "default").build()), mListType);
    }

    @Override
    public List<T> getList(int id, String nameField) {
        URLBuilder url = new URLBuilder(sURI);
        return new Gson().fromJson(Connector.get(url.withParam("id", String.valueOf(id)).withParam("type", nameField).build()), mListType);
    }

    @Override
    public List<FileDumpModel> uploadFile(int id, List<File> files, String type) throws IOException {
        URLBuilder url=new URLBuilder(getLoadFileURL());
        List<FileDumpModel> list= new Gson().fromJson(
                Connector.post(url.withParam("id",String.valueOf(id)).withParam("type", type).build(), files),
                new TypeToken<ArrayList<FileDumpModel>>(){}.getType());
        return list;
    }

    @Override
    public List<FileDumpModel> getFiles(int id, String type) {
        URLBuilder url=new URLBuilder(getLoadFileURL());
        List<FileDumpModel> list= new Gson().fromJson(
                Connector.get(url.withParam("id",String.valueOf(id)).withParam("type", type).build()),
                new TypeToken<ArrayList<FileDumpModel>>(){}.getType());
        return list;
    }

    @Override
    public File downloadFile(int id, String type, String path, File file) {
        URLBuilder url=new URLBuilder(getLoadFileURL());
       return Connector.get(url.withParam("id",String.valueOf(id)).withParam("type", type).withParam("path", path).build(),file);
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
    public String getLoadFileURL() {
        return null;
    }

}
