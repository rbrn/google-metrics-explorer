import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GcpMetricsExplorerTestModule } from '../../../test.module';
import { GoogleProjectDetailComponent } from 'app/entities/google-project/google-project-detail.component';
import { GoogleProject } from 'app/shared/model/google-project.model';

describe('Component Tests', () => {
  describe('GoogleProject Management Detail Component', () => {
    let comp: GoogleProjectDetailComponent;
    let fixture: ComponentFixture<GoogleProjectDetailComponent>;
    const route = ({ data: of({ googleProject: new GoogleProject('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GcpMetricsExplorerTestModule],
        declarations: [GoogleProjectDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GoogleProjectDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoogleProjectDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load googleProject on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.googleProject).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
