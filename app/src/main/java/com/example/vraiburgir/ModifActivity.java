package com.example.vraiburgir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ModifActivity extends AppCompatActivity {

    private EditText editTextPseudo;
    private EditText editTextEmail;
    private EditText editTextAddress;
    private EditText editPhoneNumber;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private Connexion connexion;
    private Button buttonModif;
    private Button buttonModifMdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif);

        //les champs de texte
        editTextPseudo = findViewById(R.id.editTextPseudo);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);

        buttonModifMdp = findViewById(R.id.buttonModifMpd);
        buttonModifMdp.setOnClickListener(view -> {
            Intent intent = new Intent(this, ModifPassword.class);
            startActivity(intent);
        });


        //TODO
        this.connexion = new Connexion("tet", "Aa123456");
        System.out.println("token " + connexion.getToken());
        if (connexion.connected()) {
            HashMap<String, String> variables = new HashMap<>();
            variables.put("requete", "infos_utilisateur"); // inscription
            RequeteApi requeteApi = new RequeteApi(connexion, variables);
            requeteApi.execute();
            try {
                JSONObject reponse = (JSONObject) requeteApi.get();
                if (reponse.has("message")) {
                    Toast.makeText(getApplicationContext(), " " + reponse.get("message"), Toast.LENGTH_SHORT).show();
                } else {
                    editTextPseudo.setText(reponse.get("login").toString());
                    editTextEmail.setText(reponse.get("email").toString());
                    editTextAddress.setText(reponse.get("adresse").toString());
                    editPhoneNumber.setText(reponse.get("tel").toString());
                    editTextFirstName.setText(reponse.get("prenom").toString());
                    editTextLastName.setText(reponse.get("nom").toString());
                }

            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }

        Button buttonModif = findViewById(R.id.buttonModif);
        buttonModif.setOnClickListener(view -> {
            // Récupère les valeurs saisies par l'utilisateur
            String pseudo = editTextPseudo.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String phone = editPhoneNumber.getText().toString().trim();
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();

            // Vérifie si les champs ne sont pas vides
            if (email.isEmpty() || address.isEmpty() ||
                    firstName.isEmpty() || lastName.isEmpty() ||
                    pseudo.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifie si l'adresse e-mail est valide
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getApplicationContext(), "Veuillez saisir une adresse e-mail valide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifie si le numéro de telephone est valide
            String phonePattern = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";
            if (!phone.matches(phonePattern)){
                Toast.makeText(getApplicationContext(), "Le numéro de télephone n'est pas valide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Faire un update sur l'user avec l'api

            // ...

            Toast.makeText(getApplicationContext(), "Modifications enregistrés", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}