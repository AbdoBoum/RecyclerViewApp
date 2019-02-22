package com.example.etablissementmanagement.UI.Etablissement;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.etablissementmanagement.Helper.OnLoadCompleted;
import com.example.etablissementmanagement.Models.Etablissement;
import com.example.etablissementmanagement.R;
import com.example.etablissementmanagement.R2;
import com.example.etablissementmanagement.Repositories.EtablissementRepository;
import com.example.etablissementmanagement.UI.Login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class recyclerView extends Fragment implements OnLoadCompleted {

    private List<Etablissement> list;

    private EtablissementRepository repository;

    private EtablissementActivity activity;

    private View mainView;

    @BindView(R2.id.recycler)
    RecyclerView recyclerView;

    private MyAdapter adapter;

    @BindView(R2.id.add_new_etab)
    FloatingActionButton add;

    EtablissementRepository.getEtablissementAsyncTask getEtablissementAsyncTask;

    private Unbinder unbinder;

    public static recyclerView getInstance() {
        recyclerView _recyclerView = new recyclerView();
        Bundle args = new Bundle();
        _recyclerView.setArguments(args);
        return _recyclerView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.recycler_view, container, false);
        unbinder = ButterKnife.bind(this, mainView);
        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (EtablissementActivity) getActivity();
        init();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
                activity.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    void init() {
        activity.getSupportActionBar().setTitle("Etablissements");

        repository = new EtablissementRepository(activity.getApplication());

        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(activity.getApplicationContext());
        adapter.setOnItemClickListner(position -> {
            repository.deleteEtablissement(adapter.getEtablissementAt(position));
            list.remove(position);
            adapter.notifyItemRemoved(position);
        });
        recyclerView.setAdapter(adapter);
        updateRecyclerView();
    }

    @OnClick(R2.id.add_new_etab)
    public void onClickAdd(View v) {
        activity.navigateTo(add_fragment.getInstance());
    }

    void updateRecyclerView() {
        getEtablissementAsyncTask = new EtablissementRepository.getEtablissementAsyncTask(repository.getEtablissementDao(), this);
        getEtablissementAsyncTask.execute();

    }

    @Override
    public void OnLoadCompleted() {
        list = getEtablissementAsyncTask.getEtablissements();
        adapter.setEtablissements(list);
        adapter.notifyDataSetChanged();
        Log.i("TAG", String.valueOf(getEtablissementAsyncTask.getEtablissements().size()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
