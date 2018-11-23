package Model;

public class FileDumpModel extends AbstractModel {

    private String mPath;
    private transient String mTempPath;

    public FileDumpModel(int id, String name) {
        super(id, name);
    }

    public FileDumpModel() {
    }

    @Override
    public AbstractModel<?> create(String name) {
        return new FileDumpModel(-1, name);
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getTempPath() {
        return mTempPath;
    }

    public void setTempPath(String tempPath) {
        mTempPath = tempPath;
    }

    @Override
    public String toString() {
        return "id= "+getId()+" name= "+getName()+" path= "+getPath();
    }
}
