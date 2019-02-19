package com.example.etablissementmanagement.UI.Etablissement;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.example.etablissementmanagement.Repositories.EtablissementRepository;
import com.example.etablissementmanagement.UI.LoginActivity;

import java.util.List;

public class recyclerView extends Fragment implements OnLoadCompleted {

    private List<Etablissement> list;

    private EtablissementRepository repository;

    private EtablissementActivity activity;

    private View mainView;

    private RecyclerView recyclerView;

    private MyAdapter adapter;

    private FloatingActionButton add;

    EtablissementRepository.getEtablissementAsyncTask getEtablissementAsyncTask;

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

        add = mainView.findViewById(R.id.add_new_etab);

        repository = new EtablissementRepository(activity.getApplication());

        recyclerView = mainView.findViewById(R.id.recycler);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                repository.deleteEtablissement(adapter.getEtablissementAt(viewHolder.getAdapterPosition()));
                list.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.navigateTo(add_fragment.getInstance());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(activity.getApplicationContext());
        adapter.setOnItemClickListner(new MyAdapter.OnItemClickListner() {
            @Override
            public void OnItemDelete(int position) {
                repository.deleteEtablissement(adapter.getEtablissementAt(position));
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
        recyclerView.setAdapter(adapter);
        updateRecyclerView();
    }

    void updateRecyclerView() {
        //adapter.setEtablissements(list);
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

}
