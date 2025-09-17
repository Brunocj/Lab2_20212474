package com.example.lab2.APs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.lab2.Model.AccessPoint;
import com.example.lab2.Model.Estado;
import com.example.lab2.R;
import java.util.List;

public class ApAdapter extends BaseAdapter {
    private final List<AccessPoint> data;
    private final LayoutInflater inflater;

    public ApAdapter(Context ctx, List<AccessPoint> data){
        this.data = data; this.inflater = LayoutInflater.from(ctx);
    }

    @Override public int getCount(){ return data.size(); }
    @Override public Object getItem(int position){ return data.get(position); }
    @Override public long getItemId(int position){ return position; }

    static class VH { TextView tvMarca, tvModelo, tvVelocidad, tvEstado, tvBanda; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        VH h;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_ap, parent, false);
            h = new VH();
            h.tvMarca     = convertView.findViewById(R.id.tvMarca);
            h.tvModelo    = convertView.findViewById(R.id.tvModelo);
            h.tvVelocidad = convertView.findViewById(R.id.tvVelocidad);
            h.tvEstado    = convertView.findViewById(R.id.tvEstado);
            h.tvBanda     = convertView.findViewById(R.id.tvBanda);
            convertView.setTag(h);
        } else h = (VH) convertView.getTag();

        AccessPoint a = data.get(position);

        h.tvMarca.setText("Marca: " + (a.marca == null ? "-" : a.marca));
        h.tvModelo.setText("Modelo: " + (a.modelo == null ? "-" : a.modelo));
        h.tvVelocidad.setText("Velocidad: " + (a.velocidad == null || a.velocidad.isEmpty() ? "-" : a.velocidad));

        String estadoStr = (a.estado == Estado.OPERATIVO) ? "Operativo"
                : (a.estado == Estado.EN_REPARACION) ? "En reparación" : "Dado de baja";
        h.tvEstado.setText(estadoStr);
        if (a.estado == Estado.OPERATIVO){
            h.tvEstado.setTextColor(0xFF2E7D32); h.tvEstado.setBackgroundColor(0x1A81C784);
        } else if (a.estado == Estado.EN_REPARACION){
            h.tvEstado.setTextColor(0xFFEF6C00); h.tvEstado.setBackgroundColor(0x1AFFA726);
        } else {
            h.tvEstado.setTextColor(0xFFB71C1C); h.tvEstado.setBackgroundColor(0x1AE53935);
        }

        h.tvBanda.setText(a.banda == null || a.banda.isEmpty() ? "-" : a.banda);

        return convertView;
    }
}