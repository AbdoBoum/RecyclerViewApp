package com.example.etablissementmanagement.UI.Login;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.etablissementmanagement.Models.User;
import com.example.etablissementmanagement.R;
import com.example.etablissementmanagement.R2;
import com.example.etablissementmanagement.ViewModel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SignUpFragment extends Fragment {

    private LoginViewModel loginViewModel;

    private LoginActivity activity;

    private View mainView;

    @BindView(R2.id.username_registre)
    EditText username;
    @BindView(R2.id.password_registre)
    EditText password;
    @BindView(R2.id.confirm_password)
    EditText confirmPassword;

    String userText;
    String passwordText;
    String confirmPasswordText;

    @BindView(R2.id.back_to_login)
    ImageView back;

    @BindView(R2.id.registre_button)
    Button button;

    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, mainView);
        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (LoginActivity) getActivity();
        init();
    }

    void init() {
        loginViewModel = ViewModelProviders.of(activity).get(LoginViewModel.class);
    }

    @OnClick(R2.id.back_to_login)
    public void onClickBack(View v) {
        activity.navigateTo(LoginFragment.getInstance());
    }


    @OnClick(R2.id.registre_button)
    public void onClickRegistre(View v) {
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
            Log.i("Text", "Fields: " + userText + passwordText + confirmPasswordText);
            Toast.makeText(activity.getApplicationContext(), "Please complete all fields", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
