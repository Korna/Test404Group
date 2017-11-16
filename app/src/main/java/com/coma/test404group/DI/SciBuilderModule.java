package com.coma.test404group.DI;

import android.content.Context;

import com.scichart.extensions.builders.SciChartBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Koma on 15.11.2017.
 */
@Module
public class SciBuilderModule {

    SciChartBuilder sciChartBuilder;

    public SciBuilderModule(Context context) {
        SciChartBuilder.init(context);
        this.sciChartBuilder = SciChartBuilder.instance();

    }

    @Provides
    @Singleton
    public SciChartBuilder sciChartBuilder() {
        return sciChartBuilder;
    }
}
