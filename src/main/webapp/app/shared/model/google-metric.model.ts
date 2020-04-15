export interface IGoogleMetric {
  id?: string;
  name?: string;
  description?: string;
  metricGroupName?: string;
  metricGroupId?: string;
  data?: TimeSeriesPoint[];
}

export class GoogleMetric implements IGoogleMetric {
  constructor(
    public id?: string,
    public name?: string,
    public description?: string,
    public metricGroupName?: string,
    public metricGroupId?: string,
    public data?: TimeSeriesPoint[]
  ) {}
}

export class TimeSeriesPoint {
  constructor(public value?: bigint, public startTime?: string, public startTimeHoursMinutes?: bigint) {}
}
