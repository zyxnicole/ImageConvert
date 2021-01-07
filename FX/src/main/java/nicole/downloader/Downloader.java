package nicole.downloader;

import java.io.FileNotFoundException;

/**
 * Program: FX
 * Description:
 * CreatedBy: Nicole_Z
 * CreatDate: 12/18/2020
 **/

public interface Downloader {
    void download(String outputPath) throws FileNotFoundException;
}
