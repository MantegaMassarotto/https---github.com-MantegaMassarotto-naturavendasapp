package com.natura.model;

import com.natura.interfaces.Model;

/**
 * Created by Murillo on 11/06/2016.
 */
public abstract class BaseModel<T> implements Model<T> {

    private Long id;
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id == null ? 0 : id;
    }
}
