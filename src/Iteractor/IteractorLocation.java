package Iteractor;

import Model.Location.LocationModel;
import Model.Location.Locations;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IteractorLocation extends GenericIteractor<LocationModel> {

    private static String sURL="/location_servlet";

    public IteractorLocation() {
        super(sURL, LocationModel.class, new TypeToken<ArrayList<LocationModel>>(){}.getType());
    }

    @Override
    public String getGson(LocationModel entity) {
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
    public void setList(ObservableList<LocationModel> list) {
        Collections.sort(list, Comparator.comparing(LocationModel::getNameToLowerCase));
        Locations.get().setEntityList(list);
    }

    @Override
    public void setEntity(LocationModel entity) {
        if(Locations.get().getEntity(entity.getId())!=null){
            Locations.get().replace(entity);
        }else {
            Locations.get().addEntity(entity);
        }
    }
}
