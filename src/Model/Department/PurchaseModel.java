package Model.Department;

import Model.AbstractModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class PurchaseModel extends AbstractModel {

    private String mDescription;
    private LocalDate mDate;
    private DepartmentModel mDepratment;

    public PurchaseModel(int id, String URL, String description,LocalDate date) {
        super(id,URL);
        mDescription = description;
        mDate = date;
    }

    public PurchaseModel(int id, String URL, String description, LocalDate date, DepartmentModel department) {
        super(id,URL);
        mDescription = description;
        mDate = date;
        setDepratment(department);
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate date) {
        mDate = date;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return new SimpleObjectProperty<>(mDate);
    }

    public DepartmentModel getDepratment() {
        return mDepratment;
    }

    public void setDepratment(DepartmentModel depratment) {
        mDepratment = depratment;
    }
}
