package com.example.lab2.Routers;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.lab2.Model.Estado;
import com.example.lab2.databinding.DialogRouterFormBinding;

public class RouterFormDialogFragment extends DialogFragment {

    public interface OnRouterCreated {
        void onCreateRouter(String marca, String modelo, String velocidad, com.example.lab2.Model.Estado estado);
    }

    private OnRouterCreated listener;
    private com.example.lab2.databinding.DialogRouterFormBinding binding;

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnRouterCreated) listener = (OnRouterCreated) context;
        else throw new IllegalStateException("Activity debe implementar OnRouterCreated");
    }

    @NonNull @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = com.example.lab2.databinding.DialogRouterFormBinding.inflate(getLayoutInflater());

        // Adapter de estados
        String[] estados = new String[]{"Operativo","En reparación","Dado de baja"};
        ArrayAdapter<String> adp = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, estados);
        binding.ddlEstado.setAdapter(adp);
        binding.ddlEstado.setText(estados[0], false); // default

        // Fuerza mostrar el menú al tocar o enfocar (por si el sistema no lo abre solo)
        binding.ddlEstado.setOnClickListener(v -> binding.ddlEstado.showDropDown());
        binding.ddlEstado.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) binding.ddlEstado.showDropDown();
        });

        return new AlertDialog.Builder(requireContext())
                .setTitle("Nuevo router")
                .setView(binding.getRoot())
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Guardar", (d, w) -> {
                    String marca = String.valueOf(binding.etMarca.getText()).trim();
                    String modelo = String.valueOf(binding.etModelo.getText()).trim();
                    String velocidad = String.valueOf(binding.etVelocidad.getText()).trim();
                    String estadoLabel = String.valueOf(binding.ddlEstado.getText());
                    if (marca.isEmpty() || modelo.isEmpty() || velocidad.isEmpty()) return;
                    com.example.lab2.Model.Estado estado = toEstado(estadoLabel);
                    if (listener != null) listener.onCreateRouter(marca, modelo, velocidad, estado);
                })
                .create();
    }

    private com.example.lab2.Model.Estado toEstado(String label) {
        String k = label.toLowerCase();
        if (k.startsWith("opera")) return com.example.lab2.Model.Estado.OPERATIVO;
        if (k.startsWith("en rep")) return com.example.lab2.Model.Estado.EN_REPARACION;
        return com.example.lab2.Model.Estado.DADO_DE_BAJA;
    }
}

