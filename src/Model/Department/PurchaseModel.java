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
    private DepartmentModel mDepartment;

    public PurchaseModel(int id, String URL, String description,LocalDate date) {
        super(id,URL);
        mDescription = description;
        mDate = date;
    }

    public PurchaseModel(int id, String URL, String description, LocalDate date, DepartmentModel department) {
        super(id,URL);
        mDescription = description;
        mDate = date;
        setDepartment(department);
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

    public DepartmentModel getDepartment() {
        return mDepartment;
    }

    public void setDepartment(DepartmentModel department) {
        mDepartment = department;
    }
}
