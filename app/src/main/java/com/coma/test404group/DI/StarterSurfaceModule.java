package com.coma.test404group.DI;


import android.view.View;

import com.coma.test404group.R;
import com.coma.test404group.StarterActivity;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.LabelPlacement;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Koma on 15.11.2017.
 */
@Module(includes= {
        SciBuilderModule.class//extra dependency for injection
})
public class StarterSurfaceModule {
    private final SciChartSurface sciChartSurface;
    @Inject
    SciChartBuilder sciChartBuilder;

    public StarterSurfaceModule(StarterActivity mainActivity) {
        View view = ButterKnife.findById(mainActivity, R.id.chart);
        sciChartSurface = (SciChartSurface) view;
    }

    @Provides
    public SciChartSurface sciChartSurface(SciChartBuilder sciChartBuilder) {
        setUpSurface(sciChartSurface, sciChartBuilder);
        return sciChartSurface;}

    private void setUpSurface(SciChartSurface surface, SciChartBuilder sciChartBuilder){
        //setting up axis
        final NumericAxis xAxis = sciChartBuilder.newNumericAxis()
                .withVisibleRange(0, 30)
                .withVisibleRangeLimit(0, 120)
                .withAxisTitle("Seconds").build();
        final NumericAxis yAxis = sciChartBuilder.newNumericAxis()
                .withVisibleRange(-2, 2)
                .withAxisTitle("Rate").build();

        surface.getXAxes().add(xAxis);
        surface.getYAxes().add(yAxis);
        //input handling
        surface.getChartModifiers().add(sciChartBuilder.newModifierGroupWithDefaultModifiers().build());
        //add horizontal line
        surface.getAnnotations().add(sciChartBuilder.newHorizontalLineAnnotation()
                .withPosition(0d, 0d)
                .withStroke(2, ColorUtil.White)
                .withAnnotationLabel(LabelPlacement.Axis)
                .build());
    }

}
