package Iteractor;

import Model.Area.AreaModel;
import Model.Area.Areas;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class IteractorArea extends GenericIteractor<AreaModel> {

    private static String sURL="/area_servlet";


    public IteractorArea() {
        super(sURL, AreaModel.class, new TypeToken<ArrayList<AreaModel>>(){}.getType());
    }

    @Override
    public void setList(ObservableList<AreaModel> list) {
        Areas.get().setEntityList(list);
    }

    @Override
    public void setEntity(AreaModel entity) {
        if(Areas.get().getEntity(entity.getId())!=null){
            Areas.get().replace(entity);
        }else{
            Areas.get().addEntity(entity);
        }
    }

    @Override
    public void deleteEntity(int id) {
        Areas.get().deleteEntity(id);
    }
}
