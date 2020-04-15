import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoogleMetric } from 'app/shared/model/google-metric.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GoogleMetricService } from './google-metric.service';
import { GoogleMetricDeleteDialogComponent } from './google-metric-delete-dialog.component';

@Component({
  selector: 'jhi-google-metric',
  templateUrl: './google-metric.component.html'
})
export class GoogleMetricComponent implements OnInit, OnDestroy {
  googleMetrics: IGoogleMetric[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;

  constructor(
    protected googleMetricService: GoogleMetricService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.googleMetrics = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.googleMetricService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IGoogleMetric[]>) => this.paginateGoogleMetrics(res.body, res.headers));
      return;
    }

    this.googleMetricService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGoogleMetric[]>) => this.paginateGoogleMetrics(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.googleMetrics = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  search(query: string): void {
    this.googleMetrics = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    if (query) {
      this.predicate = '_score';
      this.ascending = false;
    } else {
      this.predicate = 'id';
      this.ascending = true;
    }
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
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

  protected paginateGoogleMetrics(data: IGoogleMetric[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.googleMetrics.push(data[i]);
      }
    }
  }
}
