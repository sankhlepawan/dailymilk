import { Component, ViewChild, AfterViewInit } from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, Observable, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { MediaObserver } from '@angular/flex-layout';
import { CollectionService } from './collection.service';
import { CollectionModel, AllCollectionApi } from './collection.types';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class CollectionComponent implements AfterViewInit {
  columnsToDisplay: any[] = [
    { def: 'type', showMobile: false },
    { def: 'testMode', showMobile: false },
    { def: 'status', showMobile: true },
    { def: 'fat', showMobile: true },
    { def: 'snf', showMobile: true },
    { def: 'qwt', showMobile: true },
    { def: 'amount', showMobile: false },
    // { def: 'rate', showMobile: false },
    // { def: 'temp', showMobile: false },
    // { def: 'water', showMobile: false },
    // { def: 'protein', showMobile: false },
    { def: 'createdOn', showMobile: false },
    { def: 'action', showMobile: true },
  ];

  data: CollectionModel[] = [];
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  expandedElement: any | null;
  selectedCollection: any;
  isLoading = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _service: CollectionService, private media: MediaObserver) {}

  _getData() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._service!.search(
            this.sort.active,
            this.sort.direction,
            this.paginator.pageIndex,
            this.paginator.pageSize,
            ''
          );
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = 100;

          return data.items;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          // Catch if the GitHub API has reached its rate limit. Return empty data.
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      )
      .subscribe((data) => (this.data = data));
  }

  ngAfterViewInit() {
    this._getData();
  }

  get isMobile(): boolean {
    return this.media.isActive('xs') || this.media.isActive('sm');
  }

  getDisplayedColumns(): string[] {
    const isMobile = this.media.isActive('xs') || this.media.isActive('sm');
    const columns: string[] = this.columnsToDisplay.filter((cd) => !isMobile || cd.showMobile).map((cd) => cd.def);
    return columns;
  }

  onOrderStatusChanged(status: string) {
    const { orderID } = this.selectedCollection;
    this.isLoading = true;
    // this._service.updateOrderStatus({ status: orderType[status] , orderID})
    // .subscribe((res : OrderModel) => {
    //   this.isLoading = false;
    //   this.data = this.data.map((item: any) => item.orderID == this.selectedOrder.orderID ?  { ...res } : item);
    // });
  }
}
