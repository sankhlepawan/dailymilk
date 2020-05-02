import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductModel, AllProductApi } from './product.types';
import { Logger } from '@core/logger.service';
import { defaultPagination } from '@shared/constants';

const log = new Logger('ProductService');

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private _httpClient: HttpClient) {}

  getOrders(sort: string, order: string, page: number, size: number): Observable<AllProductApi> {
    log.info('fetching order data...');
    const url = '/v1/product/findAll';
    return this._httpClient.get<AllProductApi>(url);
  }

  addProduct(body: ProductModel) {
    const url = '/v1/product/create';
    let form = { subCategory: { id: body.subCategory } };
    delete body.subCategory;
    delete body.subCategoryName;
    form = { ...form, ...body };
    console.log(form);
    return this._httpClient.post<ProductModel>(url, form);
  }

  getAllCategory() {
    log.info('fetching category data...');
    const url = '/v1/common/findAllCategory';
    return this._httpClient.get<AllProductApi>(url);
  }

  getproductByName(name: string) {
    let nameVal = encodeURI(name);
    const body = { ...defaultPagination, limit: 100, query: `name==*${nameVal}*` };
    return this._httpClient.post<AllProductApi>('/v1/product/search', body);
  }
}
