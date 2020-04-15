import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GcpMetricsExplorerTestModule } from '../../../test.module';
import { GoogleMetricGroupUpdateComponent } from 'app/entities/google-metric-group/google-metric-group-update.component';
import { GoogleMetricGroupService } from 'app/entities/google-metric-group/google-metric-group.service';
import { GoogleMetricGroup } from 'app/shared/model/google-metric-group.model';

describe('Component Tests', () => {
  describe('GoogleMetricGroup Management Update Component', () => {
    let comp: GoogleMetricGroupUpdateComponent;
    let fixture: ComponentFixture<GoogleMetricGroupUpdateComponent>;
    let service: GoogleMetricGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GcpMetricsExplorerTestModule],
        declarations: [GoogleMetricGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GoogleMetricGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoogleMetricGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoogleMetricGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoogleMetricGroup('123');
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
        const entity = new GoogleMetricGroup();
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
