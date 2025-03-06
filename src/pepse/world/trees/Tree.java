package pepse.world.trees;

import danogl.GameObject;

import java.util.List;

/**
 * A class that represents a tree, pairing its trunk, leaves, and fruits together.
 */
public class Tree {
    private GameObject trunk;
    private List<GameObject> leaves;
    private final List<GameObject> fruits;

    /**
     * Constructor for creating a Tree object.
     *
     * @param trunk  The trunk of the tree.
     * @param leaves A list of GameObjects representing the leaves of the tree.
     * @param fruits A list of GameObjects representing the fruits of the tree.
     */
    public Tree(GameObject trunk, List<GameObject> leaves, List<GameObject> fruits) {
        this.trunk = trunk;
        this.leaves = leaves;
        this.fruits = fruits;
    }

    /**
     * returns the trunk of the tree
     * @return the gameObject of the trunk
     */
    public GameObject getTrunk() {
        return trunk;
    }

    /**
     * returns the leaves of the tree
     * @return the List of gameObjects of the leaves
     */
    public List<GameObject> getLeaves() {
        return leaves;
    }

    /**
     * Returns the fruits of the tree.
     *
     * @return A list of GameObjects representing the tree's fruits.
     */
    public List<GameObject> getFruits() {
        return fruits;
    }
}

