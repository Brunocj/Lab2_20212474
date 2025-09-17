package com.example.lab2.Switches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.lab2.Model.Estado;
import com.example.lab2.Model.Switch;
import com.example.lab2.Model.TipoSwitch;
import com.example.lab2.R;
import java.util.List;

public class SwitchAdapter extends BaseAdapter {
    private final List<Switch> data;
    private final LayoutInflater inflater;

    public SwitchAdapter(Context ctx, List<Switch> data){
        this.data = data; this.inflater = LayoutInflater.from(ctx);
    }

    @Override public int getCount(){ return data.size(); }
    @Override public Object getItem(int position){ return data.get(position); }
    @Override public long getItemId(int position){ return position; }

    static class VH {
        TextView tvMarca, tvModelo, tvVelocidad, tvEstado, tvTipo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        VH h;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_switch, parent, false);
            h = new VH();
            h.tvMarca     = convertView.findViewById(R.id.tvMarca);
            h.tvModelo    = convertView.findViewById(R.id.tvModelo);
            h.tvVelocidad = convertView.findViewById(R.id.tvVelocidad);
            h.tvEstado    = convertView.findViewById(R.id.tvEstado);
            h.tvTipo      = convertView.findViewById(R.id.tvTipo);
            convertView.setTag(h);
        } else h = (VH) convertView.getTag();

        Switch s = data.get(position);

        h.tvMarca.setText("Marca: " + (s.marca == null ? "-" : s.marca));
        h.tvModelo.setText("Modelo: " + (s.modelo == null ? "-" : s.modelo));
        h.tvVelocidad.setText("Velocidad: " + (s.velocidad == null || s.velocidad.isEmpty() ? "-" : s.velocidad));

        String estadoStr = (s.estado == Estado.OPERATIVO) ? "Operativo"
                : (s.estado == Estado.EN_REPARACION) ? "En reparación" : "Dado de baja";
        h.tvEstado.setText(estadoStr);
        if (s.estado == Estado.OPERATIVO){
            h.tvEstado.setTextColor(0xFF2E7D32); h.tvEstado.setBackgroundColor(0x1A81C784);
        } else if (s.estado == Estado.EN_REPARACION){
            h.tvEstado.setTextColor(0xFFEF6C00); h.tvEstado.setBackgroundColor(0x1AFFA726);
        } else {
            h.tvEstado.setTextColor(0xFFB71C1C); h.tvEstado.setBackgroundColor(0x1AE53935);
        }

        String tipoStr = (s.tipo == TipoSwitch.ADMINISTRABLE) ? "Administrable" : "No administrable";
        h.tvTipo.setText(tipoStr);

        return convertView;
    }
}
