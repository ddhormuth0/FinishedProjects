package ddhormuth.cosc310_p011_ddhormuth;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

/**
 * The Graph that our program utilizes
 * @author Dawson Hormuth
 */
public class Graph {
    //NEED TO INITIALIZE THE CURRENT NODE to currentNode = find('A');

    private ArrayList<Node> graph;
    private ArrayList<Character> visitedNodes;
    private int pathLength;
    private Stack<Character> path;

    /**
     * Constructs our graph which is a list of nodes.
     */
    public Graph() {
        graph = new ArrayList<>();
        visitedNodes = new ArrayList<>();
        path = new Stack<>();
        pathLength = 0;
    }

    /**
     * Prints graph nodes out.
     */
    public void printGraph() {
        for (Node n : graph) {
            System.out.print(n.getSource() + " ");
        }
    }

    /**
     * Gets the path length.
     *
     * @return The length in steps that zelda has to take.
     */
    public int getPathLength() {
        return this.pathLength;
    }

    /**
     * Gets the path of our graph into a string
     * @return The path in a string.
     */
    public String getPath() {
        String pathString = "";
        //stack for our characters
        Stack<Character> s1 = new Stack();
        //queue for our characters
        Queue<Character> q1 = new LinkedList<>();
        while (!path.empty()) {
            
            char c = path.pop();
            //add all chars to stack
            s1.add((c));
            //add all chars to queue
            q1.add(c);
        }
        //while the stack is not empty
        while (!s1.empty()) {
            //add the values back into the stack
            path.add(q1.remove());
            //pop to the string
            pathString += s1.pop();
            if (!s1.empty()) {
                pathString += " --> ";
            }

        }

        return pathString;
    }

    /**
     * Adds a node with the character input.
     *
     * @param node Character of the node you are creating.
     */
    public void addNode(char node) {
        graph.add(new Node(node));
    }

    /**
     * Finds the node with the associated char. Inefficient way of finding node,
     * it would be better to use a hash map but that would add a little more
     * complexity to the program. Or you could map each node to its letter and
     * remove the source field from the node. If you need better performance
     * change the array list to a hash map.
     *
     * @param node the char of the node you want to find
     * @return the node you are searching for or null if no such node exists
     */
    public Node find(char node) {
        for (Node n : graph) {

            if (n.getSource() == node) {
                return n;
            }
        }
        return null;
    }

    /**
     * Adds an edge to the node.
     *
     * @param source The source node of the edge.
     * @param destination The destination node of the edge.
     * @param weight The weight or distance of the edge.
     */
    public void addEdge(char source, char destination, int weight) {
        //node we want, to add edge to
        Node n = this.find(source);
        //add edge to node
        if (n != null) {
            n.addEdge(destination, weight);
        } else {
            System.out.println("Source Node not added");
        }

    }

    /**
     * Used when creating the list, is more efficient than searching the list
     * through each time if we already know where the int is in the array.
     *
     * @param index Index of node you want to add edge to.
     * @param destination Destination of edge.
     * @param weight Weight or distance of edge.
     */
    public void addEdge(int index, char destination, int weight) {
        Node n = this.graph.get(index);
        if (n != null) {
            n.addEdge(destination, weight);
        } else {
            System.out.println("Source Node not added");
        }

    }

    /**
     * Searches for the dungeon room ZELDA needs to find.
     *
     * @param source The current node we are on.
     */
    public void search(char source) {
        //sets our current node
        Node currentNode = find(source);
        //adds node to the path
        path.add(currentNode.getSource());
        //adds node to visited nodes
        visitedNodes.add(currentNode.getSource());
        //set of adjacent verticies put into tree set to order them
        TreeSet<Character> adjacentVs = new TreeSet<>(currentNode.explore());

        //explores our node and checks its suroundings to see if it is the destination
        for (char v : adjacentVs) {
            //destination
            if (v == 'X') {
                path.add('X');
                pathLength += currentNode.getLength(v);
                return;
            }
            //not destination
            if (!visitedNodes.contains(v)) {
                pathLength += currentNode.getLength(v);
                search(v);
                //if it is not the destination and there are no adjacent nodes left to search then this happens
                if (!path.contains('X')) {
                    path.pop();
                    pathLength -= currentNode.getLength(v);
                }

            }
        }

    }
}
