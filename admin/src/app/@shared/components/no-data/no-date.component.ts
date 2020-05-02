import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-no-data',
    templateUrl: './no-data.component.html',
    styleUrls: ['./no-data.component.scss']
})
export class NoDataComponent implements OnInit {
    
    constructor() { }

    @Input("isShow") isShow: boolean;
    @Input("msg") msg: string;
    ngOnInit(): void { }
}
