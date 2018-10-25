package Iteractor;

import Model.Department.Departments;
import Model.Worker.WorkerModel;
import Model.Worker.Workers;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

public class IteractorWorker extends GenericIteractor<WorkerModel>  {

    private static String sURL="/worker_servlet";

    public IteractorWorker() {
        super(sURL,WorkerModel.class,new TypeToken<ArrayList<WorkerModel>>(){}.getType());
    }

    @Override
    public String getGson(WorkerModel entity) {
        return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(Expose.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create().toJson(entity);
    }

    @Override
    public void setList(ObservableList<WorkerModel> list) {
        Workers.get().setEntityList(list, Comparator.comparing(WorkerModel::getNameToLowerCase));
    }

    @Override
    public void setEntity(WorkerModel entity) {
        if(Workers.get().getEntity(entity.getId())!=null){
            Workers.get().replace(entity);
        }else {
            Workers.get().addEntity(entity, Comparator.comparing(WorkerModel::getNameToLowerCase));
        }
    }

    @Override
    public void deleteEntity(WorkerModel entity) {
        Departments.get().getEntity(entity.getDepartmentModel().getId()).getWorkerList().remove(entity);
        Workers.get().deleteEntity(entity.getId());
    }
}
