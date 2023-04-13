package com.example.vraiburgir;

import android.os.AsyncTask;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RequeteApi extends AsyncTask {

    private static final String apiUrl = "http://10.0.2.2/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/index.php";
    private Connexion connexion;
    private List<NameValuePair> variables;

    public RequeteApi(Connexion connexion, List<NameValuePair> variables) {
        this.connexion = connexion;
        this.variables = variables;
    }

    public RequeteApi(List<NameValuePair> variables) {
        this.variables = variables;
    }


    public JSONObject requete() {
        JSONObject json = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            /*
            URIBuilder builder = new URIBuilder(apiUrl);
            for (String key : variables.keySet()) {
                builder.addParameter(key, variables.get(key));
            }
            HttpGet request = new HttpGet(builder.build());
            */
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setEntity(new UrlEncodedFormEntity(variables));
            if (connexion != null)
                httpPost.addHeader("Authorization", "Bearer " + connexion.getToken());
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();/*
                    String responseString = EntityUtils.toString(entity, "UTF-8");
                    System.out.println(responseString);*/
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    JSONTokener tokener = new JSONTokener(responseBody);
                    if (tokener.nextClean() == '[') {
                        json = new JSONObject();
                        json.put("array", new JSONArray(responseBody));
                    } else {
                        json = new JSONObject(responseBody);
                    }
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return requete();
    }
}
