package com.example.etablissementmanagement.UI;

import android.app.Fragment;
import android.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.etablissementmanagement.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.navigateTo(LoginFragment.getInstance());
    }

    public void navigateTo(Fragment fragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(fragment.getClass().toString());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

}
