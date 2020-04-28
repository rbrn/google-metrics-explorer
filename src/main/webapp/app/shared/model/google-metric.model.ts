export interface IGoogleMetric {
  id?: string;
  name?: string;
  description?: string;
  metricGroupName?: string;
  metricGroupId?: string;
  data?: TimeSeriesWrapper[];
}

export class GoogleMetric implements IGoogleMetric {
  constructor(
    public id?: string,
    public name?: string,
    public description?: string,
    public metricGroupName?: string,
    public metricGroupId?: string,
    public data?: TimeSeriesWrapper[]
  ) {}
}

export class TimeSeriesPoint {
  constructor(public value?: bigint, public startTime?: string, public startTimeHoursMinutes?: bigint) {}
}
export class TimeSeriesWrapper {
  constructor(public timeSeriesPoints?: TimeSeriesPoint[], public labelsMap?: Map<any, any>) {}
}
export class ChartWrapper {
  constructor(public datapoints: any, public labelsMap?: Map<any, any>) {}
}
