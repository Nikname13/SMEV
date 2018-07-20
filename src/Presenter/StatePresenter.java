package Presenter;

import Iteractor.IteractorState;
import Model.State.StateModel;
import Model.State.States;
import javafx.collections.ObservableList;

import java.util.Set;

public class StatePresenter {

    private static StateModel mState;

    public void setState(Object state){
        this.mState=(StateModel)state;
    }

    public static StateModel getState() {
        return mState;
    }

    public ObservableList<StateModel> getObservableState(){
        return States.get().getEntityList();
    }

    public void addState(String name){
        new IteractorState().addNew(new StateModel(0,name));
    }

    public void editState(String name){
        new IteractorState().edit(new StateModel(mState.getId(),name));
    }

    public void deleteState(int id){
        new IteractorState().delete(id);
    }

    public void deleteState(Set<Integer> id){
        //new IteractorState().delete(state);
    }

    public void update(){
        States.get().update();
    }
}
