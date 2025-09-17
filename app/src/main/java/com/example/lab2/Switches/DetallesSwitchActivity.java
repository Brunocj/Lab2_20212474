package com.example.lab2.Switches;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.Model.Estado;
import com.example.lab2.Model.Switch;
import com.example.lab2.Model.TipoSwitch;
import com.example.lab2.R;
import com.example.lab2.Repo;
import com.example.lab2.databinding.ActivitySwitchDetailBinding;

public class DetallesSwitchActivity extends AppCompatActivity {

    private ActivitySwitchDetailBinding binding;
    private final Repo repo = Repo.getInstance();
    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySwitchDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("switch_id");
        sw = repo.getSwitchById(id);
        if (sw == null) { finish(); return; }

        // cargar datos
        binding.etMarcaDetail.setText(sw.marca);
        binding.etModeloDetail.setText(sw.modelo);
        binding.etVelocidadDetail.setText(sw.velocidad);

        // dropdowns
        String[] estados = new String[]{"Operativo","En reparación","Dado de baja"};
        String[] tipos = new String[]{"Administrable","No administrable"};

        ArrayAdapter<String> adpEstado = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, estados);
        ArrayAdapter<String> adpTipo   = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tipos);

        binding.ddlEstadoDetail.setAdapter(adpEstado);
        binding.ddlTipoDetail.setAdapter(adpTipo);

        // seleccionar actual
        switch (sw.estado) {
            case OPERATIVO:     binding.ddlEstadoDetail.setText(estados[0], false); break;
            case EN_REPARACION: binding.ddlEstadoDetail.setText(estados[1], false); break;
            default:            binding.ddlEstadoDetail.setText(estados[2], false); break;
        }
        binding.ddlTipoDetail.setText(sw.tipo == TipoSwitch.ADMINISTRABLE ? tipos[0] : tipos[1], false);

        // forzar despliegue al tocar/enfocar
        binding.ddlEstadoDetail.setOnClickListener(v -> binding.ddlEstadoDetail.showDropDown());
        binding.ddlEstadoDetail.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) binding.ddlEstadoDetail.showDropDown(); });

        binding.ddlTipoDetail.setOnClickListener(v -> binding.ddlTipoDetail.showDropDown());
        binding.ddlTipoDetail.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) binding.ddlTipoDetail.showDropDown(); });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_switch_detail, menu);
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
        String tipoLabel = String.valueOf(binding.ddlTipoDetail.getText());
        if (marca.isEmpty() || modelo.isEmpty() || velocidad.isEmpty()) return;

        sw.marca = marca;
        sw.modelo = modelo;
        sw.velocidad = velocidad;
        sw.estado = toEstado(estadoLabel);
        sw.tipo = toTipo(tipoLabel);

        repo.updateSwitch(sw);
        finish();
    }

    private void confirmDelete(){
        new AlertDialog.Builder(this)
                .setTitle("Eliminar switch")
                .setMessage("¿Seguro que deseas eliminar este switch?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar", (d, w) -> {
                    repo.deleteSwitch(sw.id);
                    finish();
                }).show();
    }

    private Estado toEstado(String label) {
        String k = label.toLowerCase();
        if (k.startsWith("opera")) return Estado.OPERATIVO;
        if (k.startsWith("en rep")) return Estado.EN_REPARACION;
        return Estado.DADO_DE_BAJA;
    }

    private TipoSwitch toTipo(String label) {
        String k = label.toLowerCase();
        if (k.startsWith("admin")) return TipoSwitch.ADMINISTRABLE;
        return TipoSwitch.NO_ADMINISTRABLE;
    }
}
