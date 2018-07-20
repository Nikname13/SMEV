package Iteractor;

import Model.Worker.WorkerModel;
import Model.Worker.Workers;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class IteractorWorker extends GenericIteractor<WorkerModel>  {

    private static String sURL="/worker_servlet";

    public IteractorWorker() {
        super(sURL,WorkerModel.class,new TypeToken<ArrayList<WorkerModel>>(){}.getType());
    }

    @Override
    public void setList(ObservableList<WorkerModel> list) {
        Workers.get().setEntityList(list);
    }

    @Override
    public void setEntity(WorkerModel entity) {
        if(Workers.get().getEntity(entity.getId())!=null){
            Workers.get().replace(entity);
        }else {
            Workers.get().addEntity(entity);
        }
    }

    @Override
    public void deleteEntity(int id) {
        Workers.get().deleteEntity(id);
    }
}
