import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoogleMetricGroup } from 'app/shared/model/google-metric-group.model';

@Component({
  selector: 'jhi-google-metric-group-detail',
  templateUrl: './google-metric-group-detail.component.html'
})
export class GoogleMetricGroupDetailComponent implements OnInit {
  googleMetricGroup: IGoogleMetricGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ googleMetricGroup }) => (this.googleMetricGroup = googleMetricGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
