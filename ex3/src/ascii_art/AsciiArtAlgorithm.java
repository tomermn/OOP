/* package */
package ascii_art;

/* imports */
import image.Image;
import image.ImageUtils;
import image_char_matching.SubImgCharMatcher;

/**
 * AsciiArtAlgorithm converts an input Image into ASCII art using a specified pixel
 * resolution and character set.
 */
public class AsciiArtAlgorithm {
    private static Image image;
    private static int pixelResolution;
    private static char[] charset;
    private static double[][] subImagesBrightness;
    private static SubImgCharMatcher charMatcher;

    /**
     * Constructs AsciiArtAlgorithm with an Image, pixel resolution, and character set.
     *
     * @param image The input image.
     * @param pixelResolution The resolution for sub-images.
     * @param charset The character set for mapping brightness values.
     */
    public AsciiArtAlgorithm(Image image, int pixelResolution, char[] charset){
        if (toChangeImageProcessingParameters(image, pixelResolution)) {
            AsciiArtAlgorithm.image = image;
            AsciiArtAlgorithm.pixelResolution = pixelResolution;
            AsciiArtAlgorithm.subImagesBrightness = createSubImagesBrightness();
        }
        if (toChangeCharset(charset)) {
            AsciiArtAlgorithm.charset = charset;
            AsciiArtAlgorithm.charMatcher = new SubImgCharMatcher(charset);
        }
    }

    /**
     * Runs the ASCII art algorithm and returns the result as a 2D array of characters.
     *
     * @return A 2D array of characters representing the ASCII art of the input image.
     */
    public char[][] run(){
        char[][] fitChars = new char[subImagesBrightness.length][pixelResolution];
        for (int i = 0; i < fitChars.length; i++) {
            for (int j = 0; j < fitChars[i].length; j++) {
                fitChars[i][j] = charMatcher.getCharByImageBrightness(subImagesBrightness[i][j]);
            }
        }
        return fitChars;
    }

    /*
     * Checks if the image processing parameters need to be updated.
     */
    private boolean toChangeImageProcessingParameters(Image image, int pixelResolution) {
        if (AsciiArtAlgorithm.image == null ||  AsciiArtAlgorithm.pixelResolution == 0) {
            return true;
        }
        if (!(AsciiArtAlgorithm.image.equals(image) &&
                AsciiArtAlgorithm.pixelResolution == pixelResolution)) {
            return true;
        }
        return false;
    }

    /*
     * Checks if the charset need to be updated.
     */
    private boolean toChangeCharset(char[] charset) {
        if (AsciiArtAlgorithm.charset == null) {
            return true;
        }
        if(AsciiArtAlgorithm.charset.length != charset.length){
            return true;
        }

        for (int i = 0; i < charset.length; i++) {
            if (charset[i] != AsciiArtAlgorithm.charset[i]) {
                return true;
            }
        }
        return false;
    }

    /*
     * Creates a 2D array of brightness values for each sub-image of the input image.
     */
    private double[][] createSubImagesBrightness(){
        Image[][] subImages = ImageUtils.splitImage(pixelResolution, image);
        double[][] subImagesBrightness = new double[subImages.length][subImages[0].length];
        for (int i = 0; i < subImages.length; i++) {
            for (int j = 0; j < subImages[i].length; j++) {
                subImagesBrightness[i][j] = ImageUtils.calculateImageBrightness(subImages[i][j]);
            }
        }
        return subImagesBrightness;
    }
}

