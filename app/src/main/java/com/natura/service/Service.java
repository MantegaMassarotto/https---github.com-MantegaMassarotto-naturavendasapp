package com.natura.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Murillo on 12/06/2016.
 */
public abstract class Service<T, DTO> {

    protected Context context;

    public Service(Context context) {
        this.context = context;
    }

    protected abstract List<T> getDados(RestTemplate restTemplate);
    protected abstract List<T> getLista(DTO[] listDto);

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
