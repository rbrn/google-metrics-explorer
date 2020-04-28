package com.db.pwcc.tre.metrics.web.rest;

import com.db.pwcc.tre.metrics.service.dto.GoogleMetricDTO;
import com.google.cloud.monitoring.v3.MetricServiceClient;
import com.google.monitoring.v3.*;
import com.google.protobuf.Duration;
import com.google.protobuf.util.Timestamps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class GoogleMetricCustomService {

    Logger logger = LoggerFactory.getLogger(GoogleMetricCustomService.class);

    /**
     * Demonstrates listing time series and aggregating them.
     *
     * @param googleMetricDTO
     * @return
     */
    public List<TimeSeriesWrapper> listTimeSeriesAggregate(Optional<GoogleMetricDTO> googleMetricDTO) throws IOException {
        // [START monitoring_read_timeseries_align]
        MetricServiceClient metricServiceClient = MetricServiceClient.create();
        String projectId = "dev1-onb-dbhc-monitoring-e561";
        ProjectName name = ProjectName.of(projectId);

        // Restrict time to last 20 minutes
        long startMillis = System.currentTimeMillis() - ((60 * 120) * 1000);
        TimeInterval interval =
            TimeInterval.newBuilder()
                .setStartTime(Timestamps.fromMillis(startMillis))
                .setEndTime(Timestamps.fromMillis(System.currentTimeMillis()))
                .build();
        Aggregation aggregation =
            Aggregation.newBuilder()
                .setAlignmentPeriod(Duration.newBuilder().setSeconds(600).build())
                .setPerSeriesAligner(Aggregation.Aligner.ALIGN_MEAN)
                .setCrossSeriesReducer(Aggregation.Reducer.REDUCE_NONE)
                .build();
// will receive projects/dev1-onb-dbhc-monitoring-e561/metricDescriptors/agent.googleapis.com/agent/log_entry_retry_count
        //will expect compute.googleapis.com/instance/cpu/utilization
        ListTimeSeriesRequest.Builder requestBuilder =
            ListTimeSeriesRequest.newBuilder()
                .setName(name.toString())
                .setFilter(String.format("metric.type=\"%s\"", googleMetricDTO.get().getName()))
                .setInterval(interval);
        ListTimeSeriesRequest request = requestBuilder.build();

        MetricServiceClient.ListTimeSeriesPagedResponse response = metricServiceClient.listTimeSeries(request);


        List<TimeSeriesWrapper> timeSeries = new ArrayList<>();
        for (TimeSeries ts : response.iterateAll()) {
            System.out.println(ts.toString());
            List<TimeSeriesPoint> timeSeriesPoints = ts.getPointsList().stream().map(
                this::mapFromPoint
            ).collect(toList());
            timeSeries.add(   new TimeSeriesWrapper(timeSeriesPoints, ts.getMetric().getLabelsMap()));
        }

        logger.info("Got {} timeseries", timeSeries.size());
        // [END monitoring_read_timeseries_align]
        return timeSeries;
    }

    private <R> TimeSeriesPoint mapFromPoint(Point point) {
       Instant start = Instant
            .ofEpochSecond( point.getInterval().getStartTime().getSeconds() , point.getInterval().getStartTime().getNanos() )
            .atZone(ZoneId.systemDefault())
            .toInstant();

        Instant end = Instant
            .ofEpochSecond( point.getInterval().getEndTime().getSeconds() , point.getInterval().getEndTime().getNanos() )
            .atZone(ZoneId.systemDefault())
            .toInstant();
        return new TimeSeriesPoint(start,  point.getValue().getDoubleValue(), LocalDateTime.ofInstant(start, ZoneId.systemDefault()).getMinute());
    }

    /**
     * Demonstrates listing time series and aggregating and reducing them.
     */
    void listTimeSeriesReduce() throws IOException {
        // [START monitoring_read_timeseries_reduce]
        MetricServiceClient metricServiceClient = MetricServiceClient.create();
        String projectId = System.getProperty("projectId");
        ProjectName name = ProjectName.of(projectId);

        // Restrict time to last 20 minutes
        long startMillis = System.currentTimeMillis() - ((60 * 20) * 1000);
        TimeInterval interval =
            TimeInterval.newBuilder()
                .setStartTime(Timestamps.fromMillis(startMillis))
                .setEndTime(Timestamps.fromMillis(System.currentTimeMillis()))
                .build();

        Aggregation aggregation =
            Aggregation.newBuilder()
                .setAlignmentPeriod(Duration.newBuilder().setSeconds(600).build())
                .setPerSeriesAligner(Aggregation.Aligner.ALIGN_MEAN)
                .setCrossSeriesReducer(Aggregation.Reducer.REDUCE_MEAN)
                .build();

        ListTimeSeriesRequest.Builder requestBuilder =
            ListTimeSeriesRequest.newBuilder()
                .setName(name.toString())
                .setFilter("metric.type=\"compute.googleapis.com/instance/cpu/utilization\"")
                .setInterval(interval)
                .setAggregation(aggregation);

        ListTimeSeriesRequest request = requestBuilder.build();

        MetricServiceClient.ListTimeSeriesPagedResponse response = metricServiceClient.listTimeSeries(request);

        System.out.println("Got timeseries: ");
        for (TimeSeries ts : response.iterateAll()) {
            System.out.println(ts);
        }
        // [END monitoring_read_timeseries_reduce]
    }
}
