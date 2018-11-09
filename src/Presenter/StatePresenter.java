package Presenter;

import Iteractor.IteractorState;
import Model.State.StateModel;
import Service.ListenersService;

public class StatePresenter extends BasePresenter {

    private static StateModel sStateModel;
    private static StatePresenter sStatePresenter;

    private StatePresenter() {
        ListenersService.get().addListenerData(this);
    }

    public static StatePresenter get() {
        if (sStatePresenter == null) {
            sStatePresenter = new StatePresenter();
        }
        return sStatePresenter;
    }

    public StateModel getState() {
        return sStateModel;
    }

    public void setState(StateModel state) {
        sStateModel = state;
        setSelectedObject(state);
    }

    public void addState(String name){
        new IteractorState().addNew(new StateModel(0,name));
        ListenersService.get().updateControl(StateModel.class);
    }

    public void editState(String name){
        new IteractorState().edit(new StateModel(sStateModel.getId(), name));
    }

    public void editState(StateModel state) {
        ListenersService.get().updateData(new IteractorState().edit(state));
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sStateModel)) {
                if (new IteractorState().delete(sStateModel.getId())) {
                    ListenersService.get().updateControl(StateModel.class);
                }
            }
        }
    }
}
