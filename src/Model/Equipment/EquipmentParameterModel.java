package Model.Equipment;

import Model.AbstractModel;
import Model.Parameter.ParameterModel;

public class EquipmentParameterModel extends AbstractModel {

    private ParameterModel mParameterModel;

    public EquipmentParameterModel(int id, String value, ParameterModel parameter) {
        super(id,value);
        mParameterModel=parameter;
    }

    public EquipmentParameterModel(int id, String name) {
        super(id, name);
    }

    public EquipmentParameterModel(int id) {
        super(id);
    }

    public EquipmentParameterModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new EquipmentParameterModel(-1, name);
    }

    public ParameterModel getParameterModel() {
        return mParameterModel;
    }

    public void setParameterModel(ParameterModel parameterModel) {
        mParameterModel = parameterModel;
    }
}
