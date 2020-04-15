import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoogleMetricGroup } from 'app/shared/model/google-metric-group.model';
import { GoogleMetricGroupService } from './google-metric-group.service';

@Component({
  templateUrl: './google-metric-group-delete-dialog.component.html'
})
export class GoogleMetricGroupDeleteDialogComponent {
  googleMetricGroup?: IGoogleMetricGroup;

  constructor(
    protected googleMetricGroupService: GoogleMetricGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.googleMetricGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('googleMetricGroupListModification');
      this.activeModal.close();
    });
  }
}
