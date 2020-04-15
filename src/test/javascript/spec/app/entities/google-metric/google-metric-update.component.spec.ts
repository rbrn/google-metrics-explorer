import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GcpMetricsExplorerTestModule } from '../../../test.module';
import { GoogleMetricUpdateComponent } from 'app/entities/google-metric/google-metric-update.component';
import { GoogleMetricService } from 'app/entities/google-metric/google-metric.service';
import { GoogleMetric } from 'app/shared/model/google-metric.model';

describe('Component Tests', () => {
  describe('GoogleMetric Management Update Component', () => {
    let comp: GoogleMetricUpdateComponent;
    let fixture: ComponentFixture<GoogleMetricUpdateComponent>;
    let service: GoogleMetricService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GcpMetricsExplorerTestModule],
        declarations: [GoogleMetricUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GoogleMetricUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoogleMetricUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoogleMetricService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoogleMetric('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoogleMetric();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
