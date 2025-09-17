package com.example.lab2.APs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.Model.AccessPoint;
import com.example.lab2.Model.Estado;
import com.example.lab2.R;
import com.example.lab2.Repo;
import com.example.lab2.databinding.ActivityListaApsBinding;

import java.util.List;
import java.util.UUID;

public class ListaAPsActivity extends AppCompatActivity
        implements ApFormDialogFragment.OnApCreated {

    private ActivityListaApsBinding binding;
    private ApAdapter adapter;
    private final Repo repo = Repo.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaApsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarAps);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarAps.setNavigationOnClickListener(v -> finish());
        List<AccessPoint> aps = repo.getAps();
        adapter = new ApAdapter(this, aps);
        binding.listAps.setAdapter(adapter);

        updateEmptyState();

        binding.listAps.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            AccessPoint ap = (AccessPoint) adapter.getItem(position);
            Intent i = new Intent(this, DetallesApActivity.class);
            i.putExtra("ap_id", ap.id);
            startActivity(i);
        });

        binding.fabAdd.setOnClickListener(v -> {
            ApFormDialogFragment dialog = new ApFormDialogFragment();
            dialog.show(getSupportFragmentManager(), "ap_form");
        });
    }

    private void updateEmptyState(){
        if (adapter.getCount() == 0) {
            binding.emptyContainer.setVisibility(View.VISIBLE);
            binding.listAps.setVisibility(View.INVISIBLE);
        } else {
            binding.emptyContainer.setVisibility(View.GONE);
            binding.listAps.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    @Override
    public void onCreateAp(String marca, String modelo, String velocidad, Estado estado, String banda) {
        repo.addAp(new AccessPoint(UUID.randomUUID().toString(), marca, modelo, velocidad, estado, banda));
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }
}
