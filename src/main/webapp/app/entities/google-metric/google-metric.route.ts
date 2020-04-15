import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGoogleMetric, GoogleMetric } from 'app/shared/model/google-metric.model';
import { GoogleMetricService } from './google-metric.service';
import { GoogleMetricComponent } from './google-metric.component';
import { GoogleMetricDetailComponent } from './google-metric-detail.component';
import { GoogleMetricUpdateComponent } from './google-metric-update.component';

@Injectable({ providedIn: 'root' })
export class GoogleMetricResolve implements Resolve<IGoogleMetric> {
  constructor(private service: GoogleMetricService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoogleMetric> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((googleMetric: HttpResponse<GoogleMetric>) => {
          if (googleMetric.body) {
            return of(googleMetric.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoogleMetric());
  }
}

export const googleMetricRoute: Routes = [
  {
    path: '',
    component: GoogleMetricComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleMetric.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GoogleMetricDetailComponent,
    resolve: {
      googleMetric: GoogleMetricResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleMetric.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GoogleMetricUpdateComponent,
    resolve: {
      googleMetric: GoogleMetricResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleMetric.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GoogleMetricUpdateComponent,
    resolve: {
      googleMetric: GoogleMetricResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleMetric.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
