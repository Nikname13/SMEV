package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface IAbstractModel {

    IntegerProperty idProperty();
    StringProperty nameProperty();

}
