import { environment } from '@env/environment';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {Component, ViewChild, AfterViewInit} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, Observable, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import { MediaObserver } from '@angular/flex-layout';
import { UserService } from './user.service';
import { UserModel, userFilter, UserApi } from './user.types';
import { Router } from '@angular/router';




@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class UserComponent implements AfterViewInit  {
  
  columnsToDisplay: any[] = [
    { def: 'name', showMobile: true },
    { def: 'whatsapp Number', showMobile: false },
    { def: 'mobile', showMobile: false },
    { def: 'Language', showMobile: false },
    { def: 'roles', showMobile: false },
    { def: 'action', showMobile: true }]

  
  data: UserModel[] = [];
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  expandedElement: any | null;
  selectedUser:any;
  isLoading = false;
  currentPage = 1;
  filterQuery={};
  filtersKeys:string[]=[];
  

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  isShowFilter = false;

  
  constructor(private _service: UserService, private media: MediaObserver, private router: Router) {}

  
  _getData() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._service!.getUsers(
            this.sort.active, this.sort.direction, this.paginator.pageIndex,this.paginator.pageSize,this.filterQuery);
        }),
        map((data:UserApi) => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.count;

          return data.items;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          // Catch if the GitHub API has reached its rate limit. Return empty data.
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
  }
  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this._getData();
    }


  get isMobile(): boolean {
    return this.media.isActive('xs') || this.media.isActive('sm');
  }

  getDisplayedColumns(): string[] {
    const isMobile = this.media.isActive('xs') || this.media.isActive('sm');
    const columns:string[] = this.columnsToDisplay
      .filter(cd => !isMobile || cd.showMobile)
      .map(cd => cd.def);
      return (columns);
  }

  onUserDelete(user:UserModel) {
    this._service.deleteUser(user.id)
    .subscribe(res => {
      if(res){
        this.data = this.data.filter((u:UserModel)=> u.id !== user.id);
      }
    });
  }

  onUserEnable(user: UserModel) {
    this._service.enableUser(user)
    .subscribe(res => {
      this.data.forEach((u:UserModel) => {
        if(user.id == u.id) {
            u.enable = !user.enable;
        }
      });
    });
  }

  

  tootleFilter() {
    this.filterQuery = {};
    this.isShowFilter = !this.isShowFilter;
    this._getData();
  }

  onFilter(q:any) {
    this.paginator.pageIndex = 0;
    this.filterQuery = q[0];
    this._getData();
   
  }

  onUserEdit(user: UserModel) {
    this._service.setSelectedUser(user);
    this.router.navigate(["user/add-user",{ isEdit: true}],{skipLocationChange: true})
  }

}


