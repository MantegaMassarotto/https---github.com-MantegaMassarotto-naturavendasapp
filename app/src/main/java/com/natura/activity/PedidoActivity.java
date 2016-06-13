package com.natura.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.murillo.naturaapp.R;
import com.natura.adapter.PedidoAdapter;
import com.natura.banco.DbHelper;
import com.natura.exception.PedidoException;
import com.natura.model.Cliente;
import com.natura.model.ItemPedido;
import com.natura.model.Pedido;
import com.natura.model.Produto;
import com.natura.service.ClienteService;
import com.natura.service.ProdutoService;

import io.fabric.sdk.android.Fabric;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private PedidoAdapter produtoAdapter;
    private List<Cliente> clienteList = new ArrayList<Cliente>();
    private ListView ltv;
    private Spinner spnCliente;
    private ImageView imvAdicionar;
    private ImageView imvRemover;

    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_pedido);
        ltv = (ListView) findViewById(R.id.ltv_produtos);
        spnCliente = (Spinner) findViewById(R.id.spnCliente);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        fab.setOnClickListener(this);
        spnCliente.setOnItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        novoPedido();
    }

    private void novoPedido() {
        pedido = new Pedido();
        carregarItensDoPedido(null);
        carregarClientes(null);
    }

    private void carregarClientes(List<Cliente> clientes) {
        if(clientes == null || clientes.isEmpty()) {
            SQLiteDatabase db = DbHelper.getInstance(getBaseContext()).getReadableDatabase();
            clientes = new Cliente().getAll(getBaseContext(), db);
            db.close();
        }
        clienteList = clientes;
        atribuirAdapterSpinnerCliente(clientes);
    }

    private void salvarPedido() {
        try {
            if(spnCliente.getSelectedItemPosition() == 0)
                throw new PedidoException("Selecione um cliente");
            if(!pedido.getItens().isEmpty())
                if(pedido.save(DbHelper.getInstance(getBaseContext()).getWritableDatabase(), getBaseContext()))
                    Toast.makeText(getBaseContext(), "Pedido Salvo", Toast.LENGTH_LONG).show();
                else
                    throw new PedidoException("Não foi possível salvar o pedido");
        } catch (PedidoException pe) {
            Toast.makeText(getBaseContext(), pe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private List<String> getListaNomeClientes(List<Cliente> clientes) {
        List<String> nomeClientes = new ArrayList<String>();
        Cliente cliente = new Cliente();
        nomeClientes.clear();
        nomeClientes.add("Selecione o cliente");
        for(Cliente c : clientes) {
            nomeClientes.add(c.getNome());
        }
        return nomeClientes;
    }

    private void atribuirAdapterSpinnerCliente(List<Cliente> clientes) {
        spnCliente.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, getListaNomeClientes(clientes)));
    }

    private void carregarItensDoPedido(List<Produto> produtos) {
        atribuirAdapter(montaItensDoPedido(produtos));
    }

    public List<ItemPedido> montaItensDoPedido(List<Produto> produtos) {
        Produto produto = new Produto();
        List<ItemPedido> itens = new ArrayList<ItemPedido>();
        List<ItemPedido> itensDoPedido = new ArrayList<ItemPedido>();

        if(produtos == null || produtos.isEmpty()) {
            SQLiteDatabase db = DbHelper.getInstance(getBaseContext()).getReadableDatabase();
            produtos = produto.getAll(getBaseContext(), db);
            db.close();
        }

        for(Produto p : produtos) {
            ItemPedido i = new ItemPedido();
            i.setProduto(p);
            i.setPedido(pedido);
            itensDoPedido.add(i);
        }
        return itensDoPedido;
    }

    public void atribuirAdapter(List<ItemPedido> itensDoPedido) {
        if(produtoAdapter == null) {
            produtoAdapter = new PedidoAdapter(getBaseContext(), itensDoPedido);
            ltv.setAdapter(produtoAdapter);
        } else
            produtoAdapter.atualizarAdapter(getBaseContext(), itensDoPedido);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.produto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_novo:
                novoPedido();
                return true;
            case R.id.action_atualizar:
                if(pedido.getItens().isEmpty()) {
                    BuscaDadosService buscaDadosService = new BuscaDadosService(this);
                    buscaDadosService.execute();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_consulta:
                Intent it = new Intent("Consulta");
                it.addCategory("NaturaApp");
                startActivity(it);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spnCliente:
                if(position > 0)
                    pedido.setCliente(clienteList.get(position -1));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                salvarPedido();
        }
    }

    public class BuscaDadosService extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private Context context;
        private List<Produto> produtos = new ArrayList<Produto>();
        private List<Cliente> clientes = new ArrayList<Cliente>();

        public BuscaDadosService(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            carregarProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                produtos = new ProdutoService(context).getDados(restTemplate);
                clientes = new ClienteService(context).getDados(restTemplate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                inserirItens();
                carregarItensDoPedido(produtos);
                carregarClientes(clientes);
            }
            if (progressDialog.isShowing())
                progressDialog.cancel();
        }

        private void inserirItens() {
            SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
            db.beginTransaction();
            try {
                inserirClientes(db);
                inserirProdutos(db);
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), e.getMessage());
            } finally {
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
            }
        }

        public void inserirProdutos(SQLiteDatabase db) {
            for(Produto p : produtos)
                p.save(db, context);
        }

        public void inserirClientes(SQLiteDatabase db) {
            for(Cliente c : clientes)
                c.save(db, context);
        }

        private void carregarProgressDialog() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Carregando...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }
}
