package Model.Movement;

import Model.Worker.WorkerModel;

public class MovementWorker extends WorkerModel {

    private String mWorkerPost;
    private int mWorkerId;

    public MovementWorker(int id, String name, String post, int workerId) {
        super(id, name);
        mWorkerPost = post;
        mWorkerId = workerId;
    }


    public String getWorkerPost() {
        return mWorkerPost;
    }

    public void setWorkerPost(String workerPost) {
        mWorkerPost = workerPost;
    }

    public int getWorkerId() {
        return mWorkerId;
    }

    public void setWorkerId(int workerId) {
        mWorkerId = workerId;
    }
}
