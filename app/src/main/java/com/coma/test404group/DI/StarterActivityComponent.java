package com.coma.test404group.DI;


import com.coma.test404group.StarterActivity;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.extensions.builders.SciChartBuilder;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Koma on 15.11.2017.
 */

@Component(modules={
        StarterSurfaceModule.class
})
@Singleton
public interface StarterActivityComponent {
    SciChartSurface sciChartSurface();
    SciChartBuilder sciChartBuilder();
    void inject(StarterActivity activity);
}
