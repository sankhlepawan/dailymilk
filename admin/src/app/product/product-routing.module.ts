import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { extract } from '@app/i18n';
import { ProductComponent } from './product.component';
import { AddProductComponent } from './components';

const routes: Routes = [
  { path: '', component: ProductComponent, data: { title: extract('Product') } },
  { path: 'add-product', component: AddProductComponent, data: { title: extract('Product') } },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [],
})
export class ProductRoutingModule {}
