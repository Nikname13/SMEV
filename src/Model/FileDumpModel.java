package Model;

public class FileDumpModel extends AbstractModel {

    private String mPath;

    public FileDumpModel(int id, String name) {
        super(id, name);
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    @Override
    public String toString() {
        return "id= "+getId()+" name= "+getName()+" path= "+getPath();
    }
}
