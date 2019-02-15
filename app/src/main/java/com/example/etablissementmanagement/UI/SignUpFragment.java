package com.example.etablissementmanagement.UI;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.etablissementmanagement.Repositories.UserRepository;
import com.example.etablissementmanagement.Models.User;
import com.example.etablissementmanagement.R;
import com.example.etablissementmanagement.ViewModel.LoginViewModel;

public class SignUpFragment extends Fragment {

    private LoginViewModel loginViewModel;

    private LoginActivity activity;

    private View mainView;

    private EditText username;
    private EditText password;
    private EditText confirmPassword;

    String userText;
    String passwordText;
    String confirmPasswordText;

    private ImageView back;

    private Button button;

    public static SignUpFragment getInstance() {
        SignUpFragment signUpFragment = new SignUpFragment();
        Bundle args = new Bundle();
        signUpFragment.setArguments(args);
        return signUpFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_registre, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (LoginActivity) getActivity();
        init();
    }

    void init() {
        username = mainView.findViewById(R.id.username_registre);
        password = mainView.findViewById(R.id.password_registre);
        confirmPassword = mainView.findViewById(R.id.confirm_password);
        back = mainView.findViewById(R.id.back_to_login);
        button = mainView.findViewById(R.id.registre_button);
        loginViewModel = ViewModelProviders.of(activity).get(LoginViewModel.class);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.navigateTo(LoginFragment.getInstance());
            }
        });

        button.setOnClickListener(new ButtClick());

    }

    private class ButtClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            userText = username.getText().toString();
            passwordText = password.getText().toString();
            confirmPasswordText = confirmPassword.getText().toString();

            if (!userText.equals("") && !passwordText.equals("") && !confirmPasswordText.equals("")) {
                if (passwordText.equals(confirmPasswordText)) {
                    User user = new User(userText, passwordText);
                    loginViewModel.addUser(user);
                    Toast.makeText(activity.getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
                    activity.navigateTo(LoginFragment.getInstance());
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.i("Text" , "HAAAHOWA TEXT" + userText + passwordText + confirmPasswordText);
                Toast.makeText(activity.getApplicationContext(), "Please complete all fields", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
