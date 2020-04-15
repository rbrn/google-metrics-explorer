import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGoogleMetricGroup, GoogleMetricGroup } from 'app/shared/model/google-metric-group.model';
import { GoogleMetricGroupService } from './google-metric-group.service';
import { GoogleMetricGroupComponent } from './google-metric-group.component';
import { GoogleMetricGroupDetailComponent } from './google-metric-group-detail.component';
import { GoogleMetricGroupUpdateComponent } from './google-metric-group-update.component';

@Injectable({ providedIn: 'root' })
export class GoogleMetricGroupResolve implements Resolve<IGoogleMetricGroup> {
  constructor(private service: GoogleMetricGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoogleMetricGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((googleMetricGroup: HttpResponse<GoogleMetricGroup>) => {
          if (googleMetricGroup.body) {
            return of(googleMetricGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoogleMetricGroup());
  }
}

export const googleMetricGroupRoute: Routes = [
  {
    path: '',
    component: GoogleMetricGroupComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleMetricGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GoogleMetricGroupDetailComponent,
    resolve: {
      googleMetricGroup: GoogleMetricGroupResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleMetricGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GoogleMetricGroupUpdateComponent,
    resolve: {
      googleMetricGroup: GoogleMetricGroupResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleMetricGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GoogleMetricGroupUpdateComponent,
    resolve: {
      googleMetricGroup: GoogleMetricGroupResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleMetricGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
