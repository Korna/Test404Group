package com.coma.test404group.Service;


import com.coma.test404group.Logic.PointGenerator;
import com.coma.test404group.Model.Point;


/**
 * Created by Koma on 15.11.2017.
 */

public class GeneratorService extends PointService {
    PointGenerator pg = new PointGenerator();
    @Override
    public Point getPoint() {
        pg.iteration();
        return pg.getLastPoint();
    }

    /**filler*/
    @Override
    public boolean isPointLoaded() {
        return true;
    }



}
