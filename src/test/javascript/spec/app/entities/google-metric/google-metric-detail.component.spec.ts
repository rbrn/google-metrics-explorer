import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GcpMetricsExplorerTestModule } from '../../../test.module';
import { GoogleMetricDetailComponent } from 'app/entities/google-metric/google-metric-detail.component';
import { GoogleMetric } from 'app/shared/model/google-metric.model';

describe('Component Tests', () => {
  describe('GoogleMetric Management Detail Component', () => {
    let comp: GoogleMetricDetailComponent;
    let fixture: ComponentFixture<GoogleMetricDetailComponent>;
    const route = ({ data: of({ googleMetric: new GoogleMetric('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GcpMetricsExplorerTestModule],
        declarations: [GoogleMetricDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GoogleMetricDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoogleMetricDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load googleMetric on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.googleMetric).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
