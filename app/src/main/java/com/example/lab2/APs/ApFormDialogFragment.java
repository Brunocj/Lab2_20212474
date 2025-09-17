package com.example.lab2.APs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.lab2.Model.Estado;
import com.example.lab2.databinding.DialogApFormBinding;

public class ApFormDialogFragment extends DialogFragment {

    public interface OnApCreated {
        void onCreateAp(String marca, String modelo, String velocidad, Estado estado, String banda);
    }

    private OnApCreated listener;
    private DialogApFormBinding binding;

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnApCreated) listener = (OnApCreated) context;
        else throw new IllegalStateException("Activity debe implementar OnApCreated");
    }

    @NonNull @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogApFormBinding.inflate(getLayoutInflater());

        String[] estados = new String[]{"Operativo","En reparación","Dado de baja"};
        String[] bandas  = new String[]{"2.4 GHz","5 GHz","Dual Band"};

        ArrayAdapter<String> adpEstado = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, estados);
        ArrayAdapter<String> adpBanda  = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, bandas);

        binding.ddlEstado.setAdapter(adpEstado);
        binding.ddlBanda.setAdapter(adpBanda);

        // defaults
        binding.ddlEstado.setText(estados[0], false);
        binding.ddlBanda.setText(bandas[0], false);

        // forzar despliegue al tocar/enfocar
        binding.ddlEstado.setOnClickListener(v -> binding.ddlEstado.showDropDown());
        binding.ddlEstado.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) binding.ddlEstado.showDropDown(); });

        binding.ddlBanda.setOnClickListener(v -> binding.ddlBanda.showDropDown());
        binding.ddlBanda.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) binding.ddlBanda.showDropDown(); });

        return new AlertDialog.Builder(requireContext())
                .setTitle("Nuevo Access Point")
                .setView(binding.getRoot())
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Guardar", (d, w) -> {
                    String marca = String.valueOf(binding.etMarca.getText()).trim();
                    String modelo = String.valueOf(binding.etModelo.getText()).trim();
                    String velocidad = String.valueOf(binding.etVelocidad.getText()).trim();
                    String estadoLabel = String.valueOf(binding.ddlEstado.getText());
                    String banda = String.valueOf(binding.ddlBanda.getText());
                    if (marca.isEmpty() || modelo.isEmpty() || velocidad.isEmpty()) return;
                    Estado estado = toEstado(estadoLabel);
                    if (listener != null) listener.onCreateAp(marca, modelo, velocidad, estado, banda);
                })
                .create();
    }

    private Estado toEstado(String label) {
        String k = label.toLowerCase();
        if (k.startsWith("opera")) return Estado.OPERATIVO;
        if (k.startsWith("en rep")) return Estado.EN_REPARACION;
        return Estado.DADO_DE_BAJA;
    }
}
