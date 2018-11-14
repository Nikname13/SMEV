package Presenter;

import Model.FileDumpModel;
import Service.ErrorService;
import Service.ListenersService;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class BaseFilePresenter extends BasePresenter {

    protected List<File> uploadDocFiles(Stage stage) {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Word doc (*.doc)", "*.doc"),
                new FileChooser.ExtensionFilter("Open Document (*.odt)", "*.odt")
        );
        return fileChooser.showOpenMultipleDialog(stage);//Указываем текущую сцену CodeNote.mainStage
    }

    protected File saveFile(FileDumpModel file, Stage stage) {
        String fileName = file.getName();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        fileChooser.setInitialFileName(file.getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*" + fileName.substring(fileName.length() - 4, fileName.length())));
        return fileChooser.showSaveDialog(stage.getScene().getWindow());

    }

    protected void openFile(String path) {
        try {
            File savePathFile = File.createTempFile(path.substring(0, path.lastIndexOf(".")), path.substring(path.lastIndexOf(".")));
            System.out.println("path " + path + " File save path " + savePathFile.getPath());
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            getFile(savePathFile);
            desktop.open(savePathFile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
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
        List<File> fileList = uploadDocFiles(stage);
        if (fileList != null) {
            System.out.println("Процесс открытия файла");
            uploadFiles(fileList);
            ListenersService.get().updateControl(FileDumpModel.class);
            return true;
        }
        return false;
    }

    protected abstract void uploadFiles(List<File> fileList);

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
