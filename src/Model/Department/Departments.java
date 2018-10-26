package Model.Department;

import Iteractor.IteractorDepartment;
import Model.Equipment.EquipmentInventoryModel;
import Model.GenericList;
import javafx.collections.ObservableList;

public class Departments extends GenericList<DepartmentModel> {

    private static Departments sDepartments;

    public static Departments get(){
        if(sDepartments==null){
            sDepartments=new Departments();
        }
        return sDepartments;
    }

    @Override
    public ObservableList<DepartmentModel> getObsEntityList() {
        new IteractorDepartment().loadData();
        return super.getObsEntityList();
    }

    @Override
    public void update() {
        clear();
        new IteractorDepartment().loadData();
    }

    @Override
    public void replace(DepartmentModel entity) {
        DepartmentModel department=Departments.get().getEntity(entity.getId());
        department.setLoad(entity.isLoad());
        department.setNumber(entity.getNumber());
        department.setName(entity.getName());
        department.setAreaModel(entity.getAreaModel());
       // department.setConfig(entity.getConfig());
        department.setDescription(entity.getDescription());
        //department.setDocumentation(entity.getDocumentation());
        department.setLocationList(entity.getLocationList());
        //department.setPhotos(entity.getPhotos());
        department.setRenting(entity.isRenting());
        department.setWorkerList(entity.getWorkerList());
        department.setElectronicQ(entity.isElectronicQ());
        department.setEquipmentList(entity.getEquipmentList());
        department.setEntityList(entity.getEntityList());
        for (EquipmentInventoryModel equipment : department.getEquipmentList()) {
            equipment.setDepartmentModel(department);
        }
    }
}
