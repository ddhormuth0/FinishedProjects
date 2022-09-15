package edu.frostburg.cosc310.DawsonHormuth.AVLTreeProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main class that runs the program and shows off my AVLTree project
 *
 * @author Dawson Hormuth
 */
public class TreeMainClass implements AVLTreeProject {

    /**
     * The main class that runs the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TreeMainClass program = new TreeMainClass();

        program.startProgram();

    }

    private AVLTree myTree;

    /**
     * Constructor for the class
     */
    public TreeMainClass() {
        myTree = new AVLTree();
    }

    /**
     * Method that starts the program
     */
    public void startProgram() {
        String command;
        String firstWord;

        boolean isRunning = true;
        Scanner userInput = new Scanner(System.in);
        System.out.println("\nWelcome to the DH Ninja Tabulating Co. classmate information program!\n");
        getInput();
        this.menu();

        while (isRunning) {
            String parameterOne;
            parameterOne = null;

            String[] parameterTwo = new String[10];

            System.out.println("\nEnter Command: ");
            command = userInput.nextLine();

            //takes the input and puts it in a new scanner we can disect
            Scanner inputLine = new Scanner(command);

            //takes the input and makes the first word into the first input word
            if (inputLine.hasNext()) {
                firstWord = inputLine.next();
            } else {
                firstWord = command;
            }
            //gets the first parameter the user entered
            if (inputLine.hasNext()) {
                parameterOne = inputLine.next();
            }
            //gets the second parameter that is an array for the interests
            if (inputLine.hasNext()) {
                int i = 0;
                while (inputLine.hasNext()) {
                    if (i == 10) {
                        System.out.print("Error Too Many Interests Added, Only the First 10 Were Added!");
                        break;
                    } else {
                        parameterTwo[i] = inputLine.next();
                        i++;
                    }
                }

            }
            System.out.println("");
            //a switch that acts as our menu traversal
            switch (firstWord) {
                //default is in invalid command
                default ->
                    System.out.println("Invalid Command!");
                //searches for name in tree
                case "search" -> {
                    if (parameterOne != null) {
                        if (!this.search(parameterOne)) {
                            System.out.println("Name not found");
                        }
                    } else {
                        System.out.println("Please follow 'search' with a name");
                    }

                }
                //adds name into tree
                case "add" -> {
                    if (parameterTwo[0] != null && parameterOne != null) {
                        insert(parameterOne, parameterTwo);
                        System.out.println(parameterOne + " was entered into the tree with interests ----> " + printArray(parameterTwo));
                    } else {
                        System.out.println("Please enter in the form 'add name interests'");
                    }
                }
                //prints inorder
                case "inorder" -> {
                    printInorder();
                }
                //prints preorder
                case "preorder" -> {
                    printPreorder();
                }
                //prints postorder
                case "postorder" -> {
                    printPostorder();
                }
                //exits program
                case "exit" ->
                    this.exit();
                //prints menu commands
                case "?" ->
                    this.menu();
                //prints creators name
                case "who" ->
                    this.who();

            }

        }
    }

    /**
     * Inserts the given name and string array into the tree
     *
     * @param name
     * @param interests
     */
    @Override
    public void insert(String name, String... interests) {
        myTree.overallRoot = myTree.insertAtNull(name, interests, myTree.overallRoot);
        myTree.overallRoot.setHeight(myTree.setNodeHeight(myTree.overallRoot));
        myTree.checkBalance(myTree.overallRoot);
        if (!myTree.isBalanced(myTree.overallRoot)) {
            myTree.overallRoot = myTree.balanceTree(myTree.overallRoot);
        }
    }

    /**
     * Searches for the name and returns true if its there and false if its not
     *
     * @param name The name of the person you want to search for.
     * @return Returns true or false based on whether that person is in the tree
     * or not.
     */
    @Override
    public boolean search(String name) {
        return myTree.search(name, myTree.overallRoot);
    }

    /**
     * prints inorder traversal
     */
    @Override
    public void printInorder() {
        myTree.printInorder(myTree.overallRoot);
        System.out.println();
    }

    /**
     * prints preorder traversal
     */
    @Override
    public void printPreorder() {
        myTree.printPreorder(myTree.overallRoot);
        System.out.println();
    }

    /**
     * prints postorder traversal
     */
    @Override
    public void printPostorder() {
        myTree.printPostorder(myTree.overallRoot);
        System.out.println();
    }

    /**
     * Prints who made the project
     */
    @Override
    public void who() {
        System.out.println("\nThe P001 program (DH Ninja Tabulating Co. AVLTree Program) was created by Dawson Hormuth!");
    }

    /**
     * Prints the commands that can be used
     */
    @Override
    public void menu() {
        System.out.println("""
                           ------------------------------------------------
                           List of Commands (Please type in all lowercase! Except Names, Capatalize The First Letter of All Names!)
                           search XYZ - search for XYZ (where XYZ is the name string)
                           add XYZ INTERESTA INTERESTB \u2013 Add XYZ with the associated interests(up to 10 interests)
                           inorder, preorder, postorder \u2013 print the following traversals
                           who \u2013 prints program creators name
                           ? - prints all commands
                           exit - quits the program
                           ------------------------------------------------
                           """);
    }

