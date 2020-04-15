import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GcpMetricsExplorerTestModule } from '../../../test.module';
import { GoogleMetricGroupDetailComponent } from 'app/entities/google-metric-group/google-metric-group-detail.component';
import { GoogleMetricGroup } from 'app/shared/model/google-metric-group.model';

describe('Component Tests', () => {
  describe('GoogleMetricGroup Management Detail Component', () => {
    let comp: GoogleMetricGroupDetailComponent;
    let fixture: ComponentFixture<GoogleMetricGroupDetailComponent>;
    const route = ({ data: of({ googleMetricGroup: new GoogleMetricGroup('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GcpMetricsExplorerTestModule],
        declarations: [GoogleMetricGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GoogleMetricGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoogleMetricGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load googleMetricGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.googleMetricGroup).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
