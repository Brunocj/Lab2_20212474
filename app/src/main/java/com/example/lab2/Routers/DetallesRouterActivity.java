package com.example.lab2.Routers;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.Model.Estado;
import com.example.lab2.Model.Router;
import com.example.lab2.R;
import com.example.lab2.Repo;
import com.example.lab2.databinding.ActivityListaRoutersBinding;
import com.example.lab2.databinding.ActivityRouterDetailBinding;

public class DetallesRouterActivity extends AppCompatActivity {

    private ActivityRouterDetailBinding binding;
    private final Repo repo = Repo.getInstance();
    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.lab2.databinding.ActivityRouterDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("router_id");
        router = repo.getRouterById(id);
        if (router == null) { finish(); return; }

        // Datos base
        binding.etMarcaDetail.setText(router.marca);
        binding.etModeloDetail.setText(router.modelo);
        binding.etVelocidadDetail.setText(router.velocidad);

        // Dropdown de estado
        String[] estados = new String[]{"Operativo","En reparación","Dado de baja"};
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, estados);
        binding.ddlEstadoDetail.setAdapter(adp);

        switch (router.estado) {
            case OPERATIVO:     binding.ddlEstadoDetail.setText(estados[0], false); break;
            case EN_REPARACION: binding.ddlEstadoDetail.setText(estados[1], false); break;
            default:            binding.ddlEstadoDetail.setText(estados[2], false); break;
        }

        // Fuerza mostrar al tocar o enfocar
        binding.ddlEstadoDetail.setOnClickListener(v -> binding.ddlEstadoDetail.showDropDown());
        binding.ddlEstadoDetail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) binding.ddlEstadoDetail.showDropDown();
        });
    }





    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_router_detail, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){ finish(); return true; }
        if (item.getItemId() == R.id.action_save){ saveAndExit(); return true; }
        if (item.getItemId() == R.id.action_delete){ confirmDelete(); return true; }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        String marca = String.valueOf(binding.etMarcaDetail.getText()).trim();
        String modelo = String.valueOf(binding.etModeloDetail.getText()).trim();
        String velocidad = String.valueOf(binding.etVelocidadDetail.getText()).trim();
        String estadoLabel = String.valueOf(binding.ddlEstadoDetail.getText());
        if (marca.isEmpty() || modelo.isEmpty() || velocidad.isEmpty()) return;

        router.marca = marca;
        router.modelo = modelo;
        router.velocidad = velocidad;
        router.estado = toEstado(estadoLabel);

        repo.updateRouter(router);
        finish();
    }

    private com.example.lab2.Model.Estado toEstado(String label) {
        String k = label.toLowerCase();
        if (k.startsWith("opera")) return com.example.lab2.Model.Estado.OPERATIVO;
        if (k.startsWith("en rep")) return com.example.lab2.Model.Estado.EN_REPARACION;
        return com.example.lab2.Model.Estado.DADO_DE_BAJA;
    }

    private void confirmDelete(){
        new AlertDialog.Builder(this)
                .setTitle("Eliminar router")
                .setMessage("¿Seguro que deseas eliminar este router?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar", (d, w) -> {
                    repo.deleteRouter(router.id);
                    finish();
                }).show();
    }
}
