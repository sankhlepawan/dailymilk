import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {merge, Observable, of as observableOf} from 'rxjs';
import { UserModel, UserApi, RoleApi, RoleType } from './user.types';

import { Logger } from '@core/logger.service';

const log = new Logger('OrderService');

@Injectable({
    providedIn: 'root',
})
export class UserService {
  
    selectUser:UserModel;
    constructor(private _httpClient: HttpClient,) {}

    setSelectedUser(u:UserModel) {
        this.selectUser = u;
    }

    getSelectedUser() {
        return this.selectUser;
    }
    
    _createRIQL(filter:any) {
        let q = '';
        for(let key in filter) {
              if(key == 'role' && filter[key]) {
                  q += `roles.roleName==${filter[key]};`
              }else if(key == 'name' && filter[key]){
                q += `(firstName==*${filter[key]}*,lastName==*${filter[key]}*);`
              }else if(filter[key]){
                q += `${[key]}==*${filter[key]}*;`;
              }
            }
            return q.substring(0, q.length -1);
        }
    
    getUsers(sortBy: string, sortType: string, page: number, limit: number,filter:any): Observable<UserApi> {
       const body = {sortType,sortBy,page, limit, query: ''};
       body.query =  this._createRIQL(filter);
       log.info("fetching order data...")
       const url = '/v1/user/search';
       return this._httpClient.post<UserApi>(url,body);
    }



    create(form: any){
       return this._httpClient.post<UserModel>('/v1/user/create', form);
    }

    deleteUser(userId:number) {
        return this._httpClient.post<UserModel>(`/v1/user/delete`, {id: userId});
    }

    enableUser(user: UserModel) {
        return this._httpClient.post<UserModel>(`/v1/user/enable`, {id: user.id,enable: !user.enable});
    }

    getAllRoles(){
        const url = '/v1/role/search';
        const form = {
            "query":"id=gt=0",
            "sortBy":"id",
            "sortType":"asc",
            "page":0,
            "limit":100
        }
       return this._httpClient.post<RoleApi>(url, form);
    }
}