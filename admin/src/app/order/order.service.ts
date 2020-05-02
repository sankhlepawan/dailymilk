import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {merge, Observable, of as observableOf} from 'rxjs';
import { OrderModel , OrderApi} from './order.types';

import { Logger } from '@core/logger.service';

const log = new Logger('OrderService');

@Injectable({
    providedIn: 'root',
})
export class OrderService {
  
    constructor(private _httpClient: HttpClient,) {}

    getOrders(sort: string, order: string, page: number, size: number): Observable<OrderApi> {
       log.info("fetching order data...")
       const url = '/v1/order/findAll';
       return this._httpClient.get<OrderApi>(url);
    }

    updateOrderStatus(body: OrderModel){
        const url = '/v1/order/update';
       return this._httpClient.post<OrderModel>(url, body);
    }
}