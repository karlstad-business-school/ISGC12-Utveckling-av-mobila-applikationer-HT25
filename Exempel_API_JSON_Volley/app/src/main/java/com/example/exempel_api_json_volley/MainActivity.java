package com.example.exempel_api_json_volley;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements VolleyCallback {

    private final String API_KEY = "YOUR_API_KEY";
    private TextView cityTV, skyTV, tempTV;
    private EditText searchET;

    private RequestQueue requestQueue;
    private Cache cache;
    private Network network;

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

        cityTV = findViewById(R.id.city_tv);
        skyTV = findViewById(R.id.sky_tv);
        tempTV = findViewById(R.id.temp_tv);
        searchET = findViewById(R.id.serach);

        cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }


    public void search(View view){
        String city = "Karlstad";
        city = searchET.getText().toString();

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&mode=json";

        APICall api = new APICall();
        api.get(requestQueue, MainActivity.this, url);
    }

    @Override
    public void onSuccess(JSONObject object) {
        Log.e("VOLLEY TEST", object.toString());
        try{
            String name = object.get("name").toString();
            String temp = object.getJSONObject("main").get("temp").toString();

            JSONArray weather = object.getJSONArray("weather");
            String sky = weather.getJSONObject(0).get("main").toString();
            /*for(int i = 0; i < weather.length(); i++){

            }*/

            cityTV.setText(name);
            tempTV.setText(temp);
            skyTV.setText(sky);
        }catch (Exception e){
            Log.e("VOLLEY ERROR PARSE JSON", e.toString());
        }
        if(count == 10){

        }

    }

    @Override
    public void onFailure(Exception e) {

    }
}