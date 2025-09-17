package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.APs.ListaAPsActivity;
import com.example.lab2.Reporte.ReporteActivity;
import com.example.lab2.Routers.ListaRoutersActivity;
import com.example.lab2.Switches.ListaSwitchesActivity;
import com.example.lab2.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.btnRouters.setOnClickListener(v ->
                startActivity(new Intent(this, ListaRoutersActivity.class)));

        binding.btnSwitches.setOnClickListener(v ->
                startActivity(new Intent(this, ListaSwitchesActivity.class)));

        binding.btnAps.setOnClickListener(v ->
                startActivity(new Intent(this, ListaAPsActivity.class)));

        findViewById(R.id.btnReporte).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, com.example.lab2.Reporte.ReporteActivity.class));
        });
    }
}