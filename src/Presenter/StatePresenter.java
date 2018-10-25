package Presenter;

import Iteractor.IteractorState;
import Model.State.StateModel;
import Model.State.States;
import Service.ListenersService;

import java.util.Set;

public class StatePresenter extends BasePresenter {

    private static StateModel mState;
    private static StatePresenter sStatePresenter;

    private StatePresenter() {

    }

    public static StatePresenter get() {
        if (sStatePresenter == null) {
            sStatePresenter = new StatePresenter();
        }
        return sStatePresenter;
    }

    public void setState(Object state){
        mState = (StateModel) state;
    }

    public StateModel getState() {
        return mState;
    }

    public void addState(String name){
        new IteractorState().addNew(new StateModel(0,name));
    }

    public void editState(String name){
        new IteractorState().edit(new StateModel(mState.getId(),name));
    }

    public void editState(StateModel state) {
        ListenersService.get().updateData(new IteractorState().edit(state));
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
