package com.natura.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.murillo.naturaapp.R;
import com.natura.adapter.ConsultaAdapter;
import com.natura.banco.DbHelper;
import com.natura.model.Pedido;

import java.util.List;

/**
 * Created by Murillo on 12/06/2016.
 */
public class ConsultaActivity extends Activity {

    private ListView ltvConsulta;
    private ConsultaAdapter adapter;
    private List<Pedido> pedidos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        ltvConsulta = (ListView) findViewById(R.id.ltvConsulta);
        carregarPedidos();
    }

    private void carregarPedidos() {
        pedidos = new Pedido().getAll(getBaseContext(), DbHelper.getInstance(getBaseContext()).getReadableDatabase());
        atribuirAdapter();
    }

    private void atribuirAdapter() {
        try {
            if(adapter == null) {
                adapter = new ConsultaAdapter(getBaseContext(), pedidos);
                ltvConsulta.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
