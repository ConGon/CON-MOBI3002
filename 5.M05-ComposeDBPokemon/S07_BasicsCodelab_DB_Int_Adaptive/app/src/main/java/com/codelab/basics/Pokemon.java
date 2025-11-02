package com.codelab.basics;

// Object DB ... see Room for Android Studio
// https://developer.android.com/training/data-storage/room
public class Pokemon {

    // Change to reflect Pokemon
    private long id;
    private String Pokemon_Name;
    private String Pokemon_Type;
    private Integer Pokemon_Number;
    // ...

    public Pokemon(long id, String pokemon_Name, String pokemon_Type, Integer pokemon_Number) {
        this.id = id;
        Pokemon_Name = pokemon_Name;
        Pokemon_Type = pokemon_Type;
        Pokemon_Number = pokemon_Number;

    }

    @Override
    public String toString() {
        return  "ID: " + getId() + "\n"
                + "Pokemon Name: "  + getPokemon_Name() + "\n"
                + "Pokemon Type: " + getPokemon_Type() + "\n"
                + "Pokemon Number: " + getPokemon_Number() + "\n"
                ;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPokemon_Name() {
        return Pokemon_Name;
    }

    public void setPokemon_Name(String pokemon_Name) {
        Pokemon_Name = pokemon_Name;
    }

    public String getPokemon_Type() {
        return Pokemon_Type;
    }

    public void setPokemon_Type(String pokemon_Type) {
        Pokemon_Type = pokemon_Type;
    }

    public Integer getPokemon_Number() {
        return Pokemon_Number;
    }

    public void setPokemon_Number(Integer pokemon_Number) {
        Pokemon_Number = pokemon_Number;
    }
}
