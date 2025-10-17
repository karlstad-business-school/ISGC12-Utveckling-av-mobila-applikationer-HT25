package com.example.pokemon_app_basic;

import java.util.ArrayList;

public class Database {
    public static Database instance;
    private ArrayList<Pokemon> pokemons;

    public Database(){
        if(instance == null){
            instance = this;
        }

        if(pokemons == null){
            pokemons = new ArrayList<Pokemon>();
        }
    }


    public void setPokemons(ArrayList<Pokemon> p){
        pokemons = p;
    }

    public ArrayList<Pokemon> getPokemons(){
        return pokemons;
    }

    public void addPokemon(Pokemon p){
        pokemons.add(p);
    }

    public Pokemon getPokemon(int id){
        for(int i = 0; i < pokemons.size(); i++){
            if(pokemons.get(i).getId() == id){
                return pokemons.get(i);
            }
        }
        return null;
    }

}
