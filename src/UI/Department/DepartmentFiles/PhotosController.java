package UI.Department.DepartmentFiles;

import Model.Department.DepartmentModel;
import Model.FileDumpModel;
import Presenter.DepartmentPresenter;
import Presenter.FileDumpPresenter;
import UI.BaseFileController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PhotosController extends BaseFileController {

    private DepartmentModel mDepartmentModel;
    @FXML
    private AnchorPane mPanePhoto;

    public PhotosController() {
        mDepartmentModel = DepartmentPresenter.get().getDepartmentModel();
        setTypeDocument(DepartmentPresenter.get().getTypeDocuments());
    }

    @FXML
    public void initialize() {

    }

    private ImageView createImageView(File imageFile) {
        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                        if (mouseEvent.getClickCount() == 2) {
                            try {
                                BorderPane borderPane = new BorderPane();
                                ImageView imageView = new ImageView();
                                Image image = new Image(new FileInputStream(imageFile));
                                imageView.setImage(image);
                                imageView.setStyle("-fx-background-color: BLACK");
                                imageView.setFitHeight(390);
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);
                                borderPane.setCenter(imageView);
                                borderPane.setStyle("-fx-background-color: BLACK");
                                Stage newStage = new Stage();
                                newStage.setWidth(600);
                                newStage.setHeight(400);
                                newStage.setTitle(imageFile.getName());
                                Scene scene = new Scene(borderPane, Color.BLACK);
                                newStage.setScene(scene);
                                newStage.show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }

    @Override
    public void createGallery() {
        ScrollPane root = new ScrollPane();
        TilePane tile = new TilePane();
        // root.setStyle("-fx-background-color: DAE6F3;");
        tile.setPadding(new Insets(30, 15, 15, 15));
        tile.setHgap(15);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        root.setFitToWidth(true);
        root.setContent(tile);

        for (FileDumpModel file : mDepartmentModel.getFilesList(getTypeDocument())) {
            ImageView imageView;
            FileDumpPresenter.get().setFileDumpModel(file);
            imageView = createImageView(DepartmentPresenter.get().getTempFile(file.getPath()));
            Pane pane = new Pane();
            pane.getChildren().add(imageView);
            pane.setStyle("-fx-background-color: #ffffff;");
            tile.getChildren().addAll(pane);
        }
        getStage().setWidth(600);
        getStage().setHeight(400);
        Scene scene = new Scene(root);
        getStage().setScene(scene);
        getStage().show();
    }

    @Override
    protected Stage getStage() {
        return (Stage) mPanePhoto.getScene().getWindow();
    }

    @Override
    public void primaryClick(String id) {

    }
}
