package com.example.vraiburgir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifActivity extends AppCompatActivity {

    private EditText editTextPseudo;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextAddress;
    private EditText editPhoneNumber;
    private EditText editTextFirstName;
    private EditText editTextLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif);

        editTextPseudo = findViewById(R.id.editTextPseudo);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextAddress = findViewById(R.id.editTextAddress);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);

        //TODO
        //remplacer les setText par des methodes de l'api
        editTextPseudo.setText("oui");
        editTextEmail.setText("oui");
        editTextPassword.setText("oui");
        editTextConfirmPassword.setText("oui");
        editTextAddress.setText("oui");
        editPhoneNumber.setText("oui");
        editTextFirstName.setText("oui");
        editTextLastName.setText("oui");

        Button buttonModif = findViewById(R.id.buttonModif);
        buttonModif.setOnClickListener(view -> {
            // Récupère les valeurs saisies par l'utilisateur
            String pseudo = editTextPseudo.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String phone = editPhoneNumber.getText().toString().trim();
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();

            // Vérifie si les champs ne sont pas vides
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                    address.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                    pseudo.isEmpty() || phone.isEmpty()) {
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

            // Faire un update sur l'user avec l'api

            // ...

            Toast.makeText(getApplicationContext(), "Modifications enregistrés", Toast.LENGTH_SHORT).show();
        });
    }
}