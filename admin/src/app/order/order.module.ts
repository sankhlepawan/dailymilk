import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MaterialModule } from '@app/material.module';
import { OrderRoutingModule } from './order-routing.module';
import { OrderComponent } from './order.component';
import { SharedModule } from '@shared';

@NgModule({
  imports: [CommonModule, TranslateModule, FlexLayoutModule, MaterialModule, OrderRoutingModule, SharedModule],
  declarations: [OrderComponent],
})
export class OrderModule {}
