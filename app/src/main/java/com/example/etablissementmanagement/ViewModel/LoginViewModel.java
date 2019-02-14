package com.example.etablissementmanagement.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.etablissementmanagement.Repositories.UserRepository;
import com.example.etablissementmanagement.Models.User;

public class LoginViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

}
