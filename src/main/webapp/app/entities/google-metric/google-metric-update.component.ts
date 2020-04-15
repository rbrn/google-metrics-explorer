import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGoogleMetric, GoogleMetric } from 'app/shared/model/google-metric.model';
import { GoogleMetricService } from './google-metric.service';
import { IGoogleMetricGroup } from 'app/shared/model/google-metric-group.model';
import { GoogleMetricGroupService } from 'app/entities/google-metric-group/google-metric-group.service';

@Component({
  selector: 'jhi-google-metric-update',
  templateUrl: './google-metric-update.component.html'
})
export class GoogleMetricUpdateComponent implements OnInit {
  isSaving = false;
  googlemetricgroups: IGoogleMetricGroup[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    metricGroupId: []
  });

  constructor(
    protected googleMetricService: GoogleMetricService,
    protected googleMetricGroupService: GoogleMetricGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ googleMetric }) => {
      this.updateForm(googleMetric);

      this.googleMetricGroupService
        .query()
        .subscribe((res: HttpResponse<IGoogleMetricGroup[]>) => (this.googlemetricgroups = res.body || []));
    });
  }

  updateForm(googleMetric: IGoogleMetric): void {
    this.editForm.patchValue({
      id: googleMetric.id,
      name: googleMetric.name,
      description: googleMetric.description,
      metricGroupId: googleMetric.metricGroupId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const googleMetric = this.createFromForm();
    if (googleMetric.id !== undefined) {
      this.subscribeToSaveResponse(this.googleMetricService.update(googleMetric));
    } else {
      this.subscribeToSaveResponse(this.googleMetricService.create(googleMetric));
    }
  }

  private createFromForm(): IGoogleMetric {
    return {
      ...new GoogleMetric(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      metricGroupId: this.editForm.get(['metricGroupId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoogleMetric>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IGoogleMetricGroup): any {
    return item.id;
  }
}
