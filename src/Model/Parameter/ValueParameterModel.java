package Model.Parameter;

import Model.AbstractModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ValueParameterModel extends AbstractModel {

    public ValueParameterModel(int id, String name) {
        super(id,name);
    }
}
