import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { extract } from '@app/i18n';
import { OrderComponent } from './order.component';
import { CreateOrderComponent } from './components';

const routes: Routes = [
  // Module is lazy loaded, see app-routing.module.ts
  { path: '', component: OrderComponent, data: { title: extract('Order') } },
  { path: 'create-order', component: CreateOrderComponent, data: { title: extract('Order') } },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [],
})
export class OrderRoutingModule {}