    /**
     * exits the program and prints a message
     */
    @Override
    public void exit() {
        System.out.println("\nThank you for using DH Tabulating Co. AVLTree Program!");
        System.exit(0);
    }

    /**
     * Takes the input file and inserts the names and with their interests into
     * the tree
     */
    public void getInput() {

        try {
            //uses , and new line/row as delimiter
            //TO MAKE THE PROGRAM BETTER, I COULD ASK THE USER TO INPUT THE FILE NAME BECAUSE ALL FILES WILL NOT HAVE THIS NAME
            Scanner input = new Scanner(new File("class_interests.csv")).useDelimiter(",|\r\n");
            System.out.println("File Found!");
            //skips the first line because that is the label line
            input.nextLine();

            //loops through each row and column until there is no data left
            while (input.hasNext()) {
                //int that will access the array
                int interestPlace = 0;

                //makes the array that will have the interests
                String[] interests = new String[3];

                //skips first column
                input.next();

                //the first piece of data in each row is a string while the rest is an int
                String shortName = input.next();

                //loops through the 21 columns of info to get the interests
                //i represents the column
                for (int i = 0; i < 21; i++) {
                    int num = 0;
                    if (input.hasNextInt()) {
                        num = input.nextInt();
                    } else {

                        if (input.hasNext()) {
                            input.next();
                        }

                    }

                    if (num == 1) {
                        //based on which i we are on we will assign the interest to the array
                        switch (i) {
                            case 0 -> {
                                interests[interestPlace] = "EDM";
                                interestPlace++;
                            }
                            case 1 -> {
                                interests[interestPlace] = "Alternative";
                                interestPlace++;
                            }
                            case 2 -> {
                                interests[interestPlace] = "Pop";
                                interestPlace++;
                            }
                            case 3 -> {
                                interests[interestPlace] = "Rock";
                                interestPlace++;
                            }
                            case 4 -> {
                                interests[interestPlace] = "Rap";
                                interestPlace++;
                            }
                            case 5 -> {
                                interests[interestPlace] = "Video Games";
                                interestPlace++;
                            }
                            case 6 -> {
                                interests[interestPlace] = "Soccer";
                                interestPlace++;
                            }
                            case 7 -> {
                                interests[interestPlace] = "Biking";
                                interestPlace++;
                            }
                            case 8 -> {
                                interests[interestPlace] = "Shoes";
                                interestPlace++;
                            }
                            case 9 -> {
                                interests[interestPlace] = "Stocks";
                                interestPlace++;
                            }
                            case 10 -> {
                                interests[interestPlace] = "Making Games";
                                interestPlace++;
                            }
                            case 11 -> {
                                interests[interestPlace] = "Art";
                                interestPlace++;
                            }
                            case 12 -> {
                                interests[interestPlace] = "Playing Music";
                                interestPlace++;
                            }
                            case 13 -> {
                                interests[interestPlace] = "Boardgames";
                                interestPlace++;
                            }
                            case 14 -> {
                                interests[interestPlace] = "Cars";
                                interestPlace++;
                            }
                            case 15 -> {
                                interests[interestPlace] = "Basketball";
                                interestPlace++;
                            }
                            case 16 -> {
                                interests[interestPlace] = "Hanging out";
                                interestPlace++;
                            }
                            case 17 -> {
                                interests[interestPlace] = "Gym";
                                interestPlace++;
                            }
                            case 18 -> {
                                interests[interestPlace] = "Golf";
                                interestPlace++;
                            }
                            case 19 -> {
                                interests[interestPlace] = "Cinematography";
                                interestPlace++;
                            }
                            case 20 -> {
                                interests[interestPlace] = "Running";
                                interestPlace++;
                            }
                        }

                    }

                }
                //inserts name and interests
                insert(shortName, interests);
            }
            //if files is not found
        } catch (FileNotFoundException e) {
            System.out.println("\nTHE FILE THAT THIS PROGRAM REQUIRES TO RUN WAS NOT FOUND!!!");
            System.out.println("THIS PROGRAM WILL NOT RUN PROPERLY WITHOUT THE FILE!\nPLEASE MAKE SURE THE INPUT FILE IS PLACED IN THE PROJECT FOLDER OUTSIDE THE SOURCE FOLDER!\nALSO MAKE SURE THE FILE NAME IS [class_interests.csv] NO BRACKETS!\n ");
            System.exit(1);
        }
    }

    /**
     * Returns an array in string format.
     *
     * @param arr The array you want to print.
     * @return The array in string format.
     */
    public String printArray(String[] arr) {
        String str;
        str = "[";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                str += arr[i];
            }
            if (i < arr.length - 1) {
                if (arr[i + 1] != null) {
                    str += ", ";
                }
            }
        }
        str += "]";
        return str;
    }

}
