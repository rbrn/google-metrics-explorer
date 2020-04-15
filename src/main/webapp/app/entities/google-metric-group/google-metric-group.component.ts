import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoogleMetricGroup } from 'app/shared/model/google-metric-group.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GoogleMetricGroupService } from './google-metric-group.service';
import { GoogleMetricGroupDeleteDialogComponent } from './google-metric-group-delete-dialog.component';

@Component({
  selector: 'jhi-google-metric-group',
  templateUrl: './google-metric-group.component.html'
})
export class GoogleMetricGroupComponent implements OnInit, OnDestroy {
  googleMetricGroups: IGoogleMetricGroup[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected googleMetricGroupService: GoogleMetricGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.googleMetricGroups = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.googleMetricGroupService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGoogleMetricGroup[]>) => this.paginateGoogleMetricGroups(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.googleMetricGroups = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGoogleMetricGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGoogleMetricGroup): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGoogleMetricGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('googleMetricGroupListModification', () => this.reset());
  }

  delete(googleMetricGroup: IGoogleMetricGroup): void {
    const modalRef = this.modalService.open(GoogleMetricGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.googleMetricGroup = googleMetricGroup;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGoogleMetricGroups(data: IGoogleMetricGroup[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.googleMetricGroups.push(data[i]);
      }
    }
  }
}
