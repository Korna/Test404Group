package com.coma.test404group.Logic;

import com.coma.test404group.Model.Point;

import java.util.Date;
import java.util.Random;

/**
 * Created by Koma on 16.11.2017.
 */

public class PointGenerator {
    private final static long INCREMENT_PER_GENERATION = 1L;

    private final Random random = new Random();
    private Point<Double, Date> lastPoint = new Point(0.0, new Date(0L));

    public Point getLastPoint() {
        return lastPoint;
    }

    public Point iteration(){
        lastPoint.setRate(lastPoint.getRate() + getRandomRate());
        lastPoint.setDate(getDate(lastPoint.getDate().getTime()));
        return lastPoint;
    }

    private Double getRandomRate(){
        Double result;
        //generates value from -1 to 1
        if(random.nextBoolean())
            result = random.nextDouble();
        else
            result = -random.nextDouble();
        return  result;
    }

    private Date getDate(Long lastDate){
        Date date = new Date(lastDate + INCREMENT_PER_GENERATION);
        return date;
    }


}
