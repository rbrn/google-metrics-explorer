import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GcpMetricsExplorerTestModule } from '../../../test.module';
import { GoogleProjectUpdateComponent } from 'app/entities/google-project/google-project-update.component';
import { GoogleProjectService } from 'app/entities/google-project/google-project.service';
import { GoogleProject } from 'app/shared/model/google-project.model';

describe('Component Tests', () => {
  describe('GoogleProject Management Update Component', () => {
    let comp: GoogleProjectUpdateComponent;
    let fixture: ComponentFixture<GoogleProjectUpdateComponent>;
    let service: GoogleProjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GcpMetricsExplorerTestModule],
        declarations: [GoogleProjectUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GoogleProjectUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoogleProjectUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoogleProjectService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoogleProject('123');
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
        const entity = new GoogleProject();
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
