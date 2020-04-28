import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoogleMetric } from 'app/shared/model/google-metric.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GoogleMetricService } from './google-metric.service';
import { GoogleMetricDeleteDialogComponent } from './google-metric-delete-dialog.component';
import { GoogleMetricGroupService } from 'app/entities/google-metric-group/google-metric-group.service';
import { IGoogleMetricGroup } from 'app/shared/model/google-metric-group.model';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-google-metric',
  templateUrl: './google-metric.component.html'
})
export class GoogleMetricComponent implements OnInit, OnDestroy {
  googleMetrics: IGoogleMetric[];
  googlemetricgroups: IGoogleMetricGroup[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  groupLInks: any;
  page: number;
  predicate: string;
  ascending: boolean;
  selectedGroup: string;

  editForm = this.fb.group({
    metricGroupId: []
  });

  constructor(
    protected googleMetricService: GoogleMetricService,
    protected googleMetricGroupService: GoogleMetricGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    private fb: FormBuilder
  ) {
    this.selectedGroup = '';

    this.googleMetrics = [];
    this.googlemetricgroups = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.groupLInks = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAllGroups(): void {
    this.googleMetricGroupService
      .query({
        page: this.page,
        size: 150,
        sort: this.groupSort()
      })
      .subscribe((res: HttpResponse<IGoogleMetricGroup[]>) => this.paginateGoogleGroupMetrics(res.body, res.headers));
  }

  loadAll(): void {
    if (this.selectedGroup !== '') {
      this.googleMetrics = [];
      this.googleMetricService
        .findByGroup(this.selectedGroup)
        .subscribe((res: HttpResponse<IGoogleMetric[]>) => this.byGroupGoogleMetrics(res.body, res.headers));
    } else {
      this.googleMetricService
        .query({
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IGoogleMetric[]>) => this.paginateGoogleMetrics(res.body, res.headers));
    }
  }

  reset(): void {
    this.page = 0;
    this.googleMetrics = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    if (this.selectedGroup !== '') return;
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAllGroups();
    this.loadAll();
    this.registerChangeInGoogleMetrics();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGoogleMetric): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGoogleMetrics(): void {
    this.eventSubscriber = this.eventManager.subscribe('googleMetricListModification', () => this.reset());
  }

  delete(googleMetric: IGoogleMetric): void {
    const modalRef = this.modalService.open(GoogleMetricDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.googleMetric = googleMetric;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  groupSort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    result.push('name');
    return result;
  }

  protected paginateGoogleGroupMetrics(data: IGoogleMetricGroup[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.groupLInks = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.googlemetricgroups.push(data[i]);
      }
    }
  }

  trackById(index: number, item: IGoogleMetricGroup): any {
    return item.id;
  }

  protected byGroupGoogleMetrics(data: IGoogleMetric[] | null, headers: HttpHeaders): void {
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.googleMetrics.push(data[i]);
      }
    }
  }
  protected paginateGoogleMetrics(data: IGoogleMetric[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.googleMetrics.push(data[i]);
      }
    }
  }

  filter(): void {
    const groupEditor = this.editForm.get(['metricGroupId']);
    this.selectedGroup = groupEditor!.value || '';
    this.loadAll();
  }
}
