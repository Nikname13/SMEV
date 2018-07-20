package Model.Worker;

import Model.AbstractModel;
import Model.Department.DepartmentModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkerModel extends AbstractModel {

    private String mPost;
    private DepartmentModel mDepartmentModel;

    public WorkerModel(int id, String name, String post, DepartmentModel department) {
        super(id,name);
        mPost = post;
        setDepartmentModel(department);
    }

    public String getPost() {
        return mPost;
    }

    public void setPost(String post) {
        mPost = post;
    }

    public StringProperty postProperty() {
        return new SimpleStringProperty(mPost);
    }

    public DepartmentModel getDepartmentModel() {
        return mDepartmentModel;
    }

    public void setDepartmentModel(DepartmentModel departmentModel) {
        mDepartmentModel = departmentModel;
    }
}
