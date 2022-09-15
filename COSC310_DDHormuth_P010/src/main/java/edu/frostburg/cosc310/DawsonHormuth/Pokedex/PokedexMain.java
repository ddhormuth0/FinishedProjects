package edu.frostburg.cosc310.DawsonHormuth.Pokedex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pokedex main file that will display our pokedex class
 *
 * @author Dawson Hormuth
 */
public class PokedexMain implements Pokedex {

    Pokemap pokedex;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        PokedexMain p1 = new PokedexMain();

        p1.startProgram();

    }

    /**
     * Creates a new pokedex main object
     */
    public PokedexMain() {
        pokedex = new Pokemap();
    }

    @Override
    public String find(String pokemon) {
        return this.pokedex.search(pokemon, Math.abs(pokemon.hashCode()));
    }

    @Override
    public boolean add(String pokemon, String entry) {
        return this.pokedex.add(Math.abs(pokemon.hashCode()), entry);
    }

    @Override
    public boolean delete(String pokemon) {
        return this.pokedex.delete(pokemon, Math.abs(pokemon.hashCode()));
    }

    @Override
    public void printHT() {
        this.pokedex.printTable();
    }

    @Override
    public double getLoadFactor() {
        return this.pokedex.loadFactor();
    }

    @Override
    public double getMaxLoadFactor() {
        return this.pokedex.getMaxLoadFactor();
    }

    @Override
    public int count() {
        return this.pokedex.pokemoneCount();
    }

    @Override
    public void who() {
        System.out.println("\nThe P010 program (DH Ninja Tabulating Co. Pokedex Program) was created by Dawson Hormuth!");
    }

    @Override
    public void help() {
        System.out.println("""
                           ------------------------------------------------
                           List of Commands (Please type in all lowercase! Except Names, Capatalize The First Letter of All Names!)
                           find XYZ - search for XYZ (where XYZ is the name string)
                           add XYZ - Add XYZ where XYZ is the name, then wait to br prompted to enter the info
                           delete XYZ - Where XYZ is the name of the pokemon you want to delete
                           print - Print the entire table
                           count - Prints the number of entries in our pokedex
                           loadfactor - Prints current load factor of pokedex
                           maxloadfactor - Prints max load factor of pokedex
                           who - prints program creators name
                           help - prints all commands
                           exit - quits the program
                           ------------------------------------------------
                           """);
    }

    @Override
    public void exit() {
        System.out.println("\nThank you for using DH Tabulating Co. Pokedex Program!");
        System.exit(0);
    }

    /**
     * Takes our csv file and adds the contents into the pokedex
     */
    public void getInput() {
        //I want to reformat the stats in a way I think makes more sense so I am going to store them in these strings
        String attack;
        String classification;
        String defense;
        String hp;
        String japaneseName;
        String name;
        String pokedexNum;
        String spAttack;
        String spDefense;
        String speed;
        String typeOne;
        String typeTwo;
        String pokedexInfo;
        try {
            //uses , and new line/row as delimiter
            //TO MAKE THE PROGRAM BETTER, I COULD ASK THE USER TO INPUT THE FILE NAME BECAUSE ALL FILES WILL NOT HAVE THIS NAME
            Scanner input = new Scanner(new File("pokemon_pokedex_alt.csv"), StandardCharsets.UTF_8).useDelimiter(",|\r\n");
            System.out.println("File Found!");
            //skips the first line because that is the label line
            input.nextLine();

            //loops through each row and column until there is no data left
            while (input.hasNext()) {

                //order of information we need
                attack = input.next();
                classification = input.next();
                defense = input.next();
                hp = input.next();
                japaneseName = input.next();
                name = input.next();
                pokedexNum = input.next();
                spAttack = input.next();
                spDefense = input.next();
                speed = input.next();
                typeOne = input.next();
                typeTwo = input.next();

                pokedexInfo = (name + " JapaneseName: " + japaneseName + "  PokedexNumber:" + pokedexNum
                        + "\nClass: " + classification + "    Type: " + typeOne + "   Type: " + typeTwo
                        + "\nHP:" + hp + "   Attack:" + attack + "  Defense:" + defense + "   Speed:" + speed
                        + "\nSpecialAttack:" + spAttack + "  SpecialDefense:" + spDefense + "\n");

                pokedex.add((Math.abs(name.hashCode())), pokedexInfo);

            }
            //if files is not found
        } catch (FileNotFoundException e) {
            System.out.println("\nTHE FILE THAT THIS PROGRAM REQUIRES TO RUN WAS NOT FOUND!!!");
            System.out.println("THIS PROGRAM WILL NOT RUN PROPERLY WITHOUT THE FILE!\nPLEASE MAKE SURE THE INPUT FILE IS PLACED IN THE PROJECT FOLDER OUTSIDE THE SOURCE FOLDER!\nALSO MAKE SURE THE FILE NAME IS [class_interests.csv] NO BRACKETS!\n ");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(PokedexMain.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        this.help();

        while (isRunning) {
            String parameterOne;
            parameterOne = null;

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

            System.out.println("");
            //a switch that acts as our menu traversal
            switch (firstWord) {
                //default is in invalid command
                default ->
                    System.out.println("Invalid Command!");
                //searches for name in tree
                case "find" -> {
                    if (parameterOne != null) {
                        if (this.find(parameterOne) == null) {
                            System.out.println("Name not found");
                        } else {
                            System.out.println(find(parameterOne));
                        }
                    } else {
                        System.out.println("Please follow 'search' with pokemon");
                    }

                }
                //adds name into tree
                case "add" -> {
                    if (parameterOne != null) {
                        System.out.println("ENTRIES MUST BE KEPT AS ONE WORD");
                        System.out.println("What is the attack");
                        String attack = userInput.next();
                        System.out.println("What is the classification ");
                        String classification = userInput.next();
                        System.out.println("What is the defense");
                        String defense = userInput.next();
                        System.out.println("What is the hp ");
                        String hp = userInput.next();
                        System.out.println("What is the pokedex number ");
                        String pokedexNum = userInput.next();
                        System.out.println("What is the special attack ");
                        String spAttack = userInput.next();
                        System.out.println("What is the special defense ");
                        String spDefense = userInput.next();
                        System.out.println("What is the speed ");
                        String speed = userInput.next();
                        System.out.println("What is the first type ");
                        String typeOne = userInput.next();
                        System.out.println("What is the second type ");
                        String typeTwo = userInput.next();

                        String pokemonInfo;

                        pokemonInfo = (parameterOne + " PokedexNumber:" + pokedexNum
                                + "\nClass: " + classification + "    Type: " + typeOne + "   Type: " + typeTwo
                                + "\nHP:" + hp + "   Attack:" + attack + "  Defense:" + defense + "   Speed:" + speed
                                + "\nSpecialAttack:" + spAttack + "  SpecialDefense:" + spDefense + "\n");

                        this.add(parameterOne, pokemonInfo);
                        System.out.println(pokemonInfo);

                        //I was getting errors so I placed this in here because there was an extra lin being read
                        if (userInput.hasNextLine()) {
                            userInput.nextLine();
                        }
                    } else {
                        System.out.println("Please enter in the form 'add PokemonName' then wait for program to prompt you to enter the info");
                    }
                }
                //exits program
                case "exit" ->
                    this.exit();
                //prints menu commands
                case "help" ->
                    this.help();
                //prints creators name
                case "who" ->
                    this.who();
                //deletes name
                case "delete" -> {
                    if (parameterOne != null) {
                        if (this.delete(parameterOne)) {
                            System.out.println(parameterOne + " deleted!");
                        } else {
                            System.out.println(parameterOne + " not found in pokedex");
                        }
                    } else {
                        System.out.println("Please enter in the form 'delete XYZ' where XYZ is the name.");
                    }
                }
                //prints number of elements in pokedex
                case "count" ->
                    System.out.println(this.count() + " Pokemon in the pokedex!");
                //prints load factor
                case "loadfactor" ->
                    System.out.println(this.getLoadFactor());
                //prints max load factor
                case "maxloadfactor" ->
                    System.out.println(this.getMaxLoadFactor());

                //prints pokedex
                case "print" ->
                    this.printHT();
            }

        }

    }
}
