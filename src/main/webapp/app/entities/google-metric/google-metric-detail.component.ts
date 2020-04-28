import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoogleMetric } from 'app/shared/model/google-metric.model';

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
  data: any;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ googleMetric }) => (
        (this.googleMetric = googleMetric), (this.data = this.googleMetric?.data?.map(value => [value.startTimeHoursMinutes, value.value]))
      )
    );
  }

  previousState(): void {
    window.history.back();
  }
}
