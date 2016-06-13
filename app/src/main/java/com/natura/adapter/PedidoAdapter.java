package com.natura.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.murillo.naturaapp.R;
import com.natura.model.ItemPedido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murillo on 11/06/2016.
 */
public class PedidoAdapter extends BaseAdapter {

    private List<ItemPedido> itens;
    private Context ctx;

    public PedidoAdapter(Context context, List<ItemPedido> itens) {
        this.ctx = context;
        this.itens = itens;
    }

    public void atualizarAdapter(Context context, List<ItemPedido> itens) {
        this.ctx = context;
        this.itens = itens;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;

        if(convertView == null) {
            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.linha_activity_produto, null);
        }

        final ItemPedido item = itens.get(position);

        ImageView imvCarrinho = (ImageView) convertView.findViewById(R.id.imvCarrinho);

        if(item.getQuantidade() > 0)
            imvCarrinho.setVisibility(View.VISIBLE);
        else
            imvCarrinho.setVisibility(View.GONE);

        ImageView imvAdicionar = (ImageView) convertView.findViewById(R.id.imvAdicionar);
        imvAdicionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adicionarItem(item, position);
            }
        });

        ImageView imvRemover = (ImageView) convertView.findViewById(R.id.imvRemover);
        imvRemover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                diminuirItem(item, position);
            }
        });

        TextView txvNomeProduto = (TextView) convertView.findViewById(R.id.txvNomeProduto);
        txvNomeProduto.setText(item.getProduto().getNome());

        TextView txvQuantidade = (TextView) convertView.findViewById(R.id.txvQuantidade);
        txvQuantidade.setText(String.format("Qtd: %s", item.getQuantidade().toString()));

        TextView txvValorUnitario = (TextView) convertView.findViewById(R.id.txvValorUnitario);
        txvValorUnitario.setText(String.format("Valor: %.2f", item.getProduto().getValor()));

        TextView txvValorTotal = (TextView) convertView.findViewById(R.id.txvTotal);
        txvValorTotal.setText(String.format("Total: %.2f", item.getValorTotal()));

        return convertView;
    }

    public void adicionarItem(ItemPedido item, int position) {
        Integer qtdVendida = item.getQuantidade() + 1;
        Double totalItem = item.getProduto().getValor() * qtdVendida;
        atribuirItem(qtdVendida, totalItem, item, position);
    }

    public void diminuirItem(ItemPedido item, int posicao) {
        int qtdVendida = item.getQuantidade() - 1;
        if(qtdVendida > 0) {
            Double totalItem = item.getProduto().getValor() * qtdVendida;
            atribuirItem(qtdVendida, totalItem, item, posicao);
        } else if(qtdVendida == 0)
            removerItem(item, posicao);
    }

    public void removerItem(ItemPedido item, int posicao) {
        item.getPedido().getItens().remove(item);
        atribuirItem(0, 0.00, item, posicao);
    }

    public void atribuirItem(Integer qtdVendida, Double totalItem, ItemPedido item, Integer position) {
        item.setQuantidade(qtdVendida);
        item.setValorTotal(totalItem);
        item.getPedido().setItens(item);
        item.getPedido().setTotal(getTotalPedido(item));
        itens.set(position, item);
        atualizarAdapter(ctx, itens);
    }

    public Double getTotalPedido(ItemPedido item) {
        Double total = 0.0;
        for(ItemPedido i: item.getPedido().getItens()) {
            total =+ total + i.getValorTotal();
        }
        return total;
    }
}
