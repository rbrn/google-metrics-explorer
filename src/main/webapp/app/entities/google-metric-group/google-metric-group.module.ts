import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GcpMetricsExplorerSharedModule } from 'app/shared/shared.module';
import { GoogleMetricGroupComponent } from './google-metric-group.component';
import { GoogleMetricGroupDetailComponent } from './google-metric-group-detail.component';
import { GoogleMetricGroupUpdateComponent } from './google-metric-group-update.component';
import { GoogleMetricGroupDeleteDialogComponent } from './google-metric-group-delete-dialog.component';
import { googleMetricGroupRoute } from './google-metric-group.route';

@NgModule({
  imports: [GcpMetricsExplorerSharedModule, RouterModule.forChild(googleMetricGroupRoute)],
  declarations: [
    GoogleMetricGroupComponent,
    GoogleMetricGroupDetailComponent,
    GoogleMetricGroupUpdateComponent,
    GoogleMetricGroupDeleteDialogComponent
  ],
  entryComponents: [GoogleMetricGroupDeleteDialogComponent]
})
export class GcpMetricsExplorerGoogleMetricGroupModule {}
