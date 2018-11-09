package Presenter;

import Model.FileDumpModel;

public class FileDumpPresenter extends BasePresenter {

    private static FileDumpPresenter sFileDumpPresenter;
    private FileDumpModel mFileDumpModel;

    private FileDumpPresenter() {
    }

    public static FileDumpPresenter get() {
        if (sFileDumpPresenter == null) sFileDumpPresenter = new FileDumpPresenter();
        return sFileDumpPresenter;
    }

    public FileDumpModel getFileDumpModel() {
        return mFileDumpModel;
    }

    public void setFileDumpModel(FileDumpModel fileDumpModel) {
        mFileDumpModel = fileDumpModel;
        setSelectedObject(fileDumpModel);
    }
}
