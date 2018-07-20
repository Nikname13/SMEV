package Model;

public class TreeViewModel <T extends TreeViewModel<?>> {

    private String mName;
    private int mId;

    public TreeViewModel(String name, int id) {
        mName = name;
        mId=id;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
