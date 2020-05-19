import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { extract } from '@app/i18n';
import { CollectionComponent } from './collection.component';
import { AddCollectionComponent } from './components';

const routes: Routes = [
  { path: '', component: CollectionComponent, data: { title: extract('Collection') } },
  { path: 'add-collection', component: AddCollectionComponent, data: { title: extract('Collection') } },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [],
})
export class CollectionRoutingModule {}
