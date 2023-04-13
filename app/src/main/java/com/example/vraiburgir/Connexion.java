package com.example.vraiburgir;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Connexion extends AsyncTask {
    private String token;
    private String login;
    private String mdp;

    public Connexion(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
        execute();
        try {
            get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void connexion() {
        String apiUrl = "http://10.0.2.2/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/index.php";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
//            URIBuilder builder = new URIBuilder(apiUrl);
//            builder.setParameter("requete", "connexion")
//                    .setParameter("login", login)
//                    .setParameter("mdp", mdp);
//
//            HttpGet request = new HttpGet(builder.build());
//            CloseableHttpResponse response = httpClient.execute(request);

            HttpPost httpPost = new HttpPost(apiUrl);
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("requete", "connexion"));
            nvps.add(new BasicNameValuePair("login", login));
            nvps.add(new BasicNameValuePair("mdp", mdp));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    JSONObject json = new JSONObject(responseBody);
                    if (json.has("token"))
                        token = json.getString("token");
                    else
                        System.out.println(json.getString("message"));
                }
            } finally {

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
    }

    public String getToken() {
        return token;
    }

    public boolean connected() {
        return token != null;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        connexion();
        return null;
    }
}
