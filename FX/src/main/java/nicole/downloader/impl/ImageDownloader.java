package nicole.downloader.impl;

import javaxt.io.Image;
import nicole.downloader.Downloader;

/**
 * Program: FX
 * CreatedBy: Nicole_Z
 * CreatDate: 12/18/2020
 * Description:
 **/

public class ImageDownloader implements Downloader {

    private final Image image;

    public ImageDownloader(Image image) {
        this.image = image;
    }

    @Override
    public void download(String outputPath) {
        image.saveAs(outputPath);
    }
}
