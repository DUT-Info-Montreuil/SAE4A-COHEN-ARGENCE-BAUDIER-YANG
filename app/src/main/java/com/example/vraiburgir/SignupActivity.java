package com.example.vraiburgir;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextPseudo;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextAddress;
    private EditText editPhoneNumber;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextVille;
    private EditText editTextCodePostal;
    private Connexion connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextPseudo = findViewById(R.id.editTextPseudo);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextAddress = findViewById(R.id.editTextAddress);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextVille = findViewById(R.id.editTextVille);
        editTextCodePostal = findViewById(R.id.editTextCodePostal);

        Button buttonSignup = findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(v -> {
            // Récupère les valeurs saisies par l'utilisateur
            String pseudo = editTextPseudo.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String phone = editPhoneNumber.getText().toString().trim();
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();
            String ville = editTextVille.getText().toString().trim();
            String codePostal = editTextCodePostal.getText().toString().trim();

            // Vérifie si les champs ne sont pas vides
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                    address.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                    pseudo.isEmpty() || phone.isEmpty() || ville.isEmpty() || codePostal.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifie si l'adresse e-mail est valide
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getApplicationContext(), "Veuillez saisir une adresse e-mail valide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifie si les mots de passe correspondent
            if (!password.equals(confirmPassword)) {
                Toast.makeText(getApplicationContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifie si le numéro de telephone est valide
            String phonePattern = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";
            if (!phone.matches(phonePattern)){
                Toast.makeText(getApplicationContext(), "Le numéro de télephone n'est pas valide", Toast.LENGTH_SHORT).show();
                return;
            }

            if (codePostal.length() != 5){
                Toast.makeText(getApplicationContext(), "Le code postal n'est pas valide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifie si le mot de passe est suffisamment long
            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifie si le mot de passe contient des caractères spéciaux, une majuscule et un chiffre
            String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
            if (!password.matches(passwordPattern)) {
                Toast.makeText(getApplicationContext(), "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial", Toast.LENGTH_SHORT).show();
                return;
            }

            // Effectue l'action d'inscription avc l'api
            this.connexion = new Connexion("tet", "Aa123456");
            System.out.println("token " + connexion.getToken());
            if (connexion.connected()) {
                HashMap<String, String> variables = new HashMap<>();
                variables.put("requete", "inscription");
                variables.put("login", pseudo);
                variables.put("email", email);
                variables.put("mdp", password);
                variables.put("conf_mdp", confirmPassword);
                variables.put("adresse", address);
                variables.put("tel", phone);
                variables.put("prenom", firstName);
                variables.put("nom", lastName);
                variables.put("ville", ville);
                variables.put("codePostale", codePostal);
                RequeteApi requeteApi = new RequeteApi(null, variables);
                requeteApi.execute();
                try {
                    JSONObject reponse = (JSONObject) requeteApi.get();
                    if (reponse.has("message")) {
                        System.out.println(reponse.get("message"));
                        Toast.makeText(getApplicationContext(), " " + reponse.get("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            Toast.makeText(getApplicationContext(), "Inscription réussie", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

}
