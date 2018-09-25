package Presenter;

import Iteractor.IteractorState;
import Model.State.StateModel;
import Model.State.States;

import java.util.Set;

public class StatePresenter extends BasePresenter {

    private static StateModel mState;

    public void setState(Object state){
        mState = (StateModel) state;
    }

    public static StateModel getState() {
        return mState;
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

    @Override
    void loadEntity(int id) {

    }
}
