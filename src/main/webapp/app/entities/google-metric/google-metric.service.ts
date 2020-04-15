import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoogleMetric } from 'app/shared/model/google-metric.model';

type EntityResponseType = HttpResponse<IGoogleMetric>;
type EntityArrayResponseType = HttpResponse<IGoogleMetric[]>;

@Injectable({ providedIn: 'root' })
export class GoogleMetricService {
  public resourceUrl = SERVER_API_URL + 'api/google-metrics';

  constructor(protected http: HttpClient) {}

  create(googleMetric: IGoogleMetric): Observable<EntityResponseType> {
    return this.http.post<IGoogleMetric>(this.resourceUrl, googleMetric, { observe: 'response' });
  }

  update(googleMetric: IGoogleMetric): Observable<EntityResponseType> {
    return this.http.put<IGoogleMetric>(this.resourceUrl, googleMetric, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGoogleMetric>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoogleMetric[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
