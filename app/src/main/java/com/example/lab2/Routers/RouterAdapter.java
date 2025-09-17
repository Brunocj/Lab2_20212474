package com.example.lab2.Routers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lab2.Model.Estado;
import com.example.lab2.Model.Router;
import com.example.lab2.R;

import java.util.List;

public class RouterAdapter extends BaseAdapter {
    private final List<Router> data;
    private final LayoutInflater inflater;

    public RouterAdapter(Context ctx, List<Router> data){
        this.data = data; this.inflater = LayoutInflater.from(ctx);
    }

    @Override public int getCount(){ return data.size(); }
    @Override public Object getItem(int position){ return data.get(position); }
    @Override public long getItemId(int position){ return position; }

    static class VH { TextView tvMarca, tvModelo, tvVelocidad, tvEstado; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        VH h;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_router, parent, false);
            h = new VH();
            h.tvMarca     = convertView.findViewById(R.id.tvMarca);
            h.tvModelo    = convertView.findViewById(R.id.tvModelo);
            h.tvVelocidad = convertView.findViewById(R.id.tvVelocidad);
            h.tvEstado    = convertView.findViewById(R.id.tvEstado);
            convertView.setTag(h);
        } else h = (VH) convertView.getTag();

        Router r = data.get(position);

        // Marca / Modelo en líneas separadas
        h.tvMarca.setText("Marca: " + (r.marca == null ? "-" : r.marca));
        h.tvModelo.setText("Modelo: " + (r.modelo == null ? "-" : r.modelo));

        // Velocidad
        h.tvVelocidad.setText("Velocidad: " + (r.velocidad == null || r.velocidad.isEmpty() ? "-" : r.velocidad));

        // Estado (texto + colorcito)
        String estadoStr = (r.estado == com.example.lab2.Model.Estado.OPERATIVO) ? "Operativo"
                : (r.estado == com.example.lab2.Model.Estado.EN_REPARACION) ? "En reparación"
                : "Dado de baja";
        h.tvEstado.setText(estadoStr);

        if (r.estado == com.example.lab2.Model.Estado.OPERATIVO){
            h.tvEstado.setTextColor(0xFF2E7D32); h.tvEstado.setBackgroundColor(0x1A81C784);
        } else if (r.estado == com.example.lab2.Model.Estado.EN_REPARACION){
            h.tvEstado.setTextColor(0xFFEF6C00); h.tvEstado.setBackgroundColor(0x1AFFA726);
        } else {
            h.tvEstado.setTextColor(0xFFB71C1C); h.tvEstado.setBackgroundColor(0x1AE53935);
        }

        return convertView;
    }
}