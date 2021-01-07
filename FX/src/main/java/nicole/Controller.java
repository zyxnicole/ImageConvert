package nicole;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javaxt.io.Image;
import nicole.downloader.Downloader;
import nicole.downloader.DownloaderFactory;
import nicole.filter.AbstractFilter;
import nicole.filter.InvertFilter;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


/**
 * Program: FX
 * Description:
 * CreatedBy: Nicole_Z
 * CreatDate: 11/19/2020
 **/

public class Controller implements Initializable {
    private static final ObservableList<String> FORMAT_LIST =
            FXCollections.observableArrayList(".PDF", ".JPG", ".PNG", ".GIF", ".TIFF");

    @FXML private ComboBox<String> formatChoice;
    @FXML private ListView listView;
    @FXML private ImageView imageView;
    @FXML Label chooseFormatLabel;
    @FXML Label savedLabel;
    @FXML Group rotateButton;
    @FXML Group filterButton;

    private Image image;
    private String format;
    private boolean labelVisible;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formatChoice.setItems(FORMAT_LIST);
    }

    @SuppressWarnings({"unchecked", "rawtype"})
    public void openFileAction(MouseEvent mouseEvent) {
        Scene scene = imageView.getScene();
        if (labelVisible) {
            labelVisible = false;
            scene.lookup("#savedLabel").setVisible(labelVisible);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        File inputFile = fileChooser.showOpenDialog(null);

        image = new Image(inputFile);
        imageView.setImage(SwingFXUtils.toFXImage(image.getBufferedImage(),null));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        listView.getItems().clear();

        //Open Image and Get EXIF Metadata
        HashMap<Integer, Object> exif = image.getExifTags();
        ObservableList<String> listViewItems = listView.getItems();
        listViewItems.add("Image Height: " + image.getHeight());
        listViewItems.add("Image Width: " + image.getWidth());
        if (exif.get(0x0132) != null) {
            listViewItems.add("Date: " + exif.get(0x0132));
        }
        if (exif.get(0x0110) != null) {
            listViewItems.add("Camera: " + exif.get(0x0110));
        }
        if (exif.get(0x010F) != null) {
            listViewItems.add("Manufacturer: " + exif.get(0x010F));
        }
        if (exif.get(0x920A) != null) {
            listViewItems.add("Focal Length: " + exif.get(0x920A));
        }
        if (exif.get(0x9202) != null) {
            listViewItems.add("Aperture Value (APEX): " + exif.get(0x9202));
        }

        double[] coord = image.getGPSCoordinate();
        if (coord!=null){
            listViewItems.add("GPS Coordinate: " +
                    String.format("%.8f",coord[0]) + ", " + String.format("%.8f",coord[1]));
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        scene.lookup("#formatChoice").setVisible(true);
        scene.lookup("#listView").setVisible(true);
        scene.lookup("#rotateButton").setVisible(true);
        scene.lookup("#filterButton").setVisible(true);
        
    }

    public void downloadAction(MouseEvent mouseEvent) {
        if (image == null) {
            return;
        }

        Scene scene = imageView.getScene();
        scene.lookup("#chooseFormatLabel").setVisible(format == null);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(format, format));
        javafx.scene.image.Image snapshot = imageView.getImage();
        image = new Image(SwingFXUtils.fromFXImage(snapshot,image.getBufferedImage()));

        try {
            File outputFile = fileChooser.showSaveDialog(null);
            fileChooser.setInitialDirectory(outputFile.getParentFile());
            String newPath = outputFile + format;
            Downloader downloader = new DownloaderFactory(image).create(format);
            downloader.download(newPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        labelVisible = true;
        scene.lookup("#savedLabel").setVisible(labelVisible);
    }


    public void chooseImageFormat(ActionEvent actionEvent) {
        format = formatChoice.getValue();
    }

    public void rotateAction(MouseEvent mouseEvent) {
        image.rotateCounterClockwise();
        imageView.setImage(SwingFXUtils.toFXImage(image.getBufferedImage(),null));

    }

    public void filterAction(MouseEvent mouseEvent) {
        AbstractFilter filter = new InvertFilter();
        imageView.setImage(filter.filter(imageView.getImage()));
    }
}
