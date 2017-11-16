package com.coma.test404group.Model;

import java.io.Serializable;

/**
 * Created by Koma on 15.11.2017.
 */

public class Point<X,Y> implements Serializable {
    private X rate;
    private Y date;

    public Point() {
    }

    public Point(X rate, Y date) {
        this.rate = rate;
        this.date = date;
    }

    public X getRate() {
        return rate;
    }

    public void setRate(X rate) {
        this.rate = rate;
    }

    public Y getDate() {
        return date;
    }

    public void setDate(Y date) {
        this.date = date;
    }
}
