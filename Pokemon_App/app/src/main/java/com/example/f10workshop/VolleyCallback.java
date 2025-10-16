package com.example.f10workshop;

import org.json.JSONObject;

public interface VolleyCallback {
    public void onSuccess(JSONObject object);
    public void onFailure(Exception e);
}