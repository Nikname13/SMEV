package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public interface IAbstractModel {

    IntegerProperty idProperty();
    StringProperty nameProperty();

    ObjectProperty<LocalDate> dateProperty();

    StringProperty dateToString();

}
