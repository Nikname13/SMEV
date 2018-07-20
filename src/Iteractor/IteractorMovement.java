package Iteractor;

import Model.Movement.MovementModel;
import Model.Movement.Movements;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class IteractorMovement extends GenericIteractor<MovementModel>  {

    private static String sURL="/movement_servlet";

    public IteractorMovement() {
        super(sURL, MovementModel.class, new TypeToken<ArrayList<MovementModel>>(){}.getType());
    }

    @Override
    public String getGson(MovementModel entity) {
            return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                    return fieldAttributes.getAnnotation(Expose.class)!=null;
                }

                @Override
                public boolean shouldSkipClass(Class<?> aClass) {
                    return false;
                }
            }).create().toJson(entity);
    }

    @Override
    public void setList(ObservableList<MovementModel> list) {
        Movements.get().setEntityList(list);
    }

    @Override
    public void setEntity(MovementModel entity) {
        entity.setLoad(true);
        if(Movements.get().getEntity(entity.getId())!=null){
            Movements.get().replace(entity);
        }else {
            Movements.get().addEntity(entity);
        }
    }

    @Override
    public void deleteEntity(int id) {
        Movements.get().deleteEntity(id);
    }
}
