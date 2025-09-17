package com.example.lab2.Switches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.Model.Estado;
import com.example.lab2.Model.Switch;
import com.example.lab2.Model.TipoSwitch;
import com.example.lab2.R;
import com.example.lab2.Repo;
import com.example.lab2.databinding.ActivityListaSwitchesBinding;

import java.util.List;
import java.util.UUID;

public class ListaSwitchesActivity extends AppCompatActivity
        implements SwitchFormDialogFragment.OnSwitchCreated {

    private ActivityListaSwitchesBinding binding;
    private SwitchAdapter adapter;
    private final Repo repo = Repo.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaSwitchesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarSwitches);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarSwitches.setNavigationOnClickListener(v -> finish());

        List<Switch> switches = repo.getSwitches();
        adapter = new SwitchAdapter(this, switches);
        binding.listSwitches.setAdapter(adapter);

        updateEmptyState();

        binding.listSwitches.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Switch s = (Switch) adapter.getItem(position);
            Intent i = new Intent(this, DetallesSwitchActivity.class);
            i.putExtra("switch_id", s.id);
            startActivity(i);
        });

        binding.fabAdd.setOnClickListener(v -> {
            SwitchFormDialogFragment dialog = new SwitchFormDialogFragment();
            dialog.show(getSupportFragmentManager(), "switch_form");
        });
    }

    private void updateEmptyState(){
        if (adapter.getCount() == 0) {
            binding.emptyContainer.setVisibility(View.VISIBLE);
            binding.listSwitches.setVisibility(View.INVISIBLE);
        } else {
            binding.emptyContainer.setVisibility(View.GONE);
            binding.listSwitches.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    @Override
    public void onCreateSwitch(String marca, String modelo, String velocidad, Estado estado, TipoSwitch tipo) {
        repo.addSwitch(new Switch(UUID.randomUUID().toString(), marca, modelo, velocidad, estado, tipo));
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }
}
