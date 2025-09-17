package com.example.lab2.APs;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab2.Model.AccessPoint;
import com.example.lab2.Model.Estado;
import com.example.lab2.R;
import com.example.lab2.Repo;
import com.example.lab2.databinding.ActivityApDetailBinding;

public class DetallesApActivity extends AppCompatActivity {

    private ActivityApDetailBinding binding;
    private final Repo repo = Repo.getInstance();
    private AccessPoint ap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("ap_id");
        ap = repo.getApById(id);
        if (ap == null) { finish(); return; }

        // cargar datos
        binding.etMarcaDetail.setText(ap.marca);
        binding.etModeloDetail.setText(ap.modelo);
        binding.etVelocidadDetail.setText(ap.velocidad);

        String[] estados = new String[]{"Operativo","En reparación","Dado de baja"};
        String[] bandas  = new String[]{"2.4 GHz","5 GHz","Dual band"};

        ArrayAdapter<String> adpEstado = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, estados);
        ArrayAdapter<String> adpBanda  = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bandas);

        binding.ddlEstadoDetail.setAdapter(adpEstado);
        binding.ddlBandaDetail.setAdapter(adpBanda);

        switch (ap.estado) {
            case OPERATIVO:     binding.ddlEstadoDetail.setText(estados[0], false); break;
            case EN_REPARACION: binding.ddlEstadoDetail.setText(estados[1], false); break;
            default:            binding.ddlEstadoDetail.setText(estados[2], false); break;
        }
        if (ap.banda == null || ap.banda.isEmpty()) ap.banda = "2.4 GHz";
        binding.ddlBandaDetail.setText(ap.banda, false);

        // forzar despliegue al tocar/enfocar
        binding.ddlEstadoDetail.setOnClickListener(v -> binding.ddlEstadoDetail.showDropDown());
        binding.ddlEstadoDetail.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) binding.ddlEstadoDetail.showDropDown(); });

        binding.ddlBandaDetail.setOnClickListener(v -> binding.ddlBandaDetail.showDropDown());
        binding.ddlBandaDetail.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) binding.ddlBandaDetail.showDropDown(); });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ap_detail, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        if (item.getItemId() == R.id.action_save) { saveAndExit(); return true; }
        if (item.getItemId() == R.id.action_delete) { confirmDelete(); return true; }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit(){
        String marca = String.valueOf(binding.etMarcaDetail.getText()).trim();
        String modelo = String.valueOf(binding.etModeloDetail.getText()).trim();
        String velocidad = String.valueOf(binding.etVelocidadDetail.getText()).trim();
        String estadoLabel = String.valueOf(binding.ddlEstadoDetail.getText());
        String banda = String.valueOf(binding.ddlBandaDetail.getText());
        if (marca.isEmpty() || modelo.isEmpty() || velocidad.isEmpty()) return;

        ap.marca = marca;
        ap.modelo = modelo;
        ap.velocidad = velocidad;
        ap.estado = toEstado(estadoLabel);
        ap.banda = banda;

        repo.updateAp(ap);
        finish();
    }

    private void confirmDelete(){
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Access Point")
                .setMessage("¿Seguro que deseas eliminar este Access Point?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar", (d, w) -> {
                    repo.deleteAp(ap.id);
                    finish();
                }).show();
    }

    private Estado toEstado(String label) {
        String k = label.toLowerCase();
        if (k.startsWith("opera")) return Estado.OPERATIVO;
        if (k.startsWith("en rep")) return Estado.EN_REPARACION;
        return Estado.DADO_DE_BAJA;
    }
}