package com.example.vraiburgir.ui.notifications;

import android.content.Intent;
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
import com.example.vraiburgir.R;
import com.example.vraiburgir.SignupActivity;
import com.example.vraiburgir.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
//
//        View view = inflater.inflate(R.layout.login_form, container, false);
//
//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return view;
        View view = inflater.inflate(R.layout.login_form, container, false);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        Button buttonRegister = view.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.vraiburgir.SignupActivity.class);
                startActivity(intent);
            }
        });

        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // Vérifie si le mot de passe est suffisamment long
                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Effectue l'action de connexion (par exemple, en utilisant une API)
                // ...
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}