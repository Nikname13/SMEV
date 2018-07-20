package Model.Equipment;

import Model.AbstractModel;
import Model.Parameter.ParameterModel;

public class EquipmentParameterModel extends AbstractModel {

    private ParameterModel mParameterModel;

    public EquipmentParameterModel(int id, String value, ParameterModel parameter) {
        super(id,value);
        mParameterModel=parameter;
    }

    public ParameterModel getParameterModel() {
        return mParameterModel;
    }

    public void setParameterModel(ParameterModel parameterModel) {
        mParameterModel = parameterModel;
    }
}
