package com.example.etablissementmanagement.UI.Etablissement;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.etablissementmanagement.Models.Etablissement;
import com.example.etablissementmanagement.R;
import com.example.etablissementmanagement.Repositories.EtablissementRepository;


public class add_fragment extends Fragment {
    private View mainView;

    public static int REQ_CODE = 1;

    private EtablissementActivity activity;

    private EditText title;
    private EditText description;


    private LinearLayout linearLayout;

    private Button addButton;

    private ImageView upload;

    private String titleText;
    private String descriptionText;
    private String imgPath;

    private EtablissementRepository repository;
    private Uri currImageURI;

    public static add_fragment getInstance() {
        add_fragment _add_fragment = new add_fragment();
        Bundle args = new Bundle();
        _add_fragment.setArguments(args);
        return _add_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nosave:
                activity.navigateTo(recyclerView.getInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.add_etablissement_fragment, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (EtablissementActivity) getActivity();
        init();
    }

    void init() {
        activity.getSupportActionBar().setTitle("Add etablissement");
        title = mainView.findViewById(R.id.etablissement_title);
        description = mainView.findViewById(R.id.etablissement_description);
        addButton = mainView.findViewById(R.id.add_button);
        upload = mainView.findViewById(R.id.upload);
        upload.setOnClickListener(new ButtClick());
        linearLayout = mainView.findViewById(R.id.lin_lay);
        addButton.setOnClickListener(new SaveButtClick());
        repository = new EtablissementRepository(activity.getApplication());
    }

    private class ButtClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a Picture"), REQ_CODE);
        }
    }

    private class SaveButtClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            titleText = title.getText().toString();
            descriptionText = description.getText().toString();
            if (!titleText.trim().equals("") && !descriptionText.trim().equals("") && !currImageURI.getPath().equals("")) {
                repository.addEtablissement(new Etablissement(titleText, descriptionText, getRealPathFromURI(currImageURI)));
                Toast.makeText(activity.getApplicationContext(), "Etablissement Inserted", Toast.LENGTH_SHORT).show();
                activity.navigateTo(recyclerView.getInstance());
            } else {
                Toast.makeText(activity.getApplicationContext(), "Please complete all fields", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == activity.RESULT_OK && data != null) {

            if (requestCode == REQ_CODE) {

                // currImageURI is the global variable I'm using to hold the content:// URI of the image
                currImageURI = data.getData();

                upload.setImageURI(currImageURI);
            } else {
                Toast.makeText(activity.getApplicationContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity.getApplicationContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentURI, proj, null, null, null);
        if (cursor == null) { 
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


}
