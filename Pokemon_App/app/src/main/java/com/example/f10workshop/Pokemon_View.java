package com.example.f10workshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pokemon_View extends AppCompatActivity implements VolleyCallback {

    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent = getIntent();
        if(!intent.hasExtra("id")){
            Log.e("Intent PokemonView", "Det finns inget ID vid start av aktivitet");
            finish();
            return;
        }
        int id = intent.getIntExtra("id", -1);

        pokemon = Database.instance.getPokemon(id);
        if(pokemon == null){
            Log.e("PokemonView", "Det finns ingen pokemon med ID:t " + id);
            finish();
            return;
        }

        Log.e("Pokemon View", "POKEMON: " + pokemon.getName());

        if(pokemon.getTypes() == null || pokemon.getTypes().isEmpty()){
            Log.e("Pokemon View", "No data, get data from API and save");
            String url = "https://pokeapi.co/api/v2/pokemon/" + pokemon.getId();

            Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            RequestQueue requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

            APICall api = new APICall();
            api.get(requestQueue, this, url);

        }else{
            showData();
        }
    }

    private void showData(){
        TextView nameText = findViewById(R.id.pokemon_view_name);
        TextView typeText = findViewById(R.id.pokemon_view_types);
        TextView statText = findViewById(R.id.pokemon_view_stats);
        ImageView icon = findViewById(R.id.pokemon_view_image);

        String fullName = "#" + pokemon.getId() + " " + pokemon.getName();
        nameText.setText(fullName);
        typeText.setText(pokemon.getTypes());
        statText.setText(pokemon.getStats());


        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                + pokemon.getId() + ".png";

        Glide.with(this).load(url).into(icon);
    }

    @Override
    public void onSuccess(JSONObject object) {
        try{
            JSONArray types = object.getJSONArray("types");
            StringBuilder typesString = new StringBuilder();
            for(int i = 0; i < types.length(); i++){
                JSONObject typeObject = types.getJSONObject(i);
                JSONObject type = typeObject.getJSONObject("type");
                String name = type.getString("name");

                typesString.append(name).append(" ");
            }


            JSONArray stats = object.getJSONArray("stats");
            StringBuilder statsString = new StringBuilder();
            for(int i = 0; i < stats.length(); i++){
                JSONObject statObject = stats.getJSONObject(i);
                String baseStat = statObject.getString("base_stat");

                JSONObject stat = statObject.getJSONObject("stat");
                String name = stat.getString("name");

                String fullText = name + ": " + baseStat + "\n";
                statsString.append(fullText);
            }

            Log.e("Pokemon View", "Types: " + typesString.toString());
            Log.e("Pokemon View", "Stats: " + statsString.toString());


            pokemon.setTypes(typesString.toString());
            pokemon.setStats(statsString.toString());
            Database.instance.updatePokemon(pokemon);
            //DataManager.instance.writePokemon(this, Database.instance.getPokemons());
            DataManager.instance.writeToFile(this, Database.instance.getData());

            showData();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onFailure(Exception e) {

    }
}