package Model.Area;

import Model.AbstractModel;

public class AreaModel extends AbstractModel {

    public AreaModel(int id, String name) {
        super(id,name);
    }

    public AreaModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new AreaModel(-1, name);
    }
}
