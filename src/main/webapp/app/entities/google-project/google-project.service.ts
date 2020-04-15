import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoogleProject } from 'app/shared/model/google-project.model';

type EntityResponseType = HttpResponse<IGoogleProject>;
type EntityArrayResponseType = HttpResponse<IGoogleProject[]>;

@Injectable({ providedIn: 'root' })
export class GoogleProjectService {
  public resourceUrl = SERVER_API_URL + 'api/google-projects';

  constructor(protected http: HttpClient) {}

  create(googleProject: IGoogleProject): Observable<EntityResponseType> {
    return this.http.post<IGoogleProject>(this.resourceUrl, googleProject, { observe: 'response' });
  }

  update(googleProject: IGoogleProject): Observable<EntityResponseType> {
    return this.http.put<IGoogleProject>(this.resourceUrl, googleProject, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGoogleProject>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoogleProject[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
