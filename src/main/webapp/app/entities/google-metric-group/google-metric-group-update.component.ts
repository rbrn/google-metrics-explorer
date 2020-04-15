import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGoogleMetricGroup, GoogleMetricGroup } from 'app/shared/model/google-metric-group.model';
import { GoogleMetricGroupService } from './google-metric-group.service';

@Component({
  selector: 'jhi-google-metric-group-update',
  templateUrl: './google-metric-group-update.component.html'
})
export class GoogleMetricGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    googleId: [null, [Validators.required]]
  });

  constructor(
    protected googleMetricGroupService: GoogleMetricGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ googleMetricGroup }) => {
      this.updateForm(googleMetricGroup);
    });
  }

  updateForm(googleMetricGroup: IGoogleMetricGroup): void {
    this.editForm.patchValue({
      id: googleMetricGroup.id,
      name: googleMetricGroup.name,
      googleId: googleMetricGroup.googleId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const googleMetricGroup = this.createFromForm();
    if (googleMetricGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.googleMetricGroupService.update(googleMetricGroup));
    } else {
      this.subscribeToSaveResponse(this.googleMetricGroupService.create(googleMetricGroup));
    }
  }

  private createFromForm(): IGoogleMetricGroup {
    return {
      ...new GoogleMetricGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      googleId: this.editForm.get(['googleId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoogleMetricGroup>>): void {
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
}
