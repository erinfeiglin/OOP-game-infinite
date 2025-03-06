package pepse.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Provides procedurally-generated colors around a pivot.
 *
 * @author Dan Nirel
 */
public final class ColorSupplier {
    private static final int DEFAULT_COLOR_DELTA = 10;
    private final static Random random = new Random();

    /**
     * Returns a color similar to baseColor, with a default delta.
     *
     * @param baseColor A color that we wish to approximate.
     * @return A color similar to baseColor.
     */
    public static Color approximateColor(Color baseColor) {
        return approximateColor(baseColor, DEFAULT_COLOR_DELTA);
    }


    /**
     * Returns a color similar to baseColor, with a difference of at most colorDelta.
     * Where the difference is equal along all channels
     *
     * @param baseColor  A color that we wish to approximate.
     * @param colorDelta The maximal difference (per channel) between the sampled color and the base color.
     * @return A color similar to baseColor.
     */
    public static Color approximateMonoColor(Color baseColor, int colorDelta) {
        int channel = randomChannelInRange(baseColor.getRed() - colorDelta, baseColor.getRed() + colorDelta);
        return new Color(channel, channel, channel);
    }


    /**
     * Returns a color similar to baseColor, with a default delta.
     * Where the difference is equal along all channels
     *
     * @param baseColor A color that we wish to approximate.
     * @return A color similar to baseColor.
     */
    public static Color approximateMonoColor(Color baseColor) {
        return approximateMonoColor(baseColor, DEFAULT_COLOR_DELTA);
    }


    /**
     * Returns a color similar to baseColor, with a difference of at most colorDelta.
     *
     * @param baseColor  A color that we wish to approximate.
     * @param colorDelta The maximal difference (per channel) between the sampled color and the base color.
     * @return A color similar to baseColor.
     */
    public static Color approximateColor(Color baseColor, int colorDelta) {

        return new Color(
                randomChannelInRange(baseColor.getRed() - colorDelta, baseColor.getRed() + colorDelta),
                randomChannelInRange(baseColor.getGreen() - colorDelta, baseColor.getGreen() + colorDelta),
                randomChannelInRange(baseColor.getBlue() - colorDelta, baseColor.getBlue() + colorDelta));
    }

    /**
     * This method generates a random value for a color channel within the given range [min, max].
     *
     * @param min The lower bound of the given range.
     * @param max The upper bound of the given range.
     * @return A random number in the range [min, max], clipped to [0,255].
     */
    private static int randomChannelInRange(int min, int max) {
        int channel = random.nextInt(max - min + 1) + min;
        return Math.min(255, Math.max(channel, 0));
    }
    /**
     * Selects a color from the colors array, giving higher probability to colors
     * with lower indices (earlier in the array). The probability decreases as the index increases,
     * with weights derived from the indices. For an array of length n, the weights are
     * proportional to n, (n-1), (n-2), ..., 1.
     *
     * The total weight is calculated using the formula for the sum of the first n natural numbers:
     * n * (n + 1) / 2.
     * A random value is then used to select a color based on the cumulative weight distribution.
     *
     * @param colors the array of colors to choose from
     * @return A randomly selected Color from the colors array, biased towards earlier colors.
     */
    public static Color chooseRandomColor(Color[] colors) {
        int n = colors.length;
        if (n == 0) {
            return colors[0];
        }
        int totalWeight = n * (n + 1) / 2;

        int randomValue = random.nextInt(totalWeight);
        int cumulativeWeight = 0;
        for (int i = 0; i < n; i++) {
            cumulativeWeight += n - i;
            if (randomValue < cumulativeWeight) {
                return colors[i];
            }
        }
        return colors[0];

    }
}
