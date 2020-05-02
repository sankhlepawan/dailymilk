import { Component, Input,Output, EventEmitter } from '@angular/core';
import { userFilter, RoleType, RoleApi } from '../../user.types';
import { UserService } from '../../user.service';


@Component({
    selector: 'app-user-filter',
    templateUrl: './filter.component.html',
    styleUrls: ['./filter.component.scss']
})
export class UserFilterComponent {
    constructor(private userService: UserService) {
        this._initFilter();
        this._getAllRoles();
     }
    
    @Input("isShowFilter") isShowFilter:boolean;
    @Output() onClose: EventEmitter<any> = new EventEmitter();
    @Output("onFilter") onFilter: EventEmitter<any> = new EventEmitter();
    isShowChips = false;
    allRoles: RoleType[];
    query:userFilter;

    _initFilter() {
        this.query = { role: '', whatsappNumber: '', name: ''};
    }

    _getAllRoles() {
        this.userService.getAllRoles()
        .subscribe((res: RoleApi) => this.allRoles = res.items);
        console.log(this.allRoles);
    }

    onFilterClose(): void {
        // this.onSuggest.emit([clickedEntry, this.query]);
        this._initFilter();
        this.onClose.emit();
    }

    onSearch():void {
        this.isShowChips = !this.isShowChips;
        this.onFilter.emit([this.query]);
    }

    onClearChip(): void {
        this._initFilter();
        this.onClose.emit();
    }
}
