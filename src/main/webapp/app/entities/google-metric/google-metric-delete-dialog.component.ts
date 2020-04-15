import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoogleMetric } from 'app/shared/model/google-metric.model';
import { GoogleMetricService } from './google-metric.service';

@Component({
  templateUrl: './google-metric-delete-dialog.component.html'
})
export class GoogleMetricDeleteDialogComponent {
  googleMetric?: IGoogleMetric;

  constructor(
    protected googleMetricService: GoogleMetricService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.googleMetricService.delete(id).subscribe(() => {
      this.eventManager.broadcast('googleMetricListModification');
      this.activeModal.close();
    });
  }
}
