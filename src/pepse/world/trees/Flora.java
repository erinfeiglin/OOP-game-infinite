package pepse.world.trees;

import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.util.NoiseGenerator;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * a class that handles the generation of trees across the map
 */
public class Flora {

    private final Terrain terrain;
    private TreeGenerator treeGenerator;
    private final NoiseGenerator noiseGenerator;

    /**
     * Constructor for the Flora class.
     *
     * @param terrain The terrain object to determine ground height for tree placement.
     * @param seed    The seed value for procedural noise generation.
     */
    public Flora(Terrain terrain, int seed) {
        this.treeGenerator = new TreeGenerator();
        this.terrain = terrain;
        this.noiseGenerator = new NoiseGenerator(seed, (int)terrain.groundHeightAt(0));
    }


    /**
     * Creates trees within a given range, based on a combination of sine wave and noise values.
     *
     * @param minX The minimum X-coordinate for the range.
     * @param maxX The maximum X-coordinate for the range.
     * @return A list of trees created within the specified range.
     */
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> createdTrees = new ArrayList<>();

        double frequency = Constants.FLORA_SIN_PERIODICITY; // Controls the periodicity
        double amplitude = Constants.FLORA_SINE_AMPLITUDE;  // Controls how far the wave fluctuates

        for (int x = minX; x < maxX; x+= Constants.BLOCK_SIZE) {
            double sineValue = Math.sin(x * frequency) * amplitude + Constants.SIN_AMPLITUDE_OFFSET;
            double finalValue = sineValue * noiseGenerator.noise(x, Constants.SIN_NOISE_FACTOR);
            if(finalValue > Constants.TREE_SPAWN_THRESHOLD) {
                Vector2 position = new Vector2(x, terrain.groundHeightAt(x));
                Tree tree = treeGenerator.createTree(position);
                createdTrees.add(tree);
            }
        }
        return createdTrees;
    }

}
