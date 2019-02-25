package com.example.etablissementmanagement.UI.Login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.etablissementmanagement.R;
import com.example.etablissementmanagement.UI.Etablissement.EtablissementActivity;
import com.example.etablissementmanagement.Utils.Utils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Utils.getUser(this) != null) {
            Intent intent = new Intent(this, EtablissementActivity.class);
            startActivity(intent);
            finish();

        } else {
            this.navigateTo(LoginFragment.getInstance());
        }
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
