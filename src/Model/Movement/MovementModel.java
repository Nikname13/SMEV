package Model.Movement;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.GenericModel;
import Model.Worker.WorkerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovementModel extends GenericModel<MovementEquipment> {

    private List<MovementWorker> mWorkerList;
    private List<MovementDepartment> mDepartmentList;

    public MovementModel(int id, LocalDate date, String base){
        super(id, base, date);
    }

    public MovementModel(int id, LocalDate date, String base, List<MovementEquipment> equipmentList, List<MovementWorker> workerList, List<MovementDepartment> departmentList) {
        super(id, base, equipmentList, date);
        setWorkerList(workerList);
        setDepartmentList(departmentList);
    }

    public List<MovementWorker> getWorkerList() {
        return mWorkerList;
    }

    public void setWorkerList(List<MovementWorker> workerList) {
        mWorkerList = workerList;
    }

    public void addWorker(MovementWorker worker) {
        if(mWorkerList==null) mWorkerList=new ArrayList<>();
        mWorkerList.add(worker);
    }

    public ObservableList<WorkerModel> getObsWorkerList(){
        if(mWorkerList==null) getWorkerList();
        ObservableList<WorkerModel> workerList=FXCollections.observableArrayList();
        for(WorkerModel worker : mWorkerList){
            workerList.add(worker);
        }
        return workerList;
    }

    public List<MovementDepartment> getDepartmentList() {
        return mDepartmentList;
    }

    public void setDepartmentList(List<MovementDepartment> departmentList) {
        mDepartmentList = departmentList;
    }

    public void addDepartment(MovementDepartment department) {
        if(mDepartmentList==null) mDepartmentList=new ArrayList<>();
        mDepartmentList.add(department);
    }

    public ObservableList<MovementDepartment> getObsDepartmentList() {
        ObservableList<MovementDepartment> departmentList = FXCollections.observableArrayList();
        for (MovementDepartment department : getDepartmentList()) {
            departmentList.add(department);
        }
        return departmentList;
    }

    public MovementDepartment newMovementDepartment(DepartmentModel department) {
        return new MovementDepartment(0, department.getName(), department.getNumber(), department.getId());
    }

    public MovementWorker newMovementWorker(WorkerModel worker) {
        return new MovementWorker(0, worker.getName(), worker.getPost().getName(), worker.getId());
    }

    public MovementEquipment newMovementEquipment(EquipmentInventoryModel equipment) {
        return new MovementEquipment(0, equipment.getEquipmentModel().getNameFact(), equipment.getInventoryNumber().getName(),
                equipment.getEquipmentModel().getTypeModel().getName(), equipment.getId());
    }
}
