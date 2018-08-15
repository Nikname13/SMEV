package Model.Worker;

import Iteractor.IteractorWorker;
import Model.GenericList;

public class Workers extends GenericList<WorkerModel> {

    private static Workers sWorkers;

    public static Workers get(){
        if(sWorkers==null) {
            sWorkers = new Workers();
            new IteractorWorker().loadData();
        }
        return sWorkers;
    }

    @Override
    public void update() {
        clear();
        new IteractorWorker().loadData();
    }

    @Override
    public void replace(WorkerModel entity) {
        WorkerModel worker=Workers.get().getEntity(entity.getId());
        worker.setName(entity.getName());
        worker.setPost(entity.getPost());
        worker.setDepartmentModel(entity.getDepartmentModel());
    }
}
