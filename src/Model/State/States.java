package Model.State;

import Iteractor.IteractorState;
import Model.GenericList;
import javafx.collections.ObservableList;

public class States extends GenericList<StateModel> {

    private static States sState;

    public static States get() {
        if(sState==null) {
            sState = new States();
        }
        return sState;
    }

    @Override
    public ObservableList<StateModel> getObsEntityList() {
        new IteractorState().loadData();
        return super.getObsEntityList();
    }

    @Override
    public void update() {
        clear();
        new IteractorState().loadData();
    }

    @Override
    public void replace(StateModel entity) {
        StateModel state= States.get().getEntity(entity.getId());
        state.setName(entity.getName());
    }
}
