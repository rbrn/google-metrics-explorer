import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGoogleProject, GoogleProject } from 'app/shared/model/google-project.model';
import { GoogleProjectService } from './google-project.service';
import { GoogleProjectComponent } from './google-project.component';
import { GoogleProjectDetailComponent } from './google-project-detail.component';
import { GoogleProjectUpdateComponent } from './google-project-update.component';

@Injectable({ providedIn: 'root' })
export class GoogleProjectResolve implements Resolve<IGoogleProject> {
  constructor(private service: GoogleProjectService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoogleProject> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((googleProject: HttpResponse<GoogleProject>) => {
          if (googleProject.body) {
            return of(googleProject.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoogleProject());
  }
}

export const googleProjectRoute: Routes = [
  {
    path: '',
    component: GoogleProjectComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleProject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GoogleProjectDetailComponent,
    resolve: {
      googleProject: GoogleProjectResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleProject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GoogleProjectUpdateComponent,
    resolve: {
      googleProject: GoogleProjectResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleProject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GoogleProjectUpdateComponent,
    resolve: {
      googleProject: GoogleProjectResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gcpMetricsExplorerApp.googleProject.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
