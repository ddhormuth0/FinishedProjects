package ddhormuth.cosc310_p011_ddhormuth;

import java.util.Set;
import java.util.TreeMap;

/**
 * The individual nodes of our graph
 * @author Dawson Hormuth
 */
public class Node {

    //this char represents the current node or source node
    private char source;
    //this map will represent our edges where the character is the destination and the int is the weight
    private TreeMap<Character, Integer> map;

    /**
     * Constructor
     * @param source The character that represents the node.
     */
    public Node(char source) {
        map = new TreeMap<>();
        this.source = source;
    }

    /**
     * Adds an edge to our node.
     *
     * @param destination The destination vertex of the edge.
     * @param weight The distance/weight of the edge.
     */
    public void addEdge(char destination, int weight) {
        map.put(destination, weight);
    }

    /**
     * Accessor for the nodes name/source.
     *
     * @return the nodes name.
     */
    public char getSource() {
        return this.source;
    }

    /**
     * Accessor for the length of the edge.
     *
     * @param destination The destination node for the edge.
     * @return The length of the edge.
     */
    public int getLength(char destination) {
        return map.get(destination);
    }

    /**
     * Explores the nearby nodes.
     *
     * @return A set of the nearby/adjacent nodes.
     */
    public Set<Character> explore() {
        return map.keySet();
    }
}
