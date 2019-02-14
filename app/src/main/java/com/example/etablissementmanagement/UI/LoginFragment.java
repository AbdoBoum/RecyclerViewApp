package com.example.etablissementmanagement.UI;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.etablissementmanagement.Repositories.UserRepository;
import com.example.etablissementmanagement.Models.User;
import com.example.etablissementmanagement.OnLoadCompleted;
import com.example.etablissementmanagement.R;
import com.example.etablissementmanagement.UI.Etablissement.EtablissementActivity;

public class LoginFragment extends Fragment implements OnLoadCompleted {

    private LoginActivity activity;

    private View mainView;

    private EditText username;
    private EditText password;

    private TextView signUp;

    private Button loginButton;

    private UserRepository userRepository;

    private User user;

    private OnLoadCompleted callback;

    UserRepository.getUserAsyncTask getUserAsyncTask;

    String usernameText;
    String passwordText;

    public static LoginFragment getInstance() {
        LoginFragment loginFragment = new LoginFragment();
        Bundle args = new Bundle();
        loginFragment.setArguments(args);
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_login, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (LoginActivity) getActivity();
        init();
    }

    void init() {
        username = mainView.findViewById(R.id.username);
        password = mainView.findViewById(R.id.password);
        loginButton = mainView.findViewById(R.id.login_button);
        signUp = mainView.findViewById(R.id.registre);
        loginButton.setOnClickListener(new ButtClick());
        userRepository = new UserRepository(activity.getApplication());
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.navigateTo(SignUpFragment.getInstance());
            }
        });
        callback = this;
    }

    private class ButtClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            usernameText = username.getText().toString();
            passwordText = password.getText().toString();
            user = new User(usernameText, passwordText);

            if (!usernameText.equals("") && !passwordText.equals("")) {
                getUserAsyncTask = new UserRepository.getUserAsyncTask(userRepository.getUserDao(),  callback);
                getUserAsyncTask.execute(user);
            } else {
                Toast.makeText(activity.getApplicationContext(), "Please complete All fields", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void OnLoadCompleted() {
        if (getUserAsyncTask.getUser() != null) {
            Intent intent = new Intent(activity, EtablissementActivity.class);
            activity.startActivity(intent);
            activity.finish();
            //Toast.makeText(activity.getApplicationContext(), "Valid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity.getApplicationContext(), "Invalid informations", Toast.LENGTH_SHORT).show();
        }
    }

}
