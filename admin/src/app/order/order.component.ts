import { environment } from '@env/environment';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, Observable, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { MediaObserver } from '@angular/flex-layout';
import { OrderService } from './order.service';
import { PeriodicElement, orderType, OrderModel } from './order.types';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class OrderComponent implements AfterViewInit {
  columnsToDisplay: any[] = [
    { def: 'orderID', showMobile: false },
    { def: 'orderNumber', showMobile: false },
    { def: 'orderDate', showMobile: false },
    { def: 'shippingAddress', showMobile: false },
    { def: 'status', showMobile: true },
    { def: 'totalPrice', showMobile: false },
    { def: 'action', showMobile: true },
  ];

  data: OrderModel[] = [];
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  expandedElement: PeriodicElement | null;
  selectedOrder: any;
  isLoading = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _orderService: OrderService, private media: MediaObserver) {}

  ngAfterViewInit() {
    // this.exampleDatabase = new ExampleHttpDatabase(this._httpClient);

    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._orderService!.getOrders(
            this.sort.active,
            this.sort.direction,
            this.paginator.pageIndex,
            this.paginator.pageSize
          );
        }),
        map((data) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.total_count;

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

  get isMobile(): boolean {
    return this.media.isActive('xs') || this.media.isActive('sm');
  }

  getDisplayedColumns(): string[] {
    const isMobile = this.media.isActive('xs') || this.media.isActive('sm');
    const columns: string[] = this.columnsToDisplay.filter((cd) => !isMobile || cd.showMobile).map((cd) => cd.def);
    return columns;
  }

  onOrderStatusChanged(status: string) {
    const { orderID } = this.selectedOrder;
    this.isLoading = true;
    this._orderService.updateOrderStatus({ status: orderType[status], orderID }).subscribe((res: OrderModel) => {
      this.isLoading = false;
      this.data = this.data.map((item: any) => (item.orderID == this.selectedOrder.orderID ? { ...res } : item));
    });
  }
}
