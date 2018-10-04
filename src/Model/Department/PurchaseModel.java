package Model.Department;

import Model.AbstractModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class PurchaseModel extends AbstractModel {

    private String mDescription;
    private DepartmentModel mDepartment;

    public PurchaseModel(int id, String URL, String description,LocalDate date) {
        super(id, URL, date);
        mDescription = description;
    }

    public PurchaseModel(int id, String URL, String description, LocalDate date, DepartmentModel department) {
        super(id, URL, date);
        mDescription = description;
        setDepartment(department);
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public DepartmentModel getDepartment() {
        return mDepartment;
    }

    public void setDepartment(DepartmentModel department) {
        mDepartment = department;
    }
}
