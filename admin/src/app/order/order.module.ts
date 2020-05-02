import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@app/material.module';
import { OrderRoutingModule } from './order-routing.module';
import { OrderComponent } from './order.component';
import { SharedModule } from '@shared';
import { CreateOrderComponent } from './components';

const components = [OrderComponent, CreateOrderComponent];
const modules = [
  CommonModule,
  TranslateModule,
  FlexLayoutModule,
  MaterialModule,
  OrderRoutingModule,
  SharedModule,
  ReactiveFormsModule,
];
@NgModule({
  imports: [...modules],
  declarations: [...components],
})
export class OrderModule {}
