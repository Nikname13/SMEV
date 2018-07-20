package Model.State;

import Iteractor.IteractorState;
import Model.GenericList;
import Model.Type.TypeModel;
import Model.Type.Types;

public class States extends GenericList<StateModel> {

    private static States sState;

    public static States get() {
        if(sState==null) {
            sState = new States();
            new IteractorState().loadData();
        }
        return sState;
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
