import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GcpMetricsExplorerSharedModule } from 'app/shared/shared.module';
import { GoogleMetricComponent } from './google-metric.component';
import { GoogleMetricDetailComponent } from './google-metric-detail.component';
import { GoogleMetricUpdateComponent } from './google-metric-update.component';
import { GoogleMetricDeleteDialogComponent } from './google-metric-delete-dialog.component';
import { googleMetricRoute } from './google-metric.route';
import { GoogleChartsModule } from 'angular-google-charts';

@NgModule({
  imports: [GcpMetricsExplorerSharedModule, GoogleChartsModule, RouterModule.forChild(googleMetricRoute)],
  declarations: [GoogleMetricComponent, GoogleMetricDetailComponent, GoogleMetricUpdateComponent, GoogleMetricDeleteDialogComponent],
  entryComponents: [GoogleMetricDeleteDialogComponent]
})
export class GcpMetricsExplorerGoogleMetricModule {}
