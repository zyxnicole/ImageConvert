package nicole.downloader;

import javaxt.io.Image;
import nicole.downloader.impl.ImageDownloader;
import nicole.downloader.impl.PdfDownloader;

/**
 * Program: FX
 * CreatedBy: Nicole_Z
 * CreatDate: 12/18/2020
 * Description:
 **/

public class DownloaderFactory {

    private final Image image;

    public DownloaderFactory(Image image) {
        this.image = image;
    }

    public Downloader create(String format) {
        if (format.equals(".PDF")) {
            return new PdfDownloader(image);
        }
        return new ImageDownloader(image);
    }
}
