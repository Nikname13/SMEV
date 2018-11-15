package Presenter;

import Model.AbstractModel;
import Model.FileDumpModel;
import Service.ErrorService;
import Service.ListenersService;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class BaseFilePresenter extends BasePresenter {

    protected List<File> uploadDocFiles(Stage stage) {
        FileChooser fileChooser = getFileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Word doc (*.doc)", "*.doc"),
                new FileChooser.ExtensionFilter("Open Document (*.odt)", "*.odt")
        );
        return fileChooser.showOpenMultipleDialog(stage);//Указываем текущую сцену CodeNote.mainStage
    }

    protected List<File> uploadPhotoFiles(Stage stage) {
        FileChooser fileChooser = getFileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All images", "*.jpg*", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        return fileChooser.showOpenMultipleDialog(stage);//Указываем текущую сцену CodeNote.mainStage
    }

    private FileChooser getFileChooser() {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Выбрать файл");//Заголовок диалога
        return fileChooser;
    }


    protected File saveFile(FileDumpModel file, Stage stage) {
        String fileName = file.getName();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Сохранить файл");//Заголовок диалога
        fileChooser.setInitialFileName(file.getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*" + fileName.substring(fileName.length() - 4, fileName.length())));
        return fileChooser.showSaveDialog(stage.getScene().getWindow());

    }

    public File getTempFile(String savePath) {
        try {
            return getFile(File.createTempFile(savePath.substring(0, savePath.lastIndexOf(".")), savePath.substring(savePath.lastIndexOf("."))));
        } catch (IOException ex) {
            ErrorService.get().showError(ex.getMessage());
            return null;
        }
    }

    protected void openFile(String path) {
        try {
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            desktop.open(getTempFile(path));
        } catch (IOException ex) {
            ErrorService.get().showError(ex.getMessage());
        }

    }

    public void editFile(String name) {
        FileDumpModel editableFile = FileDumpPresenter.get().getFileDumpModel();
        editableFile.setName(name.concat(editableFile.getName().substring(editableFile.getName().lastIndexOf("."))));
        editFile(editableFile);
        ListenersService.get().updateControl(FileDumpModel.class);
    }

    protected abstract void editFile(FileDumpModel editableFile);

    public boolean uploadFiles(Stage stage) {
        List<File> fileList;
        if (getTypeDocuments().equals(AbstractModel.getTypePhoto())) {
            fileList = uploadPhotoFiles(stage);
        } else {
            fileList = uploadDocFiles(stage);
        }
        if (fileList != null) {
            System.out.println("Процесс открытия файла");
            uploadFiles(fileList);
            ListenersService.get().updateControl(FileDumpModel.class);
            return true;
        }
        return false;
    }

    public void uploadAvatar(Stage stage) {
        List<File> fileList = uploadPhotoFiles(stage);
        if (fileList != null) {
            setAvatar(Arrays.asList(fileList.get(0)));
            ListenersService.get().updateControl(FileDumpModel.class);
        }
    }

    protected void setAvatar(List<File> fileList) {
    }


    protected abstract List<FileDumpModel> uploadFiles(List<File> fileList);

    public void saveSelectedFile(Stage stage) {
        File savePathFile = saveFile(FileDumpPresenter.get().getFileDumpModel(), stage);
        if (savePathFile != null) {
            getFile(savePathFile);
        }
    }

    public void openSelectedFile() {
        openFile(FileDumpPresenter.get().getFileDumpModel().getPath());
    }

    protected abstract File getFile(File savePathFile);
}
