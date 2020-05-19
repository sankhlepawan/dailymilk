import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CollectionModel, AllCollectionApi } from './collection.types';
import { Logger } from '@core/logger.service';
import { defaultPagination } from '@shared/constants';

const log = new Logger('CollectionService');

@Injectable({
  providedIn: 'root',
})
export class CollectionService {
  constructor(private _httpClient: HttpClient) {}

  search(sort: string, order: string, page: number, size: number, q: string): Observable<AllCollectionApi> {
    log.info('fetching order data...');
    const body = { ...defaultPagination, q: q };
    const url = '/v1/collection/search';
    return this._httpClient.post<AllCollectionApi>(url, body);
  }

  addCollection(body: CollectionModel) {
    const url = '/v1/collection/create';
    return this._httpClient.post<CollectionModel>(url, body);
  }
}
