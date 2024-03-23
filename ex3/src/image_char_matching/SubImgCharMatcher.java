/* package */
package image_char_matching;
/* imports */
import java.util.*;

/**
 * The SubImgCharMatcher class matches characters from a given character set to image brightness levels.
 * It provides methods to add and remove characters from the character set, and to retrieve characters
 * based on image brightness.
 */
public class SubImgCharMatcher {

    /* The minimum brightness level. */
    private static final int MIN_BRIGHTNESS = 0;
    /* The maximum brightness level. */
    private static final int MAX_BRIGHTNESS = 1;
    /* Mapping of brightness values to corresponding characters. */
    private final TreeMap<Double, PriorityQueue<Character>> brightnessToCharsMap;
    /* Mapping of characters to their brightness values.
     (it is static for Sharing and saving calculation between instances) */
    private static final HashMap<Character,Double> CHARS_BRIGHTNESS_MAP = new HashMap<>();
    /* The current charset  */
    private final Set<Character> charset;

    /**
     * Constructs a SubImgCharMatcher with the specified character set.
     *
     * @param charset the character set
     */
    public SubImgCharMatcher(char[] charset){
        this.charset = new HashSet<>();
        fillCharset(charset);
        this.brightnessToCharsMap = new TreeMap<>();
        calculateCharsetBrightness();
        normalizeCharsetBrightness();
    }

    /**
     * Adds a character to the character set.
     *
     * @param c the character to add
     */
    public void addChar(char c){
        if (!charset.contains(c)) {
            insertToBrightnessMap(c);
            charset.add(c);
            resetTree();
        }
    }

    /**
     * Removes a character from the character set.
     *
     * @param c the character to remove
     */
    public void removeChar(char c){
        if (charset.contains(c)){
            charset.remove(c);
            resetTree();
        }
    }
    /**
     * Retrieves the character associated with a specific image brightness level.
     *
     * @param brightness the image brightness level
     * @return the character associated with the brightness level
     */
    public char getCharByImageBrightness(double brightness){
        double accurateKey = getAccurateKey(brightness);
        return brightnessToCharsMap.get(accurateKey).peek();
    }

    /*
     * Fills the character set with characters from the given array.
     */
    private void fillCharset(char[] charset) {
        for (char c : charset) {
            this.charset.add(c);
        }
    }

    /*
     * Calculates the brightness of all the characters in charset and insert it to the brightnessMap.
     */
    private void calculateCharsetBrightness() {
        for (char c : charset) {
            insertToBrightnessMap(c);
        }
    }

    /*
     * Calculates the brightness of a character.
     */
    private double calculateCharBrightness(char c) {
        boolean[][] boolArray = CharConverter.convertToBoolArray(c);
        int rows = boolArray.length;
        int cols = boolArray[0].length;
        int whiteCounter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (boolArray[i][j]){
                    whiteCounter++;
                }
            }
        }
        return (double) whiteCounter / (rows * cols);
    }

    /*
     * Normalizes the brightness values of characters in the character set.
     */
    private void normalizeCharsetBrightness(){
        double maxBrightness = MIN_BRIGHTNESS;
        double minBrightness = MAX_BRIGHTNESS;
        HashMap<Character,Double> normalizedBrightness = new HashMap<>();
        for (char c : charset) {
            double charNormalizedBrightness = CHARS_BRIGHTNESS_MAP.get(c);
            normalizedBrightness.put(c, charNormalizedBrightness);
            if (charNormalizedBrightness > maxBrightness) {
                maxBrightness = charNormalizedBrightness;
            }
            if (minBrightness > charNormalizedBrightness) {
                minBrightness = charNormalizedBrightness;
            }
        }
        linearStretch(minBrightness, maxBrightness, normalizedBrightness);
    }

    /*
     * Adds a character to the brightness map.
     */
    private void addBrightnessTreeMap(char c, double brightness) {
        if (brightnessToCharsMap.containsKey(brightness)){
            brightnessToCharsMap.get(brightness).add(c);
        }
        else {
            PriorityQueue<Character> charQueue = new PriorityQueue<>();
            charQueue.add(c);
            brightnessToCharsMap.put(brightness, charQueue);
        }
    }

    /*
     * Linearly stretches the brightness values of characters.
     */
    private void linearStretch(double minBrightness, double maxBrightness,
                               HashMap<Character,Double> normalizedBrightness){
        double diff = maxBrightness - minBrightness;
        for (char c: normalizedBrightness.keySet()) {
            double brightness = normalizedBrightness.get(c);
            double newBrightness = (brightness - minBrightness) / diff;
            addBrightnessTreeMap(c,newBrightness);
        }
    }

    /*
     * Retrieves the closest key in the brightness map to the given brightness level.
     */
    private double getAccurateKey(double brightness){
        double floorKey = brightnessToCharsMap.floorKey(brightness);
        if (floorKey == brightness) {
            return brightness;
        }
        double ceilingKey = brightnessToCharsMap.ceilingKey(brightness);
        double floorDiff = Math.abs(brightness - floorKey);
        double ceilingDiff = Math.abs(brightness - ceilingKey);
        return Math.min(floorDiff,ceilingDiff) == floorDiff ? floorKey : ceilingKey;
    }

    /*
     * Inserts a character and its brightness into the charToBrightness map.
     */
    private void insertToBrightnessMap(char c){
        if (!CHARS_BRIGHTNESS_MAP.containsKey(c)) {
            CHARS_BRIGHTNESS_MAP.put(c, calculateCharBrightness(c));
        }
    }

    /*
     * Resets the brightness map and modifying it to the new character set.
     */
    private void resetTree(){
        brightnessToCharsMap.clear();
        normalizeCharsetBrightness();
    }
}
