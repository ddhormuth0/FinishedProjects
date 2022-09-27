package ddhormuth.cosc310_p011_ddhormuth;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main File that executes our program by doing a graph
 * @author Dawson Hormuth
 */
public class GraphMain {

    Graph dungeonOne;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GraphMain gStart = new GraphMain();
        gStart.startProgram();
    }

    /**
     * Constructs our graph.
     */
    public GraphMain() {
        dungeonOne = new Graph();
    }

    /**
     * Finds the path ZELDA needs to take in the dungeon to fin the heart
     * container.
     */
    public void findPath() {
        //searches from A
        dungeonOne.search('A');
        //Gets our path as a string
        String path = dungeonOne.getPath();
        //If A is the only letter in the path then there is no path to x
        if (path.equals("A")) {
            System.out.println("There is no path to the heart conatiner!");
        } else {
            System.out.println("The path is:" + path);
            System.out.println("It will take " + dungeonOne.getPathLength() + " steps to reach the container.");
            System.out.println("Zelda will need " + dungeonOne.getPathLength() / 100 + " potions to get to the heart container.");
        }
    }

    /**
     * Takes our file and makes it into a graph.
     *
     * @param fileName The name of the file you want to graph.
     */
    public void getInput(String fileName) {

        try {
            int numOfNodes = 0;
            //uses , and new line/row as delimiter

            Scanner input = new Scanner(new File(fileName)).useDelimiter(",|\r\n");
            System.out.println("File Found!");

            //one line of the input file
            String line;
            //scans our file
            while (input.hasNext()) {
                line = input.nextLine();
                //if the line contains # then it is a comment
                if (!line.contains("#")) {
                    //make a scanner with the line we get from the file
                    Scanner lineScan = new Scanner(line);

                    dungeonOne.addNode(lineScan.next().charAt(0));

                    while (lineScan.hasNext()) {
                        //adds an edge to the current node indicated by the numOfNodes counter
                        dungeonOne.addEdge(numOfNodes, lineScan.next().charAt(0), lineScan.nextInt());
                    }
                    lineScan.close();
                    numOfNodes++;
                }
            }

        } catch (IOException e) {

        }
    }

    /**
     * Prints out the map number and runs our path algorithm for it
     */
    public void startProgram() {
        System.out.println("Welcome to DH Tabulating Co. We are here to help you find your way through your dungeons!");
        GraphMain g0 = new GraphMain();
        g0.getInput("map_0000-1.txt");
        GraphMain g1 = new GraphMain();
        g1.getInput("map_0001.txt");
        GraphMain g2 = new GraphMain();
        g2.getInput("map_0010.txt");
        GraphMain g3 = new GraphMain();
        g3.getInput("map_0011.txt");
        GraphMain g4 = new GraphMain();
        g4.getInput("map_0100.txt");
        System.out.println("\nFor Map 0000: ");
        g0.findPath();
        System.out.println("\nFor Map 0001: ");
        g1.findPath();
        System.out.println("\nFor Map 0010: ");
        g2.findPath();
        System.out.println("\nFor Map 0011: ");
        g3.findPath();
        System.out.println("\nFor Map 0100: ");
        g4.findPath();
    }
}
