package com.natura.builder;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Murillo on 13/06/2016.
 */
public class UrlServiceBuilder {

    private String nomeServico;
    private String operacao;
    private Context context;

    public UrlServiceBuilder(Context context) {
        this.context = context;
    }

    public UrlServiceBuilder nomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
        return this;
    }

    public UrlServiceBuilder operacao(String operacao) {
        this.operacao = operacao;
        return this;
    }

    public String build() {
        StringBuilder url = new StringBuilder();
        Properties props = new Properties();
        try {
            props.load(context.getAssets().open("conexao.properties"));

            url.append(props.getProperty("prop.server.host"));
            url.append("/" + nomeServico + "/");
            url.append("/" + operacao + "/");
            return url.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url.toString();
    }
}
