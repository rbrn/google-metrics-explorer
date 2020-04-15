import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GcpMetricsExplorerSharedModule } from 'app/shared/shared.module';
import { GoogleProjectComponent } from './google-project.component';
import { GoogleProjectDetailComponent } from './google-project-detail.component';
import { GoogleProjectUpdateComponent } from './google-project-update.component';
import { GoogleProjectDeleteDialogComponent } from './google-project-delete-dialog.component';
import { googleProjectRoute } from './google-project.route';

@NgModule({
  imports: [GcpMetricsExplorerSharedModule, RouterModule.forChild(googleProjectRoute)],
  declarations: [GoogleProjectComponent, GoogleProjectDetailComponent, GoogleProjectUpdateComponent, GoogleProjectDeleteDialogComponent],
  entryComponents: [GoogleProjectDeleteDialogComponent]
})
export class GcpMetricsExplorerGoogleProjectModule {}
