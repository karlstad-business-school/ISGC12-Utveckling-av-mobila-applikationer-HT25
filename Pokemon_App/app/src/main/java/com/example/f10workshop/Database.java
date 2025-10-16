package com.example.f10workshop;

import java.util.ArrayList;

public class Database {
    public static Database instance;

    private ArrayList<Berry> berries;
    private PokemonData data;

    public Database(){
        if(instance == null){
            instance = this;
        }

        berries = new ArrayList<Berry>();
        data = new PokemonData();
        data.setPokemons(new ArrayList<Pokemon>());
    }

    public void setData(PokemonData d){
        data = d;
    }

    public PokemonData getData(){
        return data;
    }

    public ArrayList<Berry> getBerries() {
        return berries;
    }

    public void setBerries(ArrayList<Berry> berries) {
        this.berries = berries;
    }

    public void setPokemons(ArrayList<Pokemon> p){
        data.setPokemons(p);
    }

    public ArrayList<Pokemon> getPokemons(){
        return data.getPokemons();
    }

    public Berry getBerry(int id){
        for(int i = 0; i < data.getBerries().size(); i++){
            if(data.getBerries().get(i).getId() == id){
                return data.getBerries().get(i);
            }
        }
        return null;
    }

    public Pokemon getPokemon(int id){
        for(int i = 0; i < data.getPokemons().size(); i++){
            if(data.getPokemons().get(i).getId() == id){
                return data.getPokemons().get(i);
            }
        }
        return null;
    }

    public void updatePokemon(Pokemon p){
        if(p == null){
            return;
        }
        for(int i = 0; i < data.getPokemons().size(); i++){
            if(data.getPokemons().get(i).getId() == p.getId()){
                data.getPokemons().set(i, p);
                break;
            }
        }
    }


}


/*

package com.example.f10workshop;

import java.util.ArrayList;

public class Database {
    public static Database instance;

    private ArrayList<Pokemon> pokemons;
    private ArrayList<Berry> berries;
    private PokemonData data;

    public Database(){
        if(instance == null){
            instance = this;
        }
        pokemons = new ArrayList<Pokemon>();
        berries = new ArrayList<Berry>();
        data = new PokemonData();
    }

    public ArrayList<Berry> getBerries() {
        return berries;
    }

    public void setBerries(ArrayList<Berry> berries) {
        this.berries = berries;
    }

    public void setPokemons(ArrayList<Pokemon> p){
        pokemons = p;
    }

    public ArrayList<Pokemon> getPokemons(){
        return pokemons;
    }

    public Berry getBerry(int id){
        for(int i = 0; i < berries.size(); i++){
            if(berries.get(i).getId() == id){
                return berries.get(i);
            }
        }
        return null;
    }

    public Pokemon getPokemon(int id){
        for(int i = 0; i < pokemons.size(); i++){
            if(pokemons.get(i).getId() == id){
                return pokemons.get(i);
            }
        }
        return null;
    }

    public void updatePokemon(Pokemon p){
        if(p == null){
            return;
        }
        for(int i = 0; i < pokemons.size(); i++){
            if(pokemons.get(i).getId() == p.getId()){
                pokemons.set(i, p);
                break;
            }
        }
    }


}

 */