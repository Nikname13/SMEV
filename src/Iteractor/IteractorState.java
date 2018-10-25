package Iteractor;

import Model.State.StateModel;
import Model.State.States;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

public class IteractorState extends GenericIteractor<StateModel>  {

    private static String sURL="/state_servlet";

    public IteractorState() {
        super(sURL, StateModel.class, new TypeToken<ArrayList<StateModel>>(){}.getType());
    }

    @Override
    public void setList(ObservableList<StateModel> list) {
        States.get().setEntityList(list, Comparator.comparing(StateModel::getNameToLowerCase));
    }

    @Override
    public void setEntity(StateModel entity) {
        if(States.get().getEntity(entity.getId()) != null){
            States.get().replace(entity);
        }else{
            States.get().addEntity(entity, Comparator.comparing(StateModel::getNameToLowerCase));
        }
    }

    @Override
    public void deleteEntity(int id) {
        //States.get().deleteEntity(id);
    }
}
