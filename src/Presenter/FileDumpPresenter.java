package Presenter;

import Model.FileDumpModel;

public class FileDumpPresenter extends BasePresenter {

    private static FileDumpPresenter sFileDumpPresenter;
    private static FileDumpModel sFileDumpModel;

    private FileDumpPresenter() {
    }

    public static FileDumpPresenter get() {
        if (sFileDumpPresenter == null) sFileDumpPresenter = new FileDumpPresenter();
        return sFileDumpPresenter;
    }

    public FileDumpModel getFileDumpModel() {
        return sFileDumpModel;
    }

    public void setFileDumpModel(FileDumpModel fileDumpModel) {
        sFileDumpModel = fileDumpModel;
        setSelectedObject(fileDumpModel);
    }
}
