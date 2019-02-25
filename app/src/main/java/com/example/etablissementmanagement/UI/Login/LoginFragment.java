package com.example.etablissementmanagement.UI.Login;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.etablissementmanagement.R2;
import com.example.etablissementmanagement.Repositories.UserRepository;
import com.example.etablissementmanagement.Models.User;
import com.example.etablissementmanagement.Helper.OnLoadCompleted;
import com.example.etablissementmanagement.R;
import com.example.etablissementmanagement.UI.Etablissement.EtablissementActivity;
import com.example.etablissementmanagement.Utils.Utils;

public class LoginFragment extends Fragment implements OnLoadCompleted {

    private LoginActivity activity;

    private View mainView;

    @BindView(R2.id.username)
    EditText username;
    @BindView(R2.id.password)
    EditText password;

    private UserRepository userRepository;

    private User user;

    private OnLoadCompleted callback;

    UserRepository.getUserAsyncTask getUserAsyncTask;

    String usernameText;
    String passwordText;

    private Unbinder unbinder;

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
        userRepository = new UserRepository(activity.getApplication());
        callback = this;
    }

    @OnClick(R2.id.registre)
    public void onClick(View v) {
        activity.navigateTo(SignUpFragment.getInstance());
    }

    @OnClick(R2.id.login_button)
    public void onClickLogin(View v) {
        usernameText = username.getText().toString();
        passwordText = password.getText().toString();

        if (!usernameText.equals("") && !passwordText.equals("")) {
            user = new User(usernameText, passwordText);
            getUserAsyncTask = new UserRepository.getUserAsyncTask(userRepository.getUserDao(), callback);
            getUserAsyncTask.execute(user);
        } else {
            Toast.makeText(activity.getApplicationContext(), "Please complete All fields", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void OnLoadCompleted() {
        if (getUserAsyncTask.getUser() != null) {
            Utils.addUserToSharedPrefs(activity.getApplicationContext(), user);
            Intent intent = new Intent(activity, EtablissementActivity.class);
            activity.startActivity(intent);
            activity.finish();
        } else {
            Toast.makeText(activity.getApplicationContext(), "Invalid informations", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
