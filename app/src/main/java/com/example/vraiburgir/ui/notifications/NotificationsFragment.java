package com.example.vraiburgir.ui.notifications;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vraiburgir.Connexion;
import com.example.vraiburgir.MainActivity;
import com.example.vraiburgir.ModifActivity;
import com.example.vraiburgir.R;
import com.example.vraiburgir.RequeteApi;
import com.example.vraiburgir.SignupActivity;
import com.example.vraiburgir.databinding.FragmentNotificationsBinding;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationsFragment extends Fragment {

    private static boolean connected = false;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private Button buttonModif;
    private Button buttonLogin;
    private TextView textViewBonjour;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_form, container, false);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        textViewBonjour = view.findViewById(R.id.textViewBonjour);
        textViewBonjour.setTextSize(48);
        textViewBonjour.setTypeface(null, Typeface.BOLD);

        buttonRegister = view.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), SignupActivity.class);
            startActivity(intent);
        });

        buttonModif = view.findViewById(R.id.buttonModif);
        buttonModif.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ModifActivity.class);
            startActivity(intent);
        });

        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(v -> {
            // Récupère les valeurs saisies par l'utilisateur
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Vérifie si les champs ne sont pas vides
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // CONNEXION
            MainActivity.connexion = new Connexion(email, password);
            System.out.println("token " + MainActivity.connexion.getToken());
            if (MainActivity.connexion.connected()) {
                List<NameValuePair> variables = new ArrayList<>();
                variables.add(new BasicNameValuePair("requete", "infos_utilisateur"));
                RequeteApi requeteApi = new RequeteApi(MainActivity.connexion, variables);
                requeteApi.execute();
                try {
                    JSONObject reponse = (JSONObject) requeteApi.get();
                    if (reponse.has("message")) {
                        Toast.makeText(getContext(), " " + reponse.get("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        connected = true;
                        verifieConnexion(reponse.get("login").toString());
                        view.invalidate();
                        // ...
                        Toast.makeText(getContext(), "Ravie de vous revoir " + reponse.get("login"), Toast.LENGTH_SHORT).show();
                        //
                    }

                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        verifieConnexion("");

        return view;
    }

    public void verifieConnexion(String login) {
        if (!isConnected()) {
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            buttonLogin.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.VISIBLE);
            textViewBonjour.setVisibility(View.GONE);
            buttonModif.setVisibility(View.GONE);
        } else {
            // TODO remplacer "user" + afficher des infos
            textViewBonjour.setText("Bonjour,\n" + login);
            //
            textViewBonjour.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.GONE);
            editTextPassword.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.GONE);
            buttonRegister.setVisibility(View.GONE);
            buttonModif.setVisibility(View.VISIBLE);
        }
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}