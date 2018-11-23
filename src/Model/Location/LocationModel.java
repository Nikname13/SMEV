package Model.Location;

import Model.AbstractModel;
import Model.Department.DepartmentModel;

import java.util.ArrayList;
import java.util.List;

public class LocationModel extends AbstractModel {

    private List<DepartmentModel> mDepartmentList;

    public LocationModel(int id, String name) {
        super(id, name);
    }

    public LocationModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new LocationModel(-1, name);
    }

    public LocationModel(int id, String name, DepartmentModel department) {
        super(id, name);
        addLocation(department);
    }

    public List<DepartmentModel> getDepartmentList() {
        return mDepartmentList;
    }

    public void setDepartmentList(List<DepartmentModel> departmentList) {
        mDepartmentList = departmentList;
    }

    public void addLocation(DepartmentModel department){
        if (mDepartmentList == null) mDepartmentList = new ArrayList<>();
        mDepartmentList.add(department);
    }
}
