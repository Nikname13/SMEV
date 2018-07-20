package Model.Movement;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.GenericModel;
import Model.Worker.WorkerModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovementModel extends GenericModel<EquipmentInventoryModel> {

    private LocalDate mDate;
    private List<WorkerModel> mWorkerList;
    private List<DepartmentModel> mDepartmentList;

    public MovementModel(int id, LocalDate date, String base){
        super(id, base);
        mDate = date;
    }

    public MovementModel(int id, LocalDate date, String base, List<EquipmentInventoryModel> equipmentList, List<WorkerModel> workerList, List<DepartmentModel> departmentList){
        super(id,base,equipmentList);
        mDate = date;
        setWorkerList(workerList);
        setDepartmentList(departmentList);
    }

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate date) {
        mDate = date;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return new SimpleObjectProperty<>(mDate);
    }

    public List<WorkerModel> getWorkerList() {
        return mWorkerList;
    }

    public void setWorkerList(List<WorkerModel> workerList) {
        mWorkerList = workerList;
    }

    public void addWorker(WorkerModel worker){
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

    public List<DepartmentModel> getDepartmentList() {
        return mDepartmentList;
    }

    public void setDepartmentList(List<DepartmentModel> departmentList) {
        mDepartmentList = departmentList;
    }

    public void addDepartment(DepartmentModel department){
        if(mDepartmentList==null) mDepartmentList=new ArrayList<>();
        mDepartmentList.add(department);
    }

    public ObservableList<DepartmentModel> getObsDepartmentList(){
        if(mDepartmentList==null) getDepartmentList();
        ObservableList<DepartmentModel> departmentList=FXCollections.observableArrayList();
        for(DepartmentModel department:mDepartmentList){
            departmentList.add(department);
        }
        return departmentList;
    }
}
