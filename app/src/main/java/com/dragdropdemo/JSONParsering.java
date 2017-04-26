package com.dragdropdemo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONParsering {

    private static InputStream input = null;
    private static JSONObject jobj = null;
    private static String json = "";

    public JSONParsering() {
    }

    public JSONObject getJSONFromUrl(String url) {

        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(
                    input, "iso-8859-1"), 8);
            StringBuilder builderonj = new StringBuilder();
            String line = null;
            while ((line = read.readLine()) != null) {
                builderonj.append(line + "\n");
            }
            input.close();
            json = builderonj.toString();
        } catch (Exception e) {
            Log.e("Buffer Reader", "Error...... " + e.toString());
        }

        try {
            jobj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("Parser", "Error while parsing... " + e.toString());
        }
        return jobj;
    }
}