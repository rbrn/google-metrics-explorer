import { IGoogleMetric } from 'app/shared/model/google-metric.model';

export interface IGoogleMetricGroup {
  id?: string;
  name?: string;
  googleId?: string;
  googleMetrics?: IGoogleMetric[];
}

export class GoogleMetricGroup implements IGoogleMetricGroup {
  constructor(public id?: string, public name?: string, public googleId?: string, public googleMetrics?: IGoogleMetric[]) {}
}
