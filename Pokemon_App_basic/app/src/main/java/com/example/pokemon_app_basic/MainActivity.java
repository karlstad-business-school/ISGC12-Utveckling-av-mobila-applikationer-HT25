package com.example.pokemon_app_basic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VolleyCallback{

    private Cache cache;
    private Network network;
    private RequestQueue requestQueue;

    private Button loadButton;
    int page;
    int offset;
    int limit;

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

        //Database.instance => null
        new Database();
        //Database.instance => nytt objekt, denna ska vi använda hela tiden

        cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        page = 0;
        limit = 20;
        offset = page * limit;
        String url = "https://pokeapi.co/api/v2/pokemon/?offset=" + offset + "&limit=" + limit;
        APICall api = new APICall();
        api.get(requestQueue, this, url);


        loadButton = findViewById(R.id.btn_load);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage();
            }
        });
    }

    private void nextPage(){
        page += 1;
        offset = page * limit;
        String url = "https://pokeapi.co/api/v2/pokemon/?offset=" + offset + "&limit=" + limit;
        APICall api = new APICall();
        api.get(requestQueue, this, url);
    }



    private void generatePokemonList(){
        LinearLayout list = findViewById(R.id.pokemon_list);
        list.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for(int i = 0; i < Database.instance.getPokemons().size(); i++){
            Pokemon pokemon = Database.instance.getPokemons().get(i);

            View itemView = inflater.inflate(R.layout.pokemon_item, list, false);
            TextView nameText = itemView.findViewById(R.id.pokemon_name);

            String fullName = "#" + pokemon.getId() + " " + pokemon.getName();
            nameText.setText(fullName);

            itemView.setOnClickListener(v -> {
                Toast.makeText(this, "Clicked on: " + pokemon.getName() + " : " + pokemon.getId(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, PokemonView.class);
                intent.putExtra("id", pokemon.getId());
                startActivity(intent);
            });

            list.addView(itemView);
        }
    }




    @Override
    public void onSuccess(JSONObject object) {
        try{
            JSONArray results = object.getJSONArray("results");
            for(int i = 0; i < results.length(); i++){
                JSONObject current = results.getJSONObject(i);
                String name = current.getString("name");
                Pokemon p = new Pokemon(offset + i + 1, name);

                Database.instance.addPokemon(p);
            }

            generatePokemonList();
        }catch(JSONException e){
            Log.e("JSONError", e.toString());
        }
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("OnFailureVolley", e.toString());
    }
}