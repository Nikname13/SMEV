package Model.State;

import Model.AbstractModel;

public class StateModel extends AbstractModel {

    public StateModel(int id, String name) {
        super(id,name);
    }

    public StateModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new StateModel(-1, name);
    }
}
