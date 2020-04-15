import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGoogleProject, GoogleProject } from 'app/shared/model/google-project.model';
import { GoogleProjectService } from './google-project.service';
import { IGoogleMetric } from 'app/shared/model/google-metric.model';
import { GoogleMetricService } from 'app/entities/google-metric/google-metric.service';

@Component({
  selector: 'jhi-google-project-update',
  templateUrl: './google-project-update.component.html'
})
export class GoogleProjectUpdateComponent implements OnInit {
  isSaving = false;
  googlemetrics: IGoogleMetric[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    googleId: [null, [Validators.required]],
    googleMetricId: []
  });

  constructor(
    protected googleProjectService: GoogleProjectService,
    protected googleMetricService: GoogleMetricService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ googleProject }) => {
      this.updateForm(googleProject);

      this.googleMetricService.query().subscribe((res: HttpResponse<IGoogleMetric[]>) => (this.googlemetrics = res.body || []));
    });
  }

  updateForm(googleProject: IGoogleProject): void {
    this.editForm.patchValue({
      id: googleProject.id,
      name: googleProject.name,
      googleId: googleProject.googleId,
      googleMetricId: googleProject.googleMetricId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const googleProject = this.createFromForm();
    if (googleProject.id !== undefined) {
      this.subscribeToSaveResponse(this.googleProjectService.update(googleProject));
    } else {
      this.subscribeToSaveResponse(this.googleProjectService.create(googleProject));
    }
  }

  private createFromForm(): IGoogleProject {
    return {
      ...new GoogleProject(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      googleId: this.editForm.get(['googleId'])!.value,
      googleMetricId: this.editForm.get(['googleMetricId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoogleProject>>): void {
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

  trackById(index: number, item: IGoogleMetric): any {
    return item.id;
  }
}
