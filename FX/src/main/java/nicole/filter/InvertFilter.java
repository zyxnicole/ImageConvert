package nicole.filter;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Program: FX
 * CreatedBy: Nicole_Z
 * CreatDate: 12/18/2020
 * Description:
 **/

public class InvertFilter extends AbstractFilter {
    @Override
    public Image filter(Image image) {
        return invert(image);
    }

    private Image invert(Image image) {
        int w = (int) image.getWidth();
        int h = (int) image.getHeight();

        WritableImage writableImage = new WritableImage(w,h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color c = image.getPixelReader().getColor(i, j).invert();
                writableImage.getPixelWriter().setColor(i, j, c);
            }
        }
        return writableImage;
    }
}
