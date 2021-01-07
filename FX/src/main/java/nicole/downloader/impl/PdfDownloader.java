package nicole.downloader.impl;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import javaxt.io.Image;
import nicole.downloader.Downloader;

import java.io.FileNotFoundException;

/**
 * Program: FX
 * CreatedBy: Nicole_Z
 * CreatDate: 12/18/2020
 * Description:
 **/

public class PdfDownloader implements Downloader {

    private final Image image;

    public PdfDownloader(Image image) {
        this.image = image;
    }

    @Override
    public void download(String outputPath) throws FileNotFoundException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputPath));
        Document document = new Document(pdfDocument);
        ImageData imageData = ImageDataFactory.create(image.getByteArray());
        com.itextpdf.layout.element.Image pdfImage = new com.itextpdf.layout.element.Image(imageData);
        pdfImage.setWidth(pdfDocument.getDefaultPageSize().getWidth() - 50);
        pdfImage.setAutoScaleHeight(true);
        document.add(pdfImage);
        pdfDocument.close();
    }
}
