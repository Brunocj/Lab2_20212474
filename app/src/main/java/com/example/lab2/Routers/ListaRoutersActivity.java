package com.example.lab2.Routers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.Model.Estado;
import com.example.lab2.Model.Router;
import com.example.lab2.Repo;
import com.example.lab2.databinding.ActivityListaRoutersBinding;

import java.util.List;
import java.util.UUID;

public class ListaRoutersActivity extends AppCompatActivity implements RouterFormDialogFragment.OnRouterCreated{
    private ActivityListaRoutersBinding binding;
    private RouterAdapter adapter;
    private final Repo repo = Repo.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaRoutersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarRouters);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarRouters.setNavigationOnClickListener(v -> finish());

        List<Router> routers = repo.getRouters();
        adapter = new RouterAdapter(this, routers);
        binding.listRouters.setAdapter(adapter);

        updateEmptyState();

        binding.listRouters.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Router r = (Router) adapter.getItem(position);
            Intent i = new Intent(this, DetallesRouterActivity.class);
            i.putExtra("router_id", r.id);
            Toast.makeText(this, "Tap: " + r.id, Toast.LENGTH_SHORT).show();

            startActivity(i);
        });

        binding.fabAdd.setOnClickListener(v -> {
            RouterFormDialogFragment dialog = new RouterFormDialogFragment();
            dialog.show(getSupportFragmentManager(), "router_form");
        });
    }

    private void updateEmptyState(){
        if (adapter.getCount() == 0) {
            binding.emptyContainer.setVisibility(View.VISIBLE);
            binding.listRouters.setVisibility(View.INVISIBLE);
        } else {
            binding.emptyContainer.setVisibility(View.GONE);
            binding.listRouters.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // refrescamos por si se editó/eliminó algo en Detalle
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    // callback del diálogo de creación
    @Override
    public void onCreateRouter(String marca, String modelo, String velocidad, Estado estado) {
        repo.addRouter(
                new Router(
                        java.util.UUID.randomUUID().toString(),
                        marca,
                        modelo,
                        estado,      // ahora viene del dropdown
                        velocidad
                )
        );
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }
}
