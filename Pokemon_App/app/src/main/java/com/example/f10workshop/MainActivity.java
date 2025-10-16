package com.example.f10workshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VolleyCallback {

    private PokemonData pokemonData;
    private Cache cache;
    private Network network;
    private RequestQueue requestQueue;
    private boolean waitingForData;
    private String typeOfAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new DataManager();
        new Database();
        pokemonData = DataManager.instance.readFromFile(this);
        Database.instance.setData(pokemonData);

        cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.nav_pokemon){
                Log.e("MAIN ACTIVITY", "Swap to pokemon: ");
                //showPokemonList();
                showDataList("pokemon");
                return true;
            }else if(id == R.id.nav_berry){
                Log.e("MAIN ACTIVITY", "Swap to berry: ");
                //showBerryList();
                showDataList("berry");
                return true;
            }

            return false;
        });
        nav.setSelectedItemId(R.id.nav_pokemon); //Sätter värdet och kör lyssnaren, som man trycker på den

    }

    private void showDataList(String type){
        if(waitingForData){
            return;
        }
        if(pokemonData == null){
            pokemonData = DataManager.instance.readFromFile(this);
        }

        ArrayList<Pokemon> pokemons = pokemonData.getPokemons();
        ArrayList<Berry> berries  = pokemonData.getBerries();
        int offset = 0;
        int limit = 151;
        String url = "https://pokeapi.co/api/v2/" + type + "/?offset=" + offset + "&limit=" + limit;
        typeOfAPI = type;
        waitingForData = true;

       if(type.equals(("pokemon"))){
           if(pokemons == null || pokemons.isEmpty()){
               APICall api = new APICall();
               api.get(requestQueue, this, url);
           }else{
               generatePokemonList();
           }
       }else if(type.equals("berry")){
           if(berries == null || berries.isEmpty()) {
               APICall api = new APICall();
               api.get(requestQueue, this, url);
           }else{
               generateBerryList();
           }
       }
    }

    private void generatePokemonList(){
        LinearLayout list = findViewById(R.id.pokemon_list);
        list.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        if(pokemonData == null){
            pokemonData = DataManager.instance.readFromFile(this);
        }
        ArrayList<Pokemon> pokemons = pokemonData.getPokemons();

        for(int i = 0; i < pokemons.size(); i++){
            Pokemon pokemon = pokemons.get(i);

            View itemView = inflater.inflate(R.layout.pokemon_item, list, false);
            TextView nameText = itemView.findViewById(R.id.pokemon_name);

            String fullName = "#" + pokemon.getId() + " " + pokemon.getName();
            nameText.setText(fullName);

            itemView.setOnClickListener(v -> {
                Toast.makeText(this, "Clicked on: " + pokemon.getName() + " : " + pokemon.getId(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, Pokemon_View.class);
                intent.putExtra("id", pokemon.getId());
                startActivity(intent);
            });
            list.addView(itemView);
        }
        waitingForData = false;
    }


    private void generateBerryList(){
        LinearLayout list = findViewById(R.id.pokemon_list);
        list.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        if(pokemonData == null){
            pokemonData = DataManager.instance.readFromFile(this);
        }
        ArrayList<Berry> berries = pokemonData.getBerries();

        for(int i = 0; i < berries.size(); i++){
            Berry berry = berries.get(i);

            View itemView = inflater.inflate(R.layout.pokemon_item, list, false);
            TextView nameText = itemView.findViewById(R.id.pokemon_name);

            String fullName = berry.getName() + " - " + berry.getId();
            nameText.setText(fullName);

            itemView.setOnClickListener(v -> {
                Toast.makeText(this, "Clicked on: " + berry.getName() + " : " + berry.getId(), Toast.LENGTH_SHORT).show();
            });
            list.addView(itemView);
        }
        waitingForData = false;
    }


    @Override
    public void onSuccess(JSONObject object) {
        try{
            if(typeOfAPI.equals("pokemon")){
                pokemonData.setPokemons(new ArrayList<Pokemon>());
                JSONArray results = object.getJSONArray("results");
                for(int i = 0; i < results.length(); i++){
                    JSONObject current = results.getJSONObject(i);
                    String name = current.getString("name");
                    Pokemon p = new Pokemon(i + 1, name);

                    pokemonData.getPokemons().add(p);
                }

                DataManager.instance.writeToFile(this, Database.instance.getData());
                generatePokemonList();
            }else if(typeOfAPI.equals("berry")){
                pokemonData.setBerries(new ArrayList<Berry>());
                JSONArray results = object.getJSONArray("results");
                for(int i = 0; i < results.length(); i++){
                    JSONObject current = results.getJSONObject(i);
                    String name = current.getString("name");
                    Berry b = new Berry(i + 1, name);

                    pokemonData.getBerries().add(b);
                }

                DataManager.instance.writeToFile(this, Database.instance.getData());
                generateBerryList();
            }
        }catch(JSONException e){
            Log.e("JSONError", e.toString());
        }
    }

    @Override
    public void onFailure(Exception e) {

    }
}