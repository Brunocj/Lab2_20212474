package com.example.lab2.Reporte;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.Model.AccessPoint;
import com.example.lab2.Model.Estado;
import com.example.lab2.Model.Router;
import com.example.lab2.Model.Switch;
import com.example.lab2.R;
import com.example.lab2.Repo;
import com.example.lab2.databinding.ActivityReporteBinding;

import java.util.ArrayList;
import java.util.List;

public class ReporteActivity extends AppCompatActivity {

    private ActivityReporteBinding binding;
    private final Repo repo = Repo.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReporteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarReporte);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buildReport();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildReport(); // refresca si volvieron de editar algo
    }

    private void buildReport() {
        // limpiar contenedores
        binding.containerOperativo.removeAllViews();
        binding.containerEnRep.removeAllViews();
        binding.containerBaja.removeAllViews();

        List<String> operativo = new ArrayList<>();
        List<String> enRep = new ArrayList<>();
        List<String> baja = new ArrayList<>();

        // Routers
        for (Router r : repo.getRouters()) {
            String line = "• [Router] " + safe(r.marca) + " – " + safe(r.modelo);
            bucket(r.estado, line, operativo, enRep, baja);
        }
        // Switches
        for (Switch s : repo.getSwitches()) {
            String line = "• [Switch] " + safe(s.marca) + " – " + safe(s.modelo);
            bucket(s.estado, line, operativo, enRep, baja);
        }
        // Access Points
        for (AccessPoint a : repo.getAps()) {
            String line = "• [AP] " + safe(a.marca) + " – " + safe(a.modelo);
            bucket(a.estado, line, operativo, enRep, baja);
        }

        // Títulos con conteo
        binding.tvTitleOperativo.setText("Operativo (" + operativo.size() + ")");
        binding.tvTitleEnRep.setText("En reparación (" + enRep.size() + ")");
        binding.tvTitleBaja.setText("Dado de baja (" + baja.size() + ")");

        // Pintar filas
        addRows(binding.containerOperativo, operativo);
        addRows(binding.containerEnRep, enRep);
        addRows(binding.containerBaja, baja);
    }

    private void bucket(Estado estado, String line, List<String> op, List<String> rep, List<String> baja) {
        if (estado == Estado.OPERATIVO) op.add(line);
        else if (estado == Estado.EN_REPARACION) rep.add(line);
        else baja.add(line);
    }

    private void addRows(LinearLayout container, List<String> lines) {
        if (lines.isEmpty()) {
            TextView tv = buildRow("No hay equipos en este estado");
            tv.setTypeface(tv.getTypeface(), Typeface.ITALIC);
            tv.setTextColor(0xFF9E9E9E);
            container.addView(tv);
            return;
        }
        for (String s : lines) container.addView(buildRow(s));
    }

    private TextView buildRow(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(14f);
        tv.setPadding(0, dp(4), 0, dp(4));
        return tv;
    }

    private int dp(int v){ return Math.round(getResources().getDisplayMetrics().density * v); }
    private String safe(String t){ return t == null ? "-" : t; }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
