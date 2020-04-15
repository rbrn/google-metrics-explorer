import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'google-metric-group',
        loadChildren: () =>
          import('./google-metric-group/google-metric-group.module').then(m => m.GcpMetricsExplorerGoogleMetricGroupModule)
      },
      {
        path: 'google-metric',
        loadChildren: () => import('./google-metric/google-metric.module').then(m => m.GcpMetricsExplorerGoogleMetricModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GcpMetricsExplorerEntityModule {}
