package edu.frostburg.cosc310.DawsonHormuth.AVLTreeProject;

/**
 * The nodes of the tree and their basic functions.
 *
 * @author Dawson Hormuth
 */
public class AVLTreeNode {

    AVLTreeNode left;
    AVLTreeNode right;

    private String shortName;
    private String[] interests;

    private int height;

    /**
     * Constructor for the node with a name and its interests.
     *
     * @param shortName Name you want for the person who represents this node.
     * @param interests Persons interests.
     */
    public AVLTreeNode(String shortName, String[] interests) {
        left = null;
        right = null;
        this.height = 0;
        this.shortName = shortName;
        this.interests = interests;
    }

    //accessors
    /**
     * Accesses short name
     *
     * @return Roots Short Name
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Accesses interests
     *
     * @return Roots Interests
     */
    public String[] getInterests() {
        return this.interests;
    }

    /**
     * Accesses height
     *
     * @return Roots Height
     */
    public int getHeight() {
        return this.height;
    }

    //mutator
    /**
     * Changes height
     *
     * @param num number you want to set height to
     */
    public void setHeight(int num) {
        this.height = num;
    }
}
