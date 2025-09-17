package com.example.lab2.Switches;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.lab2.Model.Estado;
import com.example.lab2.Model.TipoSwitch;
import com.example.lab2.databinding.DialogSwitchFormBinding;

public class SwitchFormDialogFragment extends DialogFragment {

    public interface OnSwitchCreated {
        void onCreateSwitch(String marca, String modelo, String velocidad, Estado estado, TipoSwitch tipo);
    }

    private OnSwitchCreated listener;
    private DialogSwitchFormBinding binding;

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSwitchCreated) listener = (OnSwitchCreated) context;
        else throw new IllegalStateException("Activity debe implementar OnSwitchCreated");
    }

    @NonNull @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogSwitchFormBinding.inflate(getLayoutInflater());

        String[] estados = new String[]{"Operativo","En reparación","Dado de baja"};
        String[] tipos = new String[]{"Administrable","No administrable"};

        ArrayAdapter<String> adpEstado = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, estados);
        ArrayAdapter<String> adpTipo   = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, tipos);

        binding.ddlEstado.setAdapter(adpEstado);
        binding.ddlTipo.setAdapter(adpTipo);

        // defaults
        binding.ddlEstado.setText(estados[0], false);
        binding.ddlTipo.setText(tipos[0], false);

        // forzar despliegue al tocar/enfocar
        binding.ddlEstado.setOnClickListener(v -> binding.ddlEstado.showDropDown());
        binding.ddlEstado.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) binding.ddlEstado.showDropDown(); });

        binding.ddlTipo.setOnClickListener(v -> binding.ddlTipo.showDropDown());
        binding.ddlTipo.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) binding.ddlTipo.showDropDown(); });

        return new AlertDialog.Builder(requireContext())
                .setTitle("Nuevo switch")
                .setView(binding.getRoot())
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Guardar", (d, w) -> {
                    String marca = String.valueOf(binding.etMarca.getText()).trim();
                    String modelo = String.valueOf(binding.etModelo.getText()).trim();
                    String velocidad = String.valueOf(binding.etVelocidad.getText()).trim();
                    String estadoLabel = String.valueOf(binding.ddlEstado.getText());
                    String tipoLabel = String.valueOf(binding.ddlTipo.getText());
                    if (marca.isEmpty() || modelo.isEmpty() || velocidad.isEmpty()) return;
                    Estado estado = toEstado(estadoLabel);
                    TipoSwitch tipo = toTipo(tipoLabel);
                    if (listener != null) listener.onCreateSwitch(marca, modelo, velocidad, estado, tipo);
                })
                .create();
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
