package com.animedataminingapp;

import java.math.BigDecimal;

/**
 * Created by frang on 4/18/2017.
 */

public class Imagen {
    String data;
    BigDecimal size;

    public Imagen(String data, BigDecimal size) {
        this.data = data;
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }
}
