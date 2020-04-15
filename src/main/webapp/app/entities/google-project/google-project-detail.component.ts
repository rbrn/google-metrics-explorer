import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoogleProject } from 'app/shared/model/google-project.model';

@Component({
  selector: 'jhi-google-project-detail',
  templateUrl: './google-project-detail.component.html'
})
export class GoogleProjectDetailComponent implements OnInit {
  googleProject: IGoogleProject | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ googleProject }) => (this.googleProject = googleProject));
  }

  previousState(): void {
    window.history.back();
  }
}
