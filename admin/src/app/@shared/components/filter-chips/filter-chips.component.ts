import { Component, OnInit , Input, Output, EventEmitter} from '@angular/core';

@Component({
    selector: 'app-filter-chip',
    templateUrl: './filter-chips.component.html',
    styleUrls: ['./filter-chips.component.scss']
})
export class FilterChipComponent  implements OnInit{
    
    @Input("filterData") filterQuery:any;
    filtersKeys: string[];
    @Output() onClear: EventEmitter<any> = new EventEmitter();
    constructor() {}

     ngOnInit () { 
        if(this.filterQuery) {
            this.filtersKeys =(Object.keys( this.clean(this.filterQuery) ));
            console.log('keys', this.filtersKeys);
        }
     }

    clean(obj:any) {
        for (var propName in obj) { 
          if (obj[propName] === null || obj[propName] === undefined || obj[propName] === '') {
            delete obj[propName];
          }
        }
        return obj;
      }

     clearChips() {
        this.onClear.emit([]);
     }

}
