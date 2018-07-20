package Service;

public class ResourceModel {

    private  String mURL;
    private  String mId;

    public ResourceModel(String URL, String id) {
        mURL = URL;
        mId = id;
    }

    public  String getURL() {
        return mURL;
    }

    public  String getId() {
        return mId;
    }
}
