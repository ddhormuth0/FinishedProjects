package edu.frostburg.cosc310.DawsonHormuth.Pokedex;

import java.util.Scanner;

/**
 * My hashmap that will be implemented for the pokedex
 *
 * @author Dawson Hormuth
 */
public class Pokemap {

    private String[] pokedex;
    private int tableSize;
    private double maxLoadFactor;

    /**
     * Constructs our hashmap with 1201 elements to have a load factor of about
     * .66
     */
    public Pokemap() {
        pokedex = new String[1201];
        tableSize = 1201;
        maxLoadFactor = .8;
    }

    /**
     * Adds the info based on the int we get from hashcoding the name.Linearly
     * probes by adding 1 to the already hashcoded name.
     *
     * @param intName Hashcode int for our pokemon name
     * @param info Info of our pokemon
     * @return true or false based on whether it was added or not
     */
    public boolean add(int intName, String info) {
        //no linear probe required
        double loadFactor = loadFactor();
        if (loadFactor <= this.maxLoadFactor) {
            if (pokedex[intName % this.tableSize] == null) {
                pokedex[intName % this.tableSize] = info;
                return true;
            } else {
                //linear probing required, done recursively
                return add(intName + 1, info);
            }
        }
        System.out.println("Cant pass Max load factor, currently at " + this.loadFactor());
        return false;

    }

    /**
     * Searches for the pokemon name in the hashtable.
     *
     * @param name Name of pokemon we are searching for
     * @param intName Int we get from hashcoding the pokemon name
     * @return
     */
    public String search(String name, int intName) {
        if (this.pokedex[intName % this.tableSize] != null) {
            Scanner input = new Scanner(this.pokedex[intName % this.tableSize]);
            String pokedexName = input.next();
            //FIRST WORD OF THE STRING SHOULD BE THE NAME
            //if the names are matches return the info
            input.close();
            if (pokedexName.equals(name) && name != null) {
                return this.pokedex[intName % this.tableSize];
                //if the names are not matches probe forward
            } else {
                return search(name, intName + 1);
            }
            //if the table spot is null then the pokemon isnt in the table
        } else {
            return null;
        }

    }

    /**
     * Removes a pokemon from our hash table
     *
     * @param name the name we want to insert
     * @param intName the hash value of the name
     * @return true or false based on if it was in our table
     */
    public boolean delete(String name, int intName) {
        String gravestone = "Gravestone";
        if (this.pokedex[(intName % this.tableSize)] != null) {
            Scanner input = new Scanner(this.pokedex[intName % this.tableSize]);
            String pokedexName = input.next();
            //FIRST WORD OF THE STRING SHOULD BE THE NAME
            //if the names are matches replace with gravestone
            input.close();
            if (pokedexName.equals(name) && name != null) {
                pokedex[intName % this.tableSize] = gravestone;
                return true;
                //if the names are not matches probe forward
            } else {
                return delete(name, intName + 1);
            }

        }
        //if the table spot is null then the pokemon isnt in the table
        return false;
    }

    /**
     * Prints out the table completely with all pokemon and their stats
     */
    public void printTable() {
        for (String i : this.pokedex) {
            if (i != null) {
                if (!i.equals("Gravestone")) {
                    System.out.println(i);
                }
            }
        }
    }

    /**
     * Gets the current load factor
     *
     * @return the current load factor
     */
    public double loadFactor() {
        return Double.valueOf(tableCount()) / Double.valueOf(this.tableSize);
    }

    /**
     * The amount of pokemon in our table excluding the amount of gravestones
     *
     * @return the amount of pokemon in our table
     */
    public int pokemoneCount() {
        int count = 0;
        int gravestones = 0;
        for (String i : this.pokedex) {
            if (i != null) {
                if (i.equals("Gravestone")) {
                    gravestones++;
                } else {
                    count++;
                }

            }
        }
        System.out.println(gravestones + " Gravestones");
        return count;
    }

    /**
     * Returns the amount of elements in our table including gravestones
     *
     * @return the amount of elements in our table
     */
    public int tableCount() {
        int count = 0;
        for (String i : this.pokedex) {
            if (i != null) {

                count++;
            }
        }
        return count;
    }

    /**
     * Accessor
     *
     * @return our set max load factor
     */
    public double getMaxLoadFactor() {
        return this.maxLoadFactor;
    }
}
