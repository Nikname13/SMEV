package Model.Parameter;

import Model.AbstractModel;

public class ValueParameterModel extends AbstractModel {

    public ValueParameterModel(int id, String name) {
        super(id,name);
    }

    public ValueParameterModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new ValueParameterModel(-1, name);
    }
}
