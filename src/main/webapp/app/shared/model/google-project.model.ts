export interface IGoogleProject {
  id?: string;
  name?: string;
  googleId?: string;
  googleMetricId?: string;
}

export class GoogleProject implements IGoogleProject {
  constructor(public id?: string, public name?: string, public googleId?: string, public googleMetricId?: string) {}
}
