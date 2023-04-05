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

import com.example.vraiburgir.MainActivity;
import com.example.vraiburgir.ModifActivity;
import com.example.vraiburgir.R;
import com.example.vraiburgir.SignupActivity;
import com.example.vraiburgir.databinding.FragmentNotificationsBinding;

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

            // Vérifie si l'adresse e-mail est valide
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "Veuillez saisir une adresse e-mail valide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérifie si le mot de passe contient ce qu'il faut
            String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
            if (!password.matches(passwordPattern)) {
                Toast.makeText(getContext(), "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO
            // Effectue l'action de connexion (par exemple, en utilisant une API)
            // Remplacer le text dans le toast
            connected = true;
            verifieConnexion();
            view.invalidate();
            // ...
            Toast.makeText(getContext(), "Ravie de vous revoir " + "#mettre le nom de m'utilisater", Toast.LENGTH_SHORT).show();
            //
        });

        verifieConnexion();

        return view;
    }

    public void verifieConnexion(){
        if (!isConnected()) {
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            buttonLogin.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.VISIBLE);
            textViewBonjour.setVisibility(View.GONE);
            buttonModif.setVisibility(View.GONE);
        } else {
            // TODO remplacer "user" + afficher des infos
            textViewBonjour.setText("Bonjour,\n"+ "user\n" + "infos");
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