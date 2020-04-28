import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ChartWrapper, IGoogleMetric } from 'app/shared/model/google-metric.model';

@Component({
  selector: 'jhi-google-metric-detail',
  templateUrl: './google-metric-detail.component.html'
})
export class GoogleMetricDetailComponent implements OnInit {
  googleMetric: IGoogleMetric | null = null;

  columnNames = ['Date', 'Utilization'];
  options = {
    hAxis: {
      title: 'Date'
    },
    vAxis: {
      title: 'Utilization'
    }
  };
  width = 550;
  height = 400;
  type = 'LineChart';
  data: ChartWrapper[];

  constructor(protected activatedRoute: ActivatedRoute) {
    this.data = [];
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ googleMetric }) => (
        (this.googleMetric = googleMetric),
        this.googleMetric?.data?.forEach(values => {
          if (values != null && values.timeSeriesPoints != null) {
            const datapoints = values.timeSeriesPoints.map(value => [value.startTimeHoursMinutes, value.value]);
            const wrapper = new ChartWrapper(datapoints, values.labelsMap);
            this.data.push(wrapper);
          }
        })
      )
    );
  }

  previousState(): void {
    window.history.back();
  }
}
