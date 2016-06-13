package com.natura.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.murillo.naturaapp.R;
import com.natura.model.Pedido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murillo on 12/06/2016.
 */
public class ConsultaAdapter extends BaseAdapter {

    private Context context;
    private List<Pedido> pedidos = new ArrayList<Pedido>();

    public ConsultaAdapter(Context context, List<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;

        if(convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.linha_activity_consulta, null);
        }

        convertView.setBackgroundResource(position % 2 == 0 ? R.drawable.alterselector_light1 : R.drawable.alterselector_light2);

        Pedido pedido = pedidos.get(position);

        TextView txvPedido = (TextView) convertView.findViewById(R.id.txvLinhaConsultaPedido);
        txvPedido.setText(String.format("Pedido nº%s", pedido.getId().toString()));

        TextView txvCliente = (TextView) convertView.findViewById(R.id.txvLinhaConsultaCliente);
        txvCliente.setText(pedido.getCliente().getNome());

        TextView txvTotal = (TextView) convertView.findViewById(R.id.txvLinhaConsultaTotal);
        txvTotal.setText(String.format("Total: R$ %.2f", pedido.getTotal()));

        return convertView;
    }
}
