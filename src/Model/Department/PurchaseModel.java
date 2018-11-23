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

    public PurchaseModel(int id, String name) {
        super(id, name);
    }

    public PurchaseModel() {
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

    @Override
    public AbstractModel<?> create(String name) {
        return new PurchaseModel(-1, name);
    }
}
