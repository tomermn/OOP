/* package */
package image;

/* imports */
import java.awt.*;
import java.util.Arrays;

/**
 * ImageUtils provides utility methods for image processing used in the AsciiArtAlgorithm.
 * Functions include padding and resizing, splitting images, calculating brightness,
 * and extracting sub-image pixels.
 *
 * @see ascii_art.AsciiArtAlgorithm
 */
public class ImageUtils {
    private static final double RED_RATIO =  0.2126;
    private static final double GREEN_RATIO = 0.7152;
    private static final double BLUE_RATIO = 0.0722;
    private static final int MAX_RGB = 255;
    /**
     * Pads and resizes the input image to the closest power of two dimensions.
     *
     * @param image The input image to be padded and resized.
     * @return A new Image object with padded and resized dimensions.
     */
    public static Image padAndResizeImage (Image image) {
        int newWidth = findClosestPowerOfTwo(image.getWidth());
        int newHeight = findClosestPowerOfTwo(image.getHeight());

        Color[][] paddedPixels = new Color[newHeight][newWidth];
        for (Color[] row : paddedPixels) {
            Arrays.fill(row, Color.WHITE);
        }
        return createPaddedImage(newWidth, newHeight, image, paddedPixels);
    }

    /**
     * Splits the input image into a 2D array of sub-images based on the specified pixel resolution.
     *
     * @param pixelResolution The resolution used for dividing the image into sub-images.
     * @param image The input image to be split.
     * @return A 2D array of sub-images.
     */
    public static Image[][] splitImage(int pixelResolution, Image image){
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int subImageSize = image.getWidth() / pixelResolution;
        Image [][] subImageList = new Image[imageHeight/subImageSize][pixelResolution];
        int placeInRow = 0;
        int placeInCol = 0;
        for (int i = 0; i < imageHeight ; i+=subImageSize) {
            for (int j = 0; j < imageWidth; j+=subImageSize) {
                Image subImagePixel = extractSubImagePixels(subImageSize,i,j,image);
                subImageList[placeInRow][placeInCol] = subImagePixel;
                placeInCol++;
            }
            placeInCol = 0;
            placeInRow ++;
        }
        return subImageList;
    }

    /**
     * Calculates the average brightness of the pixels in the input image.
     *
     * @param image The input image for calculating brightness.
     * @return The average brightness value normalized to the range [0, 1].
     */
    public static double calculateImageBrightness(Image image){
        double totalGreyPixel = 0;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j <imageWidth ; j++) {
                Color pixel = image.getPixel(i,j);
                double greyPixel = ((pixel.getRed() * RED_RATIO) + (pixel.getGreen() * GREEN_RATIO)
                        + (pixel.getBlue() * BLUE_RATIO));
                totalGreyPixel += greyPixel;
            }
        }
        int totalPixels = imageHeight * imageWidth;
        return totalGreyPixel / (totalPixels * MAX_RGB);
    }

    /*
     * private constructor to prevent instantiation
     */
    private ImageUtils(){}

    /*
     * Creates a new Image object with padded and resized dimensions based on the specified parameters.
     */
    private static Image createPaddedImage(int newWidth, int newHeight, Image image, Color[][] paddedPixels) {
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int widthPaddingOffset = (newWidth - originalWidth) / 2;
        int heightPaddingOffset = (newHeight - originalHeight) / 2;

        for (int row = 0; row < originalHeight; row++) {
            for (int col = 0; col < originalWidth; col++) {
                paddedPixels[row + heightPaddingOffset][col + widthPaddingOffset] = image.getPixel(row, col);
            }
        }
        return new Image(paddedPixels, newWidth, newHeight);
    }

    /*
     * Creates a 2D array of Image objects representing sub-images based on the specified parameters.
     */
    private static Image extractSubImagePixels(int imageSize,
                                               int rowStartingPoint,
                                               int colStartingPoint,
                                               Image image){
        Color[][] imagePixel = new Color[imageSize][imageSize];
        for (int i = 0; i < imageSize ; i++) {
            for (int j = 0; j < imageSize ; j++) {
                imagePixel[i][j] = image.getPixel(i + rowStartingPoint,j + colStartingPoint);
            }
        }
        return new Image(imagePixel, imageSize,imageSize);
    }

    /*
     * Checks if a given number is a power of two.
     */
    private static boolean isPowerOfTwo(int number) {
        return ((number & (number - 1)) == 0);
    }

    /*
     * Finds the closest power of two greater than a given number.
     */
    private static int findClosestPowerOfTwo(int number) {
        if (isPowerOfTwo(number)) {
            return number;
        }
        int power = 1;
        while (power < number) {
            power <<= 1;
        }
        return power;
    }

}
