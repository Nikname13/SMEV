package Model.Location;

import Model.AbstractModel;
import Model.Department.DepartmentModel;

import java.util.ArrayList;
import java.util.List;

public class LocationModel extends AbstractModel {

    private List<DepartmentModel> mDepartmentModel;

    public LocationModel(int id, String name) {
        super(id, name);
    }

    public LocationModel(int id, String name, DepartmentModel department) {
        super(id, name);
        addLocation(department);
    }

    public List<DepartmentModel> getDepartmentModel() {
        return mDepartmentModel;
    }

    public void setDepartmentModel(List<DepartmentModel> departmentModel) {
        mDepartmentModel = departmentModel;
    }

    public void addLocation(DepartmentModel department){
        if(mDepartmentModel ==null) mDepartmentModel=new ArrayList<>();
        mDepartmentModel.add(department);
    }
}
