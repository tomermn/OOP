package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
public class Image {
    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    /**
     * Constructs an Image object from a file.
     *
     * @param filename The name of the file containing the image.
     * @throws IOException If an I/O error occurs during reading the image file.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();
        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j]=new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructs an Image object from a Color array with specified width and height.
     *
     * @param pixelArray The 2D array of Color objects representing the pixels of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the image.
     *
     * @return The width of the image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image.
     *
     * @return The height of the image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets a Color in a specific pixel in the image.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The Color in the specified pixel.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the image to a file in JPEG format.
     *
     * @param fileName The name of the output file (without extension).
     */
    public void saveImage(String fileName){
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName+".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
