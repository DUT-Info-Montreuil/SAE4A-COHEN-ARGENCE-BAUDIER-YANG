package com.example.vraiburgir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifPassword extends AppCompatActivity {

    private EditText mOldPasswordEditText;
    private EditText mNewPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mChangePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_password);

        mOldPasswordEditText = findViewById(R.id.old_password_edittext);
        mNewPasswordEditText = findViewById(R.id.new_password_edittext);
        mConfirmPasswordEditText = findViewById(R.id.confirm_password_edittext);
        mChangePasswordButton = findViewById(R.id.change_password_button);

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = mOldPasswordEditText.getText().toString();
                String newPassword = mNewPasswordEditText.getText().toString();
                String confirmPassword = mConfirmPasswordEditText.getText().toString();

                if (!validateInputs(oldPassword, newPassword, confirmPassword)) {
                    // Inputs are not valid, show an error message
                    Toast.makeText(ModifPassword.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Inputs are valid, change the password
                changePassword(oldPassword, newPassword);
                finish();
            }
        });
    }

    private boolean validateInputs(String oldPassword, String newPassword, String confirmPassword) {
        // Check if new password is the same as old password
        if (oldPassword.equals(newPassword)) {
            return false;
        }

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            return false;
        }

        return true;
    }

    private void changePassword(String oldPassword, String newPassword) {
//        // Vérifier si l'ancien mot de passe est correct
//        if (!oldPassword.equals(user.getPassword())) {
//            // Si l'ancien mot de passe n'est pas correct, afficher un message d'erreur
//            Toast.makeText(this, "Ancien mot de passe incorrect", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Vérifier si le nouveau mot de passe est différent de l'ancien mot de passe
//        if (newPassword.equals(oldPassword)) {
//            // Si le nouveau mot de passe est le même que l'ancien, afficher un message d'erreur
//            Toast.makeText(this, "Le nouveau mot de passe ne peut pas être identique à l'ancien", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Vérifier si la confirmation du nouveau mot de passe est identique au nouveau mot de passe
//        if (!newPassword.equals(confirmPassword)) {
//            // Si la confirmation du nouveau mot de passe est différente, afficher un message d'erreur
//            Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Si tout est correct, mettre à jour le mot de passe de l'utilisateur
//        user.setPassword(newPassword);
//        // Enregistrer le nouvel utilisateur dans la base de données ou dans le système de stockage approprié
//
//        // Afficher un message de confirmation
//        Toast.makeText(this, "Mot de passe modifié avec succès", Toast.LENGTH_SHORT).show();
    }
}
