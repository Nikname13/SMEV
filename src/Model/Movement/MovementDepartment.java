package Model.Movement;

import Model.Department.DepartmentModel;

public class MovementDepartment extends DepartmentModel {

    private int mDepartmentId;

    public MovementDepartment(int id, String name, String number, int departmentId) {
        super(id, name, number);
        mDepartmentId = departmentId;
    }

    public int getDepartmentId() {
        return mDepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        mDepartmentId = departmentId;
    }
}
