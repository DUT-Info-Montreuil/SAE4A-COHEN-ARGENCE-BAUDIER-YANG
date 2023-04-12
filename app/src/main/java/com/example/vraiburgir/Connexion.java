package com.example.vraiburgir;

import android.content.Intent;
import android.os.AsyncTask;

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

public class Connexion extends AsyncTask {
    private String token;
    private String login;
    private String mdp;

    public Connexion(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
        //connexion();
    }

    public void connexion() {
        String apiUrl = "https://slimy-hounds-walk-194-254-119-253.loca.lt/api/connexion";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
//            URIBuilder builder = new URIBuilder(apiUrl);
//            builder.setParameter("login", login)
//                    .setParameter("mdp", mdp);
//
//            HttpGet request = new HttpGet(builder.build());
//            CloseableHttpResponse response = httpClient.execute(request);

            HttpPost httpPost = new HttpPost(apiUrl);
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("login", login));
            nvps.add(new BasicNameValuePair("mdp", mdp));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            httpPost.addHeader("Bypass-Tunnel-Reminder", " aaa");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                System.out.println("!!!!!!!!!");
                HttpEntity entity = response.getEntity();
                System.out.println("?????????");
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    System.out.println("test");
                    System.out.println(responseBody);
                    JSONObject json = new JSONObject(responseBody);
                    token = json.getString("token");
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
/*
    public void connexion() {
        String apiUrl = "http://localhost/~syang/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/index.php";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder(apiUrl);
            builder.setParameter("requete", "connexion").setParameter("login", login)
                    .setParameter("mdp", mdp);

            HttpGet request = new HttpGet(builder.build());
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    JSONObject json = new JSONObject(responseBody);
                    token = json.getString("token");
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
    }
    */
    public String getToken() {
        return token;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        connexion();
        return null;
    }
}
