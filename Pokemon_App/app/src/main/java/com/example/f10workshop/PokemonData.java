package com.example.f10workshop;

import java.io.Serializable;
import java.util.ArrayList;

public class PokemonData implements Serializable {
    private ArrayList<Pokemon> pokemons;
    private ArrayList<Berry> berries;

    public PokemonData(){
        pokemons = new ArrayList<Pokemon>();
        berries = new ArrayList<Berry>();
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public ArrayList<Berry> getBerries() {
        return berries;
    }

    public void setBerries(ArrayList<Berry> berries) {
        this.berries = berries;
    }
}
