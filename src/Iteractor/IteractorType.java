package Iteractor;

import Model.Type.TypeModel;
import Model.Type.Types;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class IteractorType extends GenericIteractor<TypeModel>  {

    private static String sURI="/type_servlet";

    public IteractorType() {
        super(sURI, TypeModel.class, new TypeToken<ArrayList<TypeModel>>(){}.getType());
    }

    @Override
    public void setList(ObservableList<TypeModel> list) {
        Types.get().setEntityList(list);
    }

    @Override
    public void setEntity(TypeModel entity) {
        entity.setLoad(true);
        if(Types.get().getEntity(entity.getId()) != null){
            Types.get().replace(entity);
        }else{
            Types.get().addEntity(entity);
        }
    }

    @Override
    public void deleteEntity(int id) {
        Types.get().deleteEntity(id);
    }

}
