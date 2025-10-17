package com.example.pokemon_app_basic;

import org.json.JSONObject;

public interface VolleyCallback {
    public void onSuccess(JSONObject object);
    public void onFailure(Exception e);
}