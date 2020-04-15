import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IGoogleMetricGroup } from 'app/shared/model/google-metric-group.model';

type EntityResponseType = HttpResponse<IGoogleMetricGroup>;
type EntityArrayResponseType = HttpResponse<IGoogleMetricGroup[]>;

@Injectable({ providedIn: 'root' })
export class GoogleMetricGroupService {
  public resourceUrl = SERVER_API_URL + 'api/google-metric-groups';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/google-metric-groups';

  constructor(protected http: HttpClient) {}

  create(googleMetricGroup: IGoogleMetricGroup): Observable<EntityResponseType> {
    return this.http.post<IGoogleMetricGroup>(this.resourceUrl, googleMetricGroup, { observe: 'response' });
  }

  update(googleMetricGroup: IGoogleMetricGroup): Observable<EntityResponseType> {
    return this.http.put<IGoogleMetricGroup>(this.resourceUrl, googleMetricGroup, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGoogleMetricGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoogleMetricGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoogleMetricGroup[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
