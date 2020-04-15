import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoogleProject } from 'app/shared/model/google-project.model';
import { GoogleProjectService } from './google-project.service';

@Component({
  templateUrl: './google-project-delete-dialog.component.html'
})
export class GoogleProjectDeleteDialogComponent {
  googleProject?: IGoogleProject;

  constructor(
    protected googleProjectService: GoogleProjectService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.googleProjectService.delete(id).subscribe(() => {
      this.eventManager.broadcast('googleProjectListModification');
      this.activeModal.close();
    });
  }
}
